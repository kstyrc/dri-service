package vphshare.driservice.testing.prepare;

import java.util.ArrayList;
import java.util.List;

import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.BlobMetadata;
import org.jclouds.blobstore.domain.PageSet;
import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.blobstore.domain.StorageType;

import vphshare.driservice.domain.DataSource;
import vphshare.driservice.domain.CloudFile;
import vphshare.driservice.domain.CloudDirectory;
import vphshare.driservice.exceptions.ResourceNotFoundException;
import vphshare.driservice.providers.BlobStoreContextProvider;
import vphshare.driservice.testing.MetadataRegistryMock;

public class RealWorldDataDatasetBuilder implements DatasetGenericBuilder {
	
	@Override
	public CloudDirectory build(MetadataRegistryMock registryMock, DataSource ds) {
		
		// Here we use LOBCDER-REPLICA dataset already present in the datasource,
		// so we have to only register it the the metadata registry mock.
		
		BlobStoreContext context = BlobStoreContextProvider.getContext(ds);
		try {
			BlobStore blobstore = context.getBlobStore();
			
			String datasetID = "LOBCDER-REPLICA";
			if (!blobstore.containerExists(datasetID))
				throw new ResourceNotFoundException("Dataset " + datasetID + "is not present on the data source" + ds.getResourceUrl());
			
			@SuppressWarnings("unchecked")
			PageSet<StorageMetadata> itemMetadatas = (PageSet<StorageMetadata>) blobstore.list(datasetID);
			List<CloudFile> datas = new ArrayList<CloudFile>();
			
			for (StorageMetadata metadata : itemMetadatas) {
				if (metadata.getType().equals(StorageType.BLOB)) {
					BlobMetadata blobMetadata = blobstore.blobMetadata(datasetID, metadata.getName());
					if (blobMetadata.getContentMetadata().getContentLength() > 0L) {
						CloudFile item = new CloudFile(metadata.getName());
						item.setName(metadata.getName());
						item.setSize(blobMetadata.getContentMetadata().getContentLength());
						List<DataSource> dsList = new ArrayList<DataSource>();
						dsList.add(ds);
						item.setDataSources(dsList);
						datas.add(item);
					}
				}
			}
		
			CloudDirectory dataset = new CloudDirectory("1", datasetID);
			
			registryMock.addDataset(dataset);
			registryMock.addLogicalDatas(dataset, datas);
			
			return dataset;
		} finally {
			context.close();
		}
	}

	@Override
	public void cleanup(CloudDirectory dataset, MetadataRegistryMock registry, DataSource ds) {
		// do nothing, we use existing dataset, so we don't want to delete it
	}

}
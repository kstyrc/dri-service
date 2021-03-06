package vphshare.driservice.registry;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import vphshare.driservice.domain.CloudDirectory;
import vphshare.driservice.registry.lobcder.LobcderMetadataRegistry;
import vphshare.driservice.registry.lobcder.WrappedLogicalData;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;


public class LobcderMetadataRegistryTest {
	
	@Mock
	WebResource serviceMock;
	MetadataRegistry registry;
	
	@Before
	public void setupMetadataRegistry() {
		serviceMock = mock(WebResource.class, RETURNS_DEEP_STUBS);
		registry = new LobcderMetadataRegistry(serviceMock);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturnNoDatasets() {
		when(serviceMock.queryParam(anyString(), anyString()).path(anyString()).get(any(GenericType.class)))
		.thenReturn(new ArrayList<WrappedLogicalData>());
		List<CloudDirectory> datasets = registry.getCloudDirectories(false);
		assertEquals(0, datasets.size());
	}
}

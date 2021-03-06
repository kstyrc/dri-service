public class AIRMetadataRegistry {
	@Inject @Named("air-config")
	protected WebResource service;

	public List<ManagedDataset> getManagedDatasets() {
		return service
			.path("/get_datasets")
			.queryParam("only_managed", Boolean.TRUE)
			.get(new GenericType<List<ManagedDataset>>() {});
	}
	
	// Further methods follow...
}

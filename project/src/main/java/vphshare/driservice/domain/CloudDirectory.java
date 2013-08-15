package vphshare.driservice.domain;

import org.apache.commons.collections.Predicate;

public class CloudDirectory implements Comparable<CloudDirectory> {

	private String id;
	private String name;

	public CloudDirectory() {
	}

	public CloudDirectory(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CloudDirectory))
			return false;
		CloudDirectory other = (CloudDirectory) obj;
		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		return true;
	}

	@Override
	public int compareTo(CloudDirectory o) {
		return this.name.compareTo(o.getName());
	}

	public static Predicate getPredicate(final String datasetIDorName) {
		return new Predicate() {
			
			@Override
			public boolean evaluate(Object object) {
				CloudDirectory dataset = (CloudDirectory) object;
				return dataset.getId().equals(datasetIDorName) || dataset.getName().equals(datasetIDorName);

			}
		};
	}
}
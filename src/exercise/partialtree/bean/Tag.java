package exercise.partialtree.bean;

public class Tag {

	private String name;
	private TagType type;
	
	public Tag(String name, TagType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public Tag() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TagType getType() {
		return type;
	}

	public void setType(TagType type) {
		this.type = type;
	}
	
	
	
}

package exercise.bean;

public class Step {
	
	private String nameTest;
	private Axis axis;
	private String predicate;

	public Step() {
		// TODO Auto-generated constructor stub
		this.nameTest="*";
		this.axis=Axis.DESCENDANT_OR_SELF;
	}

	public String getNameTest() {
		return nameTest;
	}

	public void setNameTest(String nameTest) {
		if(nameTest==null) {
			this.nameTest="*";
			return;
		}
		this.nameTest = nameTest;
	}

	public Axis getAxis() {
		return axis;
	}

	public void setAxis(Axis axis) {
		if(axis==null) {
			this.axis=Axis.DESCENDANT_OR_SELF;
			return;
		}
		this.axis = axis;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	@Override
	public String toString() {
		String s=axis + "::" + nameTest;
		
		if(predicate!=null&&!predicate.equals("")) {
			s+="["+predicate+"]";
		}
		
		return s;
	}
	
	
	
}














































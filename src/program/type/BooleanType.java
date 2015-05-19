package program.type;

<<<<<<< HEAD
public class BooleanType implements Type {
=======
public class BooleanType extends Type<Boolean>{
>>>>>>> refs/remotes/origin/KevinUberBranch


	public BooleanType(Boolean value) {
		setValue(value);
	}
	
	public BooleanType(){
		setValue(getDefaultValue());
	}

	public Boolean getDefaultValue() {
		return null;
	}
	

<<<<<<< HEAD
	public void setValue(Object value) {
		this.value = (Boolean) value;	
	}

	private Boolean value;	
=======
>>>>>>> refs/remotes/origin/KevinUberBranch
}


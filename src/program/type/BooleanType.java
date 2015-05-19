package program.type;

public class BooleanType extends Type<Boolean>{


	public BooleanType(Boolean value) {
		setValue(value);
	}
	
	public BooleanType(){
		setValue(getDefaultValue());
	}

	public Boolean getDefaultValue() {
		return null;
	}
	

}


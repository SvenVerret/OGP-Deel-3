package program.type;

public class BooleanType extends Type {


	public BooleanType(Boolean value) {
		setValue(value);
	}
	
	public BooleanType(){
		setValue(getDefaultValue());
	}

	@Override
	public Boolean getDefaultValue() {
		return null;
	}
	
	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	private Boolean value;
	
}


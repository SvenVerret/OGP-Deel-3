package program.type;

public class BooleanType implements Type {


	public BooleanType(Boolean value) {
		setValue(value);
	}
	
	public BooleanType(){
		setValue(getDefaultValue());
	}

	public Boolean getDefaultValue() {
		return null;
	}
	
	public Boolean getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = (Boolean) value;	
	}

	private Boolean value;	
}


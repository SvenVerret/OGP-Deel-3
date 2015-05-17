package program.type;

public class BooleanType extends Type {


	public BooleanType(Boolean value) {
		this.value = value;
	}

	@Override
	public Boolean getValue() {
		return value;
	}

	private Boolean value;

}


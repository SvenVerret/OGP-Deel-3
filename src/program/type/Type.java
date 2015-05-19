package program.type;

public class Type<T>{

	public Type(){
		setValue(getDefaultValue());
	}
	
	public Type(T value){
		setValue(value);
	}
	
	public T getDefaultValue() {
		return null;
	}
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	private T value;

}

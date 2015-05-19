package program.type;

<<<<<<< HEAD
public interface Type{
=======
public class Type<T>{
>>>>>>> refs/remotes/origin/KevinUberBranch

<<<<<<< HEAD
	public Object getDefaultValue();
	
	public Object getValue();
=======
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
>>>>>>> refs/remotes/origin/KevinUberBranch

<<<<<<< HEAD
	public void setValue(Object value);
=======
	public void setValue(T value) {
		this.value = value;
	}
	
	private T value;
>>>>>>> refs/remotes/origin/KevinUberBranch

}

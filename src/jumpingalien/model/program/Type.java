package jumpingalien.model.program;

/**
 * 
 * @author Kevin & Sven
 *
 * @param <T>
 */
public class Type<T> {
	
	public Type(){
		
	}
	
	private T t = null;
	
	/**
	 * This method creates a type from the given value
	 * 
	 * @post	...
	 * 			
	 */
	public void createType(){
		
	}
	
	
	/**
	 * this method returns the type of the object
	 * 
	 * @return	...
	 * 			| return t.getClass();
	 */
	public Class<? extends Object> getType(){
		return t.getClass();
	}
	
	
	
}

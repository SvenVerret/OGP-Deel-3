package program.type;
 
import jumpingalien.model.GameObject;
 
public class GameObjectType<T extends GameObject> extends Type{
<<<<<<< HEAD
 
        /**
         *
         * @param object
         */
        public GameObjectType(T object){
                this.object = object;
               
        }
 
 
        @Override
        public T getValue() {
                return object;
        }
 
        private final T object;
=======

	/**
	 *
	 * @param object
	 */
	public GameObjectType(T object){
		this.object = object;

	}


	@Override
	public T getValue() {
		return object;
	}

	private final T object;
>>>>>>> branch 'master' of https://github.com/SvenVerret/OGP-Deel-3.git
}

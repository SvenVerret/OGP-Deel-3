package program.type;

import jumpingalien.model.GameObject;

public class GameObjectType<T extends GameObject> extends Type{

	/**
	 * 
	 * @param object
	 */
	public GameObjectType(T object){
		
	}

	@Override
	public boolean equals(Type type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}
}

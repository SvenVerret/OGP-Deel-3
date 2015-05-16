package program.type;

import jumpingalien.model.GameObject;

public class GameObjectType<T extends GameObject> extends Type{

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
}
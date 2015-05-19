package program.type;

import jumpingalien.model.GameObject;

public class GameObjectType implements Type{

	public GameObjectType(GameObject value) {
		setValue(value);
	}

	public GameObjectType(){
		setValue(getDefaultValue());
	}

	public GameObject getDefaultValue() {
		return null;
	}

	public void setValue(Object value) {
		this.value = (GameObject) value;
	}

	public GameObject getValue() {
		return value;
	}

	private GameObject value;
}


package program.type;

public class GameObjectType{

	public GameObjectType(Object gameobject) {
		GameObject = gameobject;
	}
	
	public GameObjectType() {
		this(null);
	}
	

	public Object getGameObject() {
		return GameObject;
	}

	public void setGameObject(Object gameObject) {
		GameObject = gameObject;
	}

	private Object GameObject;
}
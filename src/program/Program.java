package program;
import java.util.Map;

import jumpingalien.model.GameObject;
import program.statement.Statement;

public class Program {

	public Statement getMainStatement() {
		return mainstatement;
	}
	private final Statement mainstatement;
	
	
	public Map<String, Object> getVariables() {
		return globalVariables;
	}
	public void setVariables(Map<String, Object> globalVariables) {
		this.globalVariables = globalVariables;
	}
	private Map<String, Object> globalVariables;
	
	
	public void setGameObject(GameObject gameobject){
		this.gameobject = gameobject;
	}
	public GameObject getGameObject(){
		return gameobject;
	}
	private GameObject gameobject;
	

	public Program(Statement mainstatement, Map<String,Object> globalVariables){
		this.mainstatement = mainstatement;
		this.setVariables(globalVariables);
	}
	
	public void advanceTime(double dt){
		getMainStatement().advanceTime(dt, this);
	}
}

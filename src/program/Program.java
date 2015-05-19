package program;
import java.util.Map;

import jumpingalien.model.GameObject;
import program.statement.Statement;
import program.type.Type;


public class Program {

	public Statement getMainStatement() {
		return mainstatement;
	}
	private final Statement mainstatement;
	
	
	public Map<String, Type<?>> getVariables() {
		return globalVariables;
	}
	public void setVariables(Map<String, Type<?>> globalVariables) {
		this.globalVariables = globalVariables;
	}
	private Map<String, Type<?>> globalVariables;
	
	
	public void setGameObject(Type<GameObject> gameobject){
		this.gameobject = gameobject;
	}
	public Type<GameObject> getGameObject(){
		return gameobject;
	}
	private Type<GameObject> gameobject;
	

	public Program(Statement mainstatement, Map<String, Type<?>> globalVariables2){
		this.mainstatement = mainstatement;
		this.setVariables(globalVariables2);
	}
	
	public void advanceTime(double dt){
		getMainStatement().advanceTime(dt, this);
	}
}

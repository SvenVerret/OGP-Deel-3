package program;
import java.util.Map;

import program.statement.Statement;
import program.type.GameObjectType;
import program.type.Type;


public class Program {

	public Statement getMainStatement() {
		return mainstatement;
	}
	private final Statement mainstatement;
	
	
	public Map<String, Type> getVariables() {
		return globalVariables;
	}
	public void setVariables(Map<String, Type> globalVariables) {
		this.globalVariables = globalVariables;
	}
	private Map<String, Type> globalVariables;
	
	
	public void setGameObject(GameObjectType<?> gameobject){
		this.gameobject = gameobject;
	}
	public GameObjectType<?> getGameObject(){
		return gameobject;
	}
	private GameObjectType<?> gameobject;
	

	public Program(Statement mainstatement, Map<String, Type> globalVariables){
		this.mainstatement = mainstatement;
		this.setVariables(globalVariables);
	}
	
	public void advanceTime(double dt){
		getMainStatement().advanceTime(dt, globalVariables);
	}
}

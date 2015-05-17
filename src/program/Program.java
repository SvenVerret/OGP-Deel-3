package program;

import java.util.HashMap;

import jumpingalien.model.GameObject;
import program.expression.Expression;
import program.statement.Statement;
import program.type.GameObjectType;
import program.type.Type;


public class Program {

	public Statement getMainStatement() {
		return mainstatement;
	}
	private final Statement mainstatement;
	
	
	public HashMap<String, Expression<? extends Type>> getVariables() {
		return variables;
	}
	public void setVariables(HashMap<String, Expression<? extends Type>> variables) {
		this.variables = variables;
	}
	private HashMap<String, Expression<? extends Type>> variables;
	
	
	public void setGameObject(GameObjectType<?> gameobject){
		this.gameobject = gameobject;
	}
	public GameObjectType<?> getGameObject(){
		return gameobject;
	}
	private GameObjectType<?> gameobject;
	

	public Program(Statement mainstatement, HashMap<String, Expression<? extends Type>> variables){
		this.mainstatement = mainstatement;
		this.setVariables(variables);
	}
	
	public void advanceTime(double dt){
		getMainStatement().advanceTime(dt, variables);
	}
}

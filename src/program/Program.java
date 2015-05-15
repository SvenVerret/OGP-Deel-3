package program;

import java.util.HashMap;

import jumpingalien.model.GameObject;
import program.expression.Expression;
import program.statement.Statement;

public class Program {

	public Statement getMainstatement() {
		return mainstatement;
	}
	private final Statement mainstatement;
	
	
	public HashMap<String, Expression<?>> getVariables() {
		return variables;
	}
	public void setVariables(HashMap<String, Expression<?>> variables) {
		this.variables = variables;
	}
	private HashMap<String, Expression<?>> variables;
	
	
	public void setGameObject(GameObject gameobject){
		this.gameobject = gameobject;
	}
	public GameObject getGameObject(){
		return gameobject;
	}
	private GameObject gameobject;
	

	public Program(Statement mainstatement, HashMap<String, Expression<?>> variables){
		this.mainstatement = mainstatement;
		this.setVariables(variables);
	}
}

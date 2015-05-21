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
	
	public Map<String, Object> getInitialVariables() {
		return globalInitialVariables;
	}
	public void setInitialVariables(Map<String, Object> globalInitialVariables) {
		this.globalInitialVariables = globalInitialVariables;
	}
	private Map<String, Object> globalInitialVariables;


	public void setGameObject(GameObject gameobject){
		this.gameobject = gameobject;
	}
	public GameObject getGameObject(){
		return gameobject;
	}
	private GameObject gameobject;


	public Program(Statement mainstatement, Map<String,Object> globalVariables){
		this.mainstatement = mainstatement;
		this.setInitialVariables(globalVariables);
		this.setVariables(globalVariables);
	}

	public void advanceTime(double dt){
		
		getMainStatement().advanceTime(dt, this);
		
		if(getMainStatement().isExecutionComplete()){
			setVariables(getInitialVariables());
			getMainStatement().Reset();
		}
	}

	public void decreaseRemainingTime(double time){
		double newtime = RemainingTime - time;
		if (newtime <= 0)
			RemainingTime = 0.0;
		else
			RemainingTime = newtime;
	}

	public void decreaseRemainingTime(){
		decreaseRemainingTime(defaultDT);
	}

	public double getRemainingTime() {
		return RemainingTime;
	}
	private void setRemainingTime(double remainingTime) {
		RemainingTime = remainingTime;
	}
	private double RemainingTime = 0.0;
	public static double defaultDT = 0.001;
}

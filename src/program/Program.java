package program;
import java.util.HashSet;
import java.util.Map;

import jumpingalien.model.GameObject;
import program.statement.Statement;
import program.util.TimeIsUpException;

public class Program {

	public Program(Statement mainstatement, Map<String,Object> globalVariables){
		this.mainstatement = mainstatement;
		this.setInitialVariables(globalVariables);
		this.setVariables(globalVariables);
	}
	public void advanceTime(double dt){
		try{
			if(getRemainingTime() == 0.0){
				
				if(dt <= defaultDT){
					setRemainingTime(defaultDT);
				}else{
					setRemainingTime(dt);
				}
				this.resetAmountMainExecuted();
			}
	
	
			while(this.getRemainingTime() > 0){
	
				getMainStatement().advanceTime(dt, this);
	
				if(getMainStatement().isExecutionComplete()){
					System.out.println("Complete");
					
					this.addAmountMainExecuted();
					setVariables(getInitialVariables());
					getMainStatement().Reset();
				}
			}
	
		} catch(TimeIsUpException t){
	
			System.out.println("Times up");
	
			//Get out of the mainstatement, wait for new advance time
		} catch(Exception e){
			getMainStatement().forceReset();
		}
	}
	public Statement getMainStatement() {
		return mainstatement;
	}
	public Map<String, Object> getVariables() {
		return globalVariables;
	}
	public void setVariables(Map<String, Object> globalVariables) {
		this.globalVariables = globalVariables;
	}
	public Map<String, Object> getInitialVariables() {
		return globalInitialVariables;
	}
	public void setInitialVariables(Map<String, Object> globalInitialVariables) {
		this.globalInitialVariables = globalInitialVariables;
	}
	public void setGameObject(GameObject gameobject){
		this.gameobject = gameobject;
	}
	public GameObject getGameObject(){
		return gameobject;
	}
	public void decreaseRemainingTime(double time){
		double newtime = RemainingTime - time;
		if (newtime <= 0){
			RemainingTime = 0.0;
			throw new TimeIsUpException();
		}
		else{
			RemainingTime = newtime;
		}
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
	public static double defaultDT = 0.001;
	
	
	public int getAmountMainExecuted() {
		return amountMainExecuted;
	}
	private void addAmountMainExecuted() {
		this.amountMainExecuted += 1;
	}
	private void resetAmountMainExecuted(){
		amountMainExecuted = 0;
	}
	public boolean isWellFormed(){
		HashSet<String> parentStatements = new HashSet<String>();
		return getMainStatement().isWellFormed(parentStatements);
	}
	private final Statement mainstatement;
	private Map<String, Object> globalVariables;
	private Map<String, Object> globalInitialVariables;
	private GameObject gameobject;
	private double RemainingTime = 0.0;
	private int amountMainExecuted = 0;
}

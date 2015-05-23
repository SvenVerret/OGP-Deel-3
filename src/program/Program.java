package program;
import java.util.Map;

import jumpingalien.model.Buzam;
import jumpingalien.model.GameObject;
import program.statement.Statement;
import program.util.TimeIsUpException;

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
		try{
			if(getGameObject() instanceof Buzam){
				System.out.println("--------------------");
				System.out.println("Begin advance time");
				System.out.println("--------------------");
			}

			if(getRemainingTime() == 0.0){

				if(dt <= defaultDT){
					setRemainingTime(defaultDT);
				}else{
					setRemainingTime(dt);
				}
			}


			while(this.getRemainingTime() > 0){
				if(getGameObject() instanceof Buzam){
					System.out.println(" ");
					System.out.println("Buzam");
					System.out.println("Main Statament called with dt = " + dt);
					System.out.println("Remaining Time = " + getRemainingTime());
				}


				getMainStatement().advanceTime(dt, this);

				if(getMainStatement().isExecutionComplete()){
					if(getGameObject() instanceof Buzam){
						System.out.println("Complete");
					}
					setVariables(getInitialVariables());
					getMainStatement().Reset();
				}
			}

		} catch(TimeIsUpException t){
			if(getGameObject() instanceof Buzam){
				System.out.println("Times up");
			}

			//Get out of the mainstatement, wait for new advance time
		}
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
	private double RemainingTime = 0.0;
	public static double defaultDT = 0.001;
	
//	public boolean isWellFormed(){
//		return getMainStatement()..isWellFormed();
//	}
}

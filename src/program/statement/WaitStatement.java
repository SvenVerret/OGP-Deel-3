package program.statement;

import java.util.HashSet;

import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.expression.ValueExpression;

public class WaitStatement extends Statement{

	public WaitStatement(Expression<Double> time, SourceLocation sourceLocation){
		super(sourceLocation);
		WaitDuration = time;
		
	}
	
	@Override
	public void advanceTime(double dt,Program program) {
		if(FirstExecution){
			setRemainingWaitTime((double) WaitDuration.evaluate(program));
			FirstExecution = false;
		}
		if(!isExecutionComplete()){
			
			while(this.getRemainingWaitTime() > 0){
				this.decreaseRemainingWaitTime();
				program.decreaseRemainingTime();
				
			}
			if(this.getRemainingWaitTime() == 0.0){
				ExecutionDone = true;
			}
		}
	}

	@Override
	public boolean isExecutionComplete() {
		return ForceReset || ExecutionDone;
	}

	@Override
	public void forceReset() {
		ForceReset = true;
	}

	@Override
	public void Reset() {
		ExecutionDone = false;
		FirstExecution = true;
	}
	
	private double getRemainingWaitTime() {
		return RemainingWaitTime;
	}
	private void setRemainingWaitTime(double remainingWaitTime) {
		RemainingWaitTime = remainingWaitTime;
	}
	
	public void decreaseRemainingWaitTime(double time){
		double newtime = RemainingWaitTime - time;
		if (newtime <= 0)
			RemainingWaitTime = 0.0;
		else
			RemainingWaitTime = newtime;
	}

	public void decreaseRemainingWaitTime(){
		decreaseRemainingWaitTime(Program.defaultDT);
	}
	
	private final Expression<Double> WaitDuration;
	private double RemainingWaitTime = 0.0;
	
	private boolean ForceReset = false;
	private boolean ExecutionDone = false;	
	private boolean FirstExecution = true;
	@Override
	public boolean isWellFormed(HashSet<String> parentStatements) {
		return true;
	}



}

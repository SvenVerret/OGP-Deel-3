package program.statement;

import java.util.Map;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;
import program.expression.ValueExpression;
import program.type.Type;


public class WaitStatement extends Statement{


	/**
	 * 
	 * @param duration
	 *  
	 */

	public WaitStatement(Expression<Double> duration, SourceLocation sourceLocation){
		super(sourceLocation);
		WaitDuration = ((ValueExpression<Double>) duration).evaluate();
	}
	
	// time in ms
	public double getWaitDuration() {
		return WaitDuration;
	}	
	private final double WaitDuration;
	
	public double getTimePassed() {
		return TimePassed;
	}
	public void setTimePassed(double timePassed) {
		this.TimePassed = timePassed;
	}
	public void addTimePassed(double time){
		this.TimePassed += time;
	}
	private double TimePassed = 0.0;
	

	@Override
	public boolean isExecutionComplete() {
		return getWaitDuration() == getTimePassed();
	}

	@Override
	public void forceReset() {
		setTimePassed(0.0);
	}

	@Override
	public void Reset() {
		if(isExecutionComplete()){
			setTimePassed(0.0);
		}
	}

	@Override
	public void advanceTime(double dt,  Map<String, Type> globalVariables) {
		// TODO Auto-generated method stub
	}


}

package program.statement;

import java.util.Map;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;
import program.expression.ValueExpression;
import program.type.Type;


public class WaitStatement extends Statement{

	public WaitStatement(Expression<Double> duration, SourceLocation sourceLocation){
		super(sourceLocation);
		WaitDuration = duration;
		
	}

	public Expression<Double> getWaitDuration() {
		return WaitDuration;
	}	
	private final Expression<Double> WaitDuration;
	
	@Override
	public boolean isExecutionComplete() {
		return false;
	}

	@Override
	public void forceReset() {
	}

	@Override
	public void Reset() {
		if(isExecutionComplete()){
		}
	}

	@Override
	public void advanceTime(double dt,  Map<String, Type> globalVariables) {
		double duration= ((ValueExpression<Double>) WaitDuration).evaluate(globalVariables);
		//return (dt-duration)
	}


}

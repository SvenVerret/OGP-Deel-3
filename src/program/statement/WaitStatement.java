package program.statement;


import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.expression.ValueExpression;


public class WaitStatement extends Statement{

	public WaitStatement(Expression<Double> time, SourceLocation sourceLocation){
		super(sourceLocation);
		WaitDuration = time;
		
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
	public void advanceTime(double dt,Program program) {
		double duration= (double) ((ValueExpression<Double>) WaitDuration).evaluate(program);
		//return (dt-duration)
	}


}

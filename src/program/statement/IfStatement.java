package program.statement;

import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.expression.ValueExpression;

public class IfStatement extends Statement{
	/**
	 * 
	 * @param e
	 * @param ifBody
	 * @param elseBody
	 * @param loc
	 */
	public IfStatement(Expression<Boolean> condition, Statement ifBody, Statement elseBody, 
			SourceLocation sourceLocation){
		super(sourceLocation);
		
		IfBody = ifBody;
		ElseBody = elseBody;
		
		UnevaluatedCondition = ((ValueExpression<Boolean>) condition);
	}
	
	private ValueExpression<Boolean> UnevaluatedCondition;
	private Statement IfBody;
	private Statement ElseBody;
	private boolean ForceReset = false;
	private boolean ExecutionDone = false;
	private Boolean IfBodyCalled = null;

	@Override
	public void advanceTime(double dt,Program program) {
		if (!isExecutionComplete()){
			
			if((Boolean) UnevaluatedCondition.evaluate(program)){
				IfBodyCalled = true;
			}else{
				IfBodyCalled = false;
			}
			program.decreaseRemainingTime();
			
			
			if(IfBodyCalled){
				IfBody.advanceTime(dt, program);
				if(IfBody.isExecutionComplete())
					ExecutionDone = true;
			}else{
				ElseBody.advanceTime(dt, program);
				if(ElseBody.isExecutionComplete())
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
		if(IfBodyCalled)
			IfBody.Reset();
		else
			ElseBody.Reset();
		IfBodyCalled = null;
	}
}

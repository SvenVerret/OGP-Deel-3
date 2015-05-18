package program.statement;

import java.util.Map;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;
import program.expression.ValueExpression;
import program.type.Type;

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
	public void advanceTime(double dt,
			Map<String, Type> globalVariables) {
		if (!isExecutionComplete()){
			if((Boolean) UnevaluatedCondition.evaluate(globalVariables)){
				IfBody.advanceTime(dt, globalVariables);
				IfBodyCalled = true;
			}else{
				ElseBody.advanceTime(dt, globalVariables);
				IfBodyCalled = false;
			}
			ExecutionDone = true;
		}
		
	}
	// eventuele IfBody.isExecutionComplete();

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

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
	public IfStatement(Expression<?> condition, Statement ifBody, Statement elseBody, 
			SourceLocation sourceLocation){
		super(sourceLocation);
		
		IfBody = ifBody;
		ElseBody = elseBody;
		
		UnevaluatedCondition = ((ValueExpression<?>) condition);
		EvaluatedCondition = (Boolean) UnevaluatedCondition.evaluate();
	}
	
	private ValueExpression<?> UnevaluatedCondition;
	private Boolean EvaluatedCondition;
	private Statement IfBody;
	private Statement ElseBody;
	private boolean ForceReset = false;
	private boolean ExecutionDone = false;

	@Override
	public void advanceTime(double dt,
			Map<String, Type> globalVariables) {
		if (!isExecutionComplete() && !ExecutionDone && !ForceReset){
			if(EvaluatedCondition){
				IfBody.advanceTime(dt, globalVariables);
			}else{
				ElseBody.advanceTime(dt, globalVariables);
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
		if(EvaluatedCondition){
			IfBody.Reset();
		}else{
			ElseBody.Reset();
		}
	}
}

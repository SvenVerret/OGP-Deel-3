package program.statement;

import java.util.Map;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;
import program.expression.ValueExpression;
import program.type.Type;

public class WhileStatement extends Statement{

	/**
	 * 
	 * @param condition
	 * @param body
	 *  
	 */
	public WhileStatement(Expression<?> condition, Statement body, 
			SourceLocation sourceLocation){
		super(sourceLocation);
		WhileBody = body;
		UnevaluatedCondition = ((ValueExpression<?>) condition);
	}
	
	
	private ValueExpression<?> UnevaluatedCondition;
	private Statement WhileBody;
	private boolean ForceReset = false;
	private boolean ExecutionDone = false;
	

	@Override
	public void advanceTime(double dt,
			Map<String, Type> globalVariables) {
		if(!isExecutionComplete() && !ExecutionDone && !ForceReset){
			while((boolean) UnevaluatedCondition.evaluate()){
				WhileBody.advanceTime(dt, globalVariables);
			}
			ExecutionDone = true;
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
		WhileBody.Reset();
	}
}

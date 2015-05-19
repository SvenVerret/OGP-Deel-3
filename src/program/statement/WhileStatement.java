package program.statement;

import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.expression.ValueExpression;

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

	// WORDT CONDITION GEUPDATED?
	@Override
	public void advanceTime(double dt, Program program) {
		while(!isExecutionComplete() && (Boolean) UnevaluatedCondition.evaluate(program)){
			WhileBody.advanceTime(dt, program);
//			if(break is called in body){
//				break;
//			}
		}
		if(!ForceReset){
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

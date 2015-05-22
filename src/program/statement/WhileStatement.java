package program.statement;

import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.util.BreakException;

public class WhileStatement extends Statement{

	/**
	 * 
	 * @param condition
	 * @param body
	 *  
	 */
	public WhileStatement(Expression<Boolean> condition, Statement body, 
			SourceLocation sourceLocation){
		super(sourceLocation);
		WhileBody = body;
		UnevaluatedCondition = condition;
	}

	private Expression<Boolean> UnevaluatedCondition;
	private Statement WhileBody;
	private boolean ForceReset = false;
	private boolean ExecutionDone = false;

	@Override
	public void advanceTime(double dt, Program program) {
		try{
			while(!isExecutionComplete() && (Boolean) UnevaluatedCondition.evaluate(program)){
				program.decreaseRemainingTime();
				WhileBody.advanceTime(dt, program);
				if(WhileBody.isExecutionComplete()){
					WhileBody.Reset();
				}
			}

			// Execution is done if the while condition isn't true anymore.
			if(!(Boolean) UnevaluatedCondition.evaluate(program)){
				ExecutionDone = true;
			}

		} catch(BreakException b) {
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

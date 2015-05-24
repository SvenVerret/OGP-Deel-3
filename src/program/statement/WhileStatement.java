package program.statement;

import java.util.HashSet;

import jumpingalien.model.Buzam;
import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.util.BreakException;
import program.util.TimeIsUpException;

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
	private boolean JumpInLoop = false;
	private boolean ForceReset = false;
	private boolean ExecutionDone = false;

	@Override
	public void advanceTime(double dt, Program program) {
		try{
			
			if(JumpInLoop){
				JumpInLoop = false;
				WhileBody.advanceTime(dt, program);
				
				if(WhileBody.isExecutionComplete() && (Boolean) UnevaluatedCondition.evaluate(program)){
					System.out.println("While reset");
					WhileBody.Reset();
				}
			}
			
			while(!isExecutionComplete() && (Boolean) UnevaluatedCondition.evaluate(program)){
				program.decreaseRemainingTime();
				WhileBody.advanceTime(dt, program);
				if(WhileBody.isExecutionComplete()){
					System.out.println("While reset");
					WhileBody.Reset();
				}
			}

			// Execution is done if the while condition isn't true anymore.
			if(!(Boolean) UnevaluatedCondition.evaluate(program)){
				ExecutionDone = true;
				System.out.println("While done");
			}

		} catch(BreakException b) {
			ExecutionDone = true;
		} catch(TimeIsUpException t){
			JumpInLoop = true;
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
		JumpInLoop = false;
		WhileBody.Reset();
	}

	@Override
	public boolean isWellFormed(HashSet<String> parentStatements) {
		parentStatements.add("While");
		return WhileBody.isWellFormed(parentStatements);
	}
}

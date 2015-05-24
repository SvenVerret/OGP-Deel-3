package program.statement;

import java.util.HashSet;

import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.expression.ValueExpression;

public class PrintStatement extends Statement{

	public PrintStatement(Expression<?> value, SourceLocation sourceLocation){
		super(sourceLocation);
		ValueToBePrinted = value;
	}

	@Override
	public void advanceTime(double dt,Program program) {
		if(!isExecutionComplete()){
			
			System.out.println(getValueToBePrinted().evaluate(program));
			ExecutionDone = true;
			program.decreaseRemainingTime();
		}
	}
	
	public Expression<?> getValueToBePrinted() {
		return ValueToBePrinted;
	}
	private final Expression<?> ValueToBePrinted;
	

	private boolean ForceReset = false;
	private boolean ExecutionDone = false;

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
		if(isExecutionComplete()){
			ExecutionDone = false;
		}
	}

	@Override
	public boolean isWellFormed(HashSet<String> parentStatements) {
		return true;
	}

}

package program.statement;

import java.util.HashSet;

import program.Program;
import program.util.BreakException;
import jumpingalien.part3.programs.SourceLocation;


public class BreakStatement extends Statement{

	public BreakStatement(SourceLocation sourceLocation){
		super(sourceLocation);
	}

	@Override
	public void advanceTime(double dt, Program program) throws BreakException {
		if(!isExecutionComplete()){
			ExecutionDone = true;
			throw new BreakException();
		}
	}
	
	@Override
	public boolean isExecutionComplete() {
		return ForceReset || ExecutionDone;
	}

	@Override
	public void forceReset() {
		ForceReset = false;	
	}

	@Override
	public void Reset() {
		ExecutionDone = false;	
	}

	@Override
	public boolean isWellFormed(HashSet<String> parentStatements) {
		if (parentStatements.contains("For") || parentStatements.contains("While")){
			return true;
		}else{
			return false;
		}
	}

	private boolean ForceReset = false;
	private boolean ExecutionDone = false;

}

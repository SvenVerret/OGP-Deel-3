package program.statement;

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
	
	private boolean ForceReset = false;
	private boolean ExecutionDone = false;

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
}

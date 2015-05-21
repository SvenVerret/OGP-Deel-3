package program.statement;

import program.Program;
import jumpingalien.part3.programs.SourceLocation;

public class MoveStatement extends Statement{

	public MoveStatement( SourceLocation sourcelocation) {
		super(sourcelocation);
	}

	@Override
	public void advanceTime(double dt, Program program) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isExecutionComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void forceReset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Reset() {
		// TODO Auto-generated method stub
		
	}
}

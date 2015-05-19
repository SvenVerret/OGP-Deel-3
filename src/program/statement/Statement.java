package program.statement;
import java.util.Map;

import program.Program;
import program.type.Type;
import jumpingalien.part3.programs.SourceLocation;



public abstract class Statement{

	public Statement(SourceLocation sourceLocation) {
		SL = sourceLocation;
	}

	public SourceLocation getSourceLocation() {
		return SL;
	}

	private final SourceLocation SL;
	
	
	
	public abstract void advanceTime(double dt, Program program);
	
	public static double defaultDT = 0.001;
	
	// state bijhouden als interrupt (door dt)
	public abstract boolean isExecutionComplete();
	
	// illegal operation -> execution stops (force quit)
	public abstract void forceReset();
	
	// reset -> global variables re-initialised en begin programma aangeduid voor te beginnen
	// CASCADING RESET -> herstarten van programma als gedaan is
	public abstract void Reset();
	
	
	
	
	
	// program is finished -> finished true & program reset -> volgende advanceTime terug bij begin beginnen

	// print e -> print evaluation of expression
	
}


package program.statement;

import java.util.HashSet;
import java.util.List;

import program.Program;
import program.util.BreakException;
import jumpingalien.part3.programs.SourceLocation;


public class SequenceStatement extends Statement{

	public SequenceStatement(List<Statement> statements, SourceLocation sourceLocation){
		super(sourceLocation);
		lst = statements;
	}

	public List<Statement> getLst(){
		return lst;
	}

	private List<Statement> lst;
	private boolean ForceReset = false;
	private boolean ExecutionDone = false;


	@Override
	public void advanceTime(double dt,Program program) {
		try{
			if(!isExecutionComplete()){

				for (Statement statement: getLst()){
					if(statement != null && !statement.isExecutionComplete()){
						statement.advanceTime(dt, program);
					}
				}
				if(CheckExecutionDone())
					ExecutionDone = true;	
			}
		} catch(BreakException b){
			ExecutionDone = true;
			// MAKE SURE THAT THE LOOP ABOVE IS ALSO STOPPED
			throw new BreakException();
		}
	}

	/**
	 * Checks if all statements of the sequence are executed.
	 * @return boolean
	 */
	private boolean CheckExecutionDone(){
		for(Statement statement: getLst()){
			if(!statement.isExecutionComplete())
				return false;
		}
		return true;
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
		for (Statement statement: getLst()){
			statement.Reset();
		}
	}

	@Override
	public boolean isWellFormed(HashSet<String> parentStatements) {
		for(Statement statement: getLst()){
			if(!statement.isWellFormed(parentStatements))
				return false;
		}
		return true;

	}
}

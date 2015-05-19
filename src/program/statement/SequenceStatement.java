package program.statement;

import java.util.List;

import program.Program;
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
		lst = getLst();
		
		for (Statement statement: getLst()){
			
			//getdt and decrease dt
//			if (dt <0.001){
//				break;
//			}
			
			if(statement != null && !statement.isExecutionComplete()){
				statement.advanceTime(dt, program);
			}
		}
		ExecutionDone = true;	
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
}

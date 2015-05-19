package program.statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import program.expression.Expression;
import program.type.Type;
import jumpingalien.part3.programs.SourceLocation;


public class SequenceStatement extends Statement{
	/**
	 * 
	 * @param statements
	 *  
	 */
	public SequenceStatement(List<Statement> statements, SourceLocation sourceLocation){
		
		super(sourceLocation);
		lst = statements;
		
	}

	
	@Override
	public void advanceTime(double dt, Map<String, Type> globalVariables) {
		lst = getLst();
		
		for (Statement statement: getLst()){
			
			//getdt and decrease dt
			if (dt <0.001){
				break;
			}
			if(!statement.isExecutionComplete()){
				statement.advanceTime(dt, globalVariables);
			}

		}
			
	}


	public List<Statement> getLst(){
		return lst;
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
	
	
	private List<Statement> lst;
}

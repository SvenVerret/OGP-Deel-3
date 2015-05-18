package program.statement;

import java.util.HashMap;
import java.util.List;

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
	}

	@Override
	public void advanceTime(double dt,
			HashMap<String, Expression<? extends Type>> variables) {s
		
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

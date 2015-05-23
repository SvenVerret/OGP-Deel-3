package program.expression;

import program.Program;
import jumpingalien.part3.programs.SourceLocation;


public class SelfExpression extends Expression<SourceLocation>{

	public SelfExpression(SourceLocation sourcelocation) {
		super(sourcelocation);
	}

	public Object evaluate(Program program) throws IllegalStateException {
		if(program != null && program.getGameObject() != null){
			return program.getGameObject();
		} else{
			throw new IllegalStateException("Self: no program or gameobject");
		}
	}
}



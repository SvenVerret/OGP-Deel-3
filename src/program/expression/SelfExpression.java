package program.expression;

import program.Program;
import jumpingalien.model.GameObject;
import jumpingalien.part3.programs.SourceLocation;


public class SelfExpression extends Expression<SourceLocation>{

	public SelfExpression(SourceLocation sourcelocation) {
		super(sourcelocation);
	}
	

	public Expression<GameObject> evaluate(Program program) throws IllegalStateException {
		if(program != null && program.getGameObject() != null){
			return new ValueExpression(program.getGameObject(), getSourceLocation());
		} else{
			throw new IllegalStateException("Self: no program or gameobject");
		}
	}
}



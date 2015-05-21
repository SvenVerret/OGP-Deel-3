package program.expression;

import program.Program;
import jumpingalien.model.GameObject;
import jumpingalien.part3.programs.SourceLocation;


public class SelfExpression{

	public SelfExpression(SourceLocation sourcelocation) {
		SL = sourcelocation;
	}
	
	public SourceLocation getSourceLocation() {
		return SL;
	}
	private final SourceLocation SL;

	public GameObject evaluate(Program program) throws IllegalStateException {
		if(program != null && program.getGameObject() != null){
			return program.getGameObject();
		} else{
			throw new IllegalStateException("Self: no program or gameobject");
		}
	}
}



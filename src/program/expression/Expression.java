package program.expression;

import java.util.Map;

import program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Expression<T> {

	public Expression(SourceLocation sourcelocation){
		SL = sourcelocation;
		System.out.println(this.getClass().getTypeParameters());
	}
	
	public SourceLocation getSourceLocation() {
		return SL;
	}

	private final SourceLocation SL;
	
	public abstract T evaluate(Map<String, Type> globalVariables);
}

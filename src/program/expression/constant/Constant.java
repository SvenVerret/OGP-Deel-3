package program.expression.constant;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class Constant<T extends Enum<T> & double> extends Expression{
	
	public Constant(T constant, SourceLocation sourcelocation){
		super(sourcelocation);
		value = constant;
	}

	public Object getValue() {
		return value;
	}

	private final Object value;
}

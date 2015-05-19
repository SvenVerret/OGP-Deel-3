package program.expression;


import program.Program;
import program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public class ValueExpression<T> extends Expression<T> {
	
	// An expression with a value of type Type<?>.
	
	public ValueExpression(Object value,SourceLocation sourcelocation) {
		super(sourcelocation);
		this.value = value;	
		}
	
	protected Object value;
	public void setValue(Object value){
		this.value = value;
	}
	
	@Override
	public Object evaluate(Program program) {
		return value;
	}

}

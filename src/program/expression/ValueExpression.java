package program.expression;


import program.Program;
import jumpingalien.part3.programs.SourceLocation;

public class ValueExpression<T> extends Expression<T> {

	// An expression with a value of type Type<?>.

	public ValueExpression(T value,SourceLocation sourcelocation) {
		super(sourcelocation);
		this.value = value;	
	}

	protected T value;
	
	public void setValue(T value){
		this.value = value;
	}
	
	public T getValue(){
		return value;
	}

	@Override
	public T evaluate(Program program) {
		return getValue();
	}

}

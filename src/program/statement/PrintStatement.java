package program.statement;

import java.util.HashMap;
import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;
import program.expression.ValueExpression;
import program.type.Type;

public class PrintStatement extends Statement{

	public PrintStatement(Expression<?> value, SourceLocation sourceLocation){
		super(sourceLocation);
		ValueToBePrinted = (ValueExpression<?>) value;
	}

	@Override
	public void advanceTime(double dt,
			HashMap<String, Expression<? extends Type>> variables) {
		if(!isExecutionComplete()){
			System.out.println(getValueToBePrinted().evaluate());
			setValueIsPrinted(true);
		}
	}
	
	public ValueExpression<?> getValueToBePrinted() {
		return ValueToBePrinted;
	}
	private final ValueExpression<?> ValueToBePrinted;
	
	public boolean isValueIsPrinted() {
		return ValueIsPrinted;
	}
	public void setValueIsPrinted(boolean valueIsPrinted) {
		ValueIsPrinted = valueIsPrinted;
	}
	private boolean ValueIsPrinted=false;


	@Override
	public boolean isExecutionComplete() {
		return isValueIsPrinted();
	}

	@Override
	public void forceReset() {
		setValueIsPrinted(false);
	}

	@Override
	public void Reset() {
		if(isExecutionComplete()){
			setValueIsPrinted(false);
		}
	}




}

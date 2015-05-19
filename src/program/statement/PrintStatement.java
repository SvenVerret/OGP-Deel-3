package program.statement;

import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.expression.ValueExpression;

public class PrintStatement extends Statement{

	public PrintStatement(Expression<?> value, SourceLocation sourceLocation){
		super(sourceLocation);
		ValueToBePrinted = (ValueExpression<?>) value;
	}

	@Override
	public void advanceTime(double dt,Program program) {
		if(!isExecutionComplete()){
			System.out.println(getValueToBePrinted().evaluate(program));
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

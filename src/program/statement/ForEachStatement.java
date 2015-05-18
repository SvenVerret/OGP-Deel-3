package program.statement;

import java.util.HashMap;
import java.util.Map;

import program.Program;
import program.expression.Expression;
import program.expression.ValueExpression;
import program.expression.VariableValueExpression;
import program.type.Type;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;

public class ForEachStatement extends Statement{

	/**
	 * 
	 * @param variableName
	 * @param variableKind
	 * @param where
	 * @param sort
	 * @param sortDirection
	 * @param body
	 *  
	 */
	@SuppressWarnings("unchecked")
	public ForEachStatement(String variableName, Kind variableKind, Expression<?> where, 
			Expression<?> sort, SortDirection sortDirection, Statement body, 
			SourceLocation sourceLocation) throws IllegalArgumentException{
		super(sourceLocation);
		if(variableName == null || variableKind == null || where == null){
			throw new IllegalArgumentException("Null values");
		}
		
		if(((ValueExpression<?>) where).evaluate() != boolean.class){
			throw new IllegalArgumentException("Where");
		}else{
			WhereExpression = (ValueExpression<Boolean>) where;
		}
		
		if(((ValueExpression<?>) sort).evaluate() != double.class){
			throw new IllegalArgumentException("Sort");
		}else{
			SortExpression = (ValueExpression<Double>) sort;
			SortDirection = sortDirection;
		}
		ForBody = body;
	}
	
	
	@Override
	public void advanceTime(double dt, Map<String, Type> globalVariables) {

		program.getGameObject().getWorld();
		action = filter(a =
		
	}


	private void ConstructAllVariablesWithKind(Kind VariableKind){
		
	}
	
	private void FilterVariables(where){
		
	}
	private VariableValueExpression<?> IterationVariable;
	private ValueExpression<Boolean> WhereExpression;
	private ValueExpression<Double> SortExpression;
	private SortDirection SortDirection;
	private Statement ForBody;
	
	private boolean ForceReset = false;
	private boolean ExecutionDone = false;


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

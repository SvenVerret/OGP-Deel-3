package program.expression;


import jumpingalien.part3.programs.SourceLocation;
import program.Program;

public class GetTileExpression extends Expression<SourceLocation>{
	
	public GetTileExpression(Expression<?> x, Expression<?> y,
			SourceLocation sourceLocation){
		super(sourceLocation);
		this.x = (ValueExpression<Integer>)x;
		this.y = (ValueExpression<Integer>)y;
		


	}

	public Integer getXValue(){
		return x.getValue();
	}

	public Integer getYValue(){
		return y.getValue();
	}
	
	@Override
	public Object evaluate(Program program) {
		
		int[] result = program.getGameObject().getWorld().convertXYtoXTYT(getXValue(),getYValue());
		
		return new ValueExpression<int[]>(result, getSourceLocation());
	}

	private ValueExpression<Integer> x;
	private ValueExpression<Integer> y;

	//return ValueExpression
}

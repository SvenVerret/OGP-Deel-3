package program.expression.inspector;


import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.expression.TileExpression;

public class IsWaterExpression extends Inspector<TileExpression>{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public IsWaterExpression(Expression<?> expr, SourceLocation sourceLocation){
		super(expr, sourceLocation);
	}

	@Override
	public Boolean evaluate(Program program) {
		
		TileExpression value = getExpression();
		
		int posx = value.getXValue();
		int posy = value.getYValue();
		
		
		int feature = program.getGameObject().getWorld().getGeologicalFeature(posx, posy);
		
		if (feature == 2)
			return true;
		else
			return false;
		
	}
}

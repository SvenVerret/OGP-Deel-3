package program.expression.inspector;


import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.expression.TileExpression;

public class IsPassableExpression extends Inspector<TileExpression>{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public IsPassableExpression(Expression<?> expr, SourceLocation sourceLocation){
		super(expr, sourceLocation);
	}

	@Override
	public Boolean evaluate(Program program) {
		
		TileExpression value = getExpression();
		
		int posx = value.getXValue();
		int posy = value.getYValue();
		
		
		return  program.getGameObject().getWorld().isPassableTerrain(posx, posy);
		
	}
}

package program.expression.inspector;


import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.expression.GetTileExpression;

public class IsMagmaExpression extends Inspector<GetTileExpression>{

	/**
	 * 
	 * @param expr
	 *  
	 */
	public IsMagmaExpression(Expression<?> expr, SourceLocation sourceLocation){
		super(expr, sourceLocation);
	}

	@Override
	public Boolean evaluate(Program program) {
		
		GetTileExpression value = getExpression();
		
		int posx = value.getXValue();
		int posy = value.getYValue();
		
		
		int feature = program.getGameObject().getWorld().getGeologicalFeature(posx, posy);
		
		if (feature == 3)
			return true;
		else
			return false;
		
	}
}

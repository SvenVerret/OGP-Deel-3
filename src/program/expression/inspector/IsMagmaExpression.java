package program.expression.inspector;


import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.expression.TileExpression;

public class IsMagmaExpression extends Inspector<TileExpression>{

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
		
		TileExpression value = getExpression();
		value.setReturnToPixels();
		int[] pos = value.evaluate(program); 
		
		int feature = program.getGameObject().getWorld().getGeologicalFeature(pos[0], pos[1]);
		
		if (feature == 3)
			return true;
		else
			return false;
		
	}
}

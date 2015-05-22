package program.expression;


import jumpingalien.part3.programs.SourceLocation;
import program.Program;

public class TileExpression extends Expression<SourceLocation>{
	
	public TileExpression(Expression<Double> x, Expression<Double> y,
			SourceLocation sourceLocation){
		super(sourceLocation);
		this.x = x;
		this.y = y;
	}

	@Override
	public int[] evaluate(Program program) {
		
		if(this.getReturnInPixel()){
			int[] result = {((Double)getXValue().evaluate(program)).intValue(),((Double)getYValue().evaluate(program)).intValue()};
			return result;
			
		}else{
			int[] result = program.getGameObject().getWorld().
					convertXYtoXTYT(((Double)getXValue().evaluate(program)).intValue(),((Double)getYValue().evaluate(program)).intValue());
			return result;
		}
	}

	private Boolean getReturnInPixel(){
		return ReturnInPixel;
	}
	public void setReturnToPixels(){
		ReturnInPixel = true;
	}
	public void setReturnToTiles(){
		ReturnInPixel = false;
	}
	private Boolean ReturnInPixel = false;
	
	private Expression<Double> x;
	private Expression<Double> y;
	
	public Expression<Double> getXValue(){
		return x;
	}
	public Expression<Double> getYValue(){
		return y;
	}
}

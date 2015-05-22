package program.statement;

import program.Program;
import program.ProgramFactory.Movement;
import program.expression.Expression;
import program.expression.ValueExpression;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;


public class MoveStatement extends Statement{

	/**
	 * 
	 *  
	 */
	public MoveStatement(Movement movement, Expression<?> direction,SourceLocation sourceLocation){
		super(sourceLocation);
		this.movement = movement;
		
		Direction value = ((ValueExpression<Direction>) direction).getValue();
		
		if(value == Direction.LEFT)
			this.direction = false;
		else if(value== Direction.RIGHT) 
			this.direction = true;
	}

	@Override
	public void advanceTime(double dt, Program program) {
		if(!this.isExecutionComplete()){

			switch(getMovement()){
			case STARTRUN:  
				program.getGameObject().startMoveProgram(getDirection());
				break;
			case STARTJUMP:  
				program.getGameObject().startJumpProgram();
				break;
			case STARTDUCK:  
				program.getGameObject().startDuckProgram();
				break;
			case STOPRUN:  
				program.getGameObject().stopMoveProgram();
				break;
			case STOPJUMP:  
				program.getGameObject().stopJumpProgram();
				break;
			case STOPDUCK:  
				program.getGameObject().stopDuckProgram();
				break;
			}
			
			ExecutionDone = true;
			program.decreaseRemainingTime();
		}

	}
	private final Movement movement;
	private Boolean direction;

	private boolean ForceReset = false;
	private boolean ExecutionDone = false;

	@Override
	public boolean isExecutionComplete() {
		return ForceReset || ExecutionDone;
	}

	@Override
	public void forceReset() {
		ForceReset = true;
	}

	@Override
	public void Reset() {
		ExecutionDone = false;
	}

	public Movement getMovement() {
		return movement;
	}

	public Boolean getDirection() {
		return direction;
	}
}

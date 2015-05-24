package jumpingalien.part1.facade;

import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.model.Mazub;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Facade implements IFacade {
	

	
	public Facade(){
		
	}
	
	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		try{
			return new Mazub(pixelLeftX, pixelBottomY, sprites);
		} catch (OutOfBoundsException e){
			throw new ModelException("Illegal initial position");
		} catch (IllegalArgumentException e){
			if (e.getMessage() == "Sprites")
				throw new ModelException("Illegal sprites");
			else
				throw new ModelException("Illegal velocities");
		}
	}

	
	//OK
	@Override
	public int[] getLocation(Mazub alien) {
		int[] location = {alien.getPosX(),alien.getPosY()};
		return location;
	}

	//OK
	@Override
	public double[] getVelocity(Mazub alien) {
		double [] velocity = {alien.getVelocityX(),alien.getVelocityY()};
		return velocity;
	}

	//OK
	@Override
	public double[] getAcceleration(Mazub alien) {
		double[] acceleration = {alien.getAccXCurr(),alien.getAccYCurr()};
		return acceleration;
	}

	//OK
	@Override
	public int[] getSize(Mazub alien) {
		return alien.getSize();
	}

	
	//OK
	@Override
	public Sprite getCurrentSprite(Mazub alien) {
		Sprite sprite = alien.getCurrentSprite();
		return sprite;
	}

	
	//OK
	@Override
	public void startJump(Mazub alien) {
		try{
			alien.startJump();	
		} catch(IllegalStateException exc){
			throw new ModelException("State Exception: Start Jumping");
		}
	}	
	
	//OK
	@Override
	public void endJump(Mazub alien) {
		try{
			alien.endJump();	
		} catch(IllegalStateException exc){
			throw new ModelException("State Exception: End Jumping");
		}
	}

	//OK
	@Override
	public void startMoveLeft(Mazub alien) {
		alien.startMove(false);

	}

	//OK
	@Override
	public void endMoveLeft(Mazub alien) {
		alien.endMove();
		
	}

	//OK
	@Override
	public void startMoveRight(Mazub alien) {
		alien.startMove(true);
	}

	//OK
	@Override
	public void endMoveRight(Mazub alien) {
		alien.endMove();
		
	}

	//OK
	@Override
	public void startDuck(Mazub alien) {
		alien.startDuck();
	}

	//OK
	@Override
	public void endDuck(Mazub alien) {
		try{
			alien.endDuck();
		} catch(IllegalStateException e){
			throw new ModelException("Illegal State: Ducking");
		}
	}

	@Override
	public void advanceTime(Mazub alien, double dt) {
		try{
			alien.advanceTime(dt);
		}catch(IllegalArgumentException e){
			throw new ModelException("Wrong dT");
		}
	}
}

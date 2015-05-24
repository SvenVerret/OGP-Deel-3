package jumpingalien.part1.tests;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.part1.facade.Facade;
import jumpingalien.part1.facade.IFacade;
import jumpingalien.exception.IllegalVelocityException;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.model.Mazub;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.Test;

public class ModelTest {
	
	
	@Test(expected = ModelException.class)
	public void MazubConstructorException1(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		@SuppressWarnings("unused")
		Mazub alien = facade.createMazub(-100, 0, sprites);
	
	}
	
	@Test(expected = ModelException.class)
	public void MazubConstructorException2(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		@SuppressWarnings("unused")
		Mazub alien = facade.createMazub(0, -100, sprites);
	
	}
	
	
	@Test
	public void startMoveLeftLegal() {
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(20, 0, sprites);
		
		facade.startMoveLeft(alien);
		
		assertTrue(alien.getOrientation() == 'L');
		assertEquals(alien.getVelocityX(), -1*alien.getInitVelocityX(), Util.DEFAULT_EPSILON);
		assertEquals(alien.getAccXCurr(), alien.getAccXBkw(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveLeftWalkWall() {
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		facade.startMoveLeft(alien);
		
		assertFalse(alien.getOrientation() == 'L');
	}
	
	@Test
	public void startMoveRightLegal() {
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(20, 0, sprites);
		
		facade.startMoveRight(alien);
		
		assertTrue(alien.getOrientation() == 'R');
		assertEquals(alien.getVelocityX(), alien.getInitVelocityX(), Util.DEFAULT_EPSILON);
		assertEquals(alien.getAccXCurr(), alien.getAccXFwd(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveRightWalkWall() {
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(1023, 0, sprites);
		
		facade.startMoveLeft(alien);
		
		assertFalse(alien.getOrientation() == 'R');
	}
	
	@Test
	public void endMoveLegal() {
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		facade.startMoveLeft(alien);
		facade.advanceTime(alien, 0.05);
		facade.endMoveLeft(alien);
		
		assertTrue(alien.getLastMove() == 'L');
		assertTrue(alien.getVelocityX() == 0.0);
		assertTrue(alien.getAccXCurr() == 0.0);
		assertTrue(alien.getSumdt() == 0.0);
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.05);
		facade.endMoveRight(alien);
		
		assertTrue(alien.getLastMove() == 'R');
		assertTrue(alien.getVelocityX() == 0.0);
		assertTrue(alien.getAccXCurr() == 0.0);
		assertTrue(alien.getSumdt() == 0.0);

	}
	
	@Test
	public void endMoveWallWalk() {
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(20, 0, sprites);
		
		facade.startMoveLeft(alien);
		facade.advanceTime(alien, 0.2);
		facade.endMoveLeft(alien);
		
		assertTrue(alien.getLastMove() == 'X');
		assertTrue(alien.getVelocityX() == 0.0);
		assertTrue(alien.getAccXCurr() == 0.0);
		assertTrue(alien.getSumdt() == 0.0);
	}
	
	
	@Test
	public void startJumpLegal() {
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.startJump();
		assertTrue(alien.getVelocityY() == 8.0);
		assertTrue(alien.isJumped());
		facade.advanceTime(alien, 0.05);
		
		assertTrue(alien.getPosY() > 0);
		assertTrue(alien.getVelocityY() == 7.5);
		
	}
	
	@Test
	public void startJumpDontJumpInAir() {
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.startJump();
		assertTrue(alien.isJumped());
		facade.advanceTime(alien, 0.05);
		alien.endJump();
		assertFalse(alien.isJumped());
		facade.advanceTime(alien, 0.05);
		
		alien.startJump();
		assertFalse(alien.getVelocityY() == 8.0);
		
	}
	
	@Test
	public void startJumpDontJumpInAir2() {
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 100, sprites);
		
		alien.startJump();
		assertFalse(alien.getVelocityY() == 8.0);
	}
	
	@Test(expected=IllegalStateException.class)
	public void startJumpException() {
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.startJump();
		assertTrue(alien.isJumped());
		facade.advanceTime(alien, 0.05);
		alien.startJump();
			
	}
	
	@Test
	public void endJumpLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.startJump();
		facade.advanceTime(alien, 0.05);
		alien.endJump();
		assertFalse(alien.isJumped());	
	}
	
	@Test(expected=IllegalStateException.class)
	public void endJumpException(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
	
		alien.endJump();
	}
	
	@Test
	public void startDuckLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.startDuck();
		assertTrue(alien.isDucked());
		assertTrue(alien.getMaxVelocityXCurr() == 1.0);
	}
	
	
	@Test
	public void endDuckLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.startDuck();
		assertTrue(alien.isDucked());
		alien.endDuck();
		assertFalse(alien.isDucked());
		assertTrue(alien.getMaxVelocityXCurr() == alien.getMaxVelocityX());
	}
	
	@Test
	public void endDuckInAirLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.startJump();
		alien.advanceTime(0.1);
		alien.startDuck();
		assertTrue(alien.isDucked());
	}
	
	@Test(expected= IllegalStateException.class)
	public void endDuckException(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		assertFalse(alien.isDucked());
		alien.endDuck();
	}
	
	@Test
	public void advanceTimeRightLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);

		alien.startMove(true);
		alien.advanceTime(0.10);
		
		// These values were calculated using Maple.
		assertTrue(alien.getVelocityX()== 1.09);
		assertTrue(alien.getPosX() == 10);
		
		alien.advanceTime(0.15);
		
		assertTrue(alien.getVelocityX()== 1.225);
		assertTrue(alien.getPosX() == 27);
		
	}
	@Test
	public void advanceTimeLeftLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(500, 0, sprites);

		alien.startMove(false);
		alien.advanceTime(0.10);
		
		// These values were calculated using Maple.
		assertTrue(alien.getVelocityX()== -1.09);
		assertTrue(alien.getPosX() == 490);
		
		alien.advanceTime(0.15);
		
		assertTrue(alien.getVelocityX()== -1.225);
		assertTrue(alien.getPosX() == 473);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void advanceTimeException1(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.advanceTime(0.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void advanceTimeException2(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.advanceTime(-0.1);
	}
	
	@Test
	public void getCurrentSpriteLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(50, 0, sprites);
		
		//STANDING UP
		assertEquals(alien.getCurrentSprite(), sprites[0]);
		
		/////////////////////////
		//WAS MOVING TO THE RIGHT
		alien.startMove(true);
		alien.advanceTime(0.05);
		alien.endMove();
		
		for(int i =0; i < 19; i++)
		{
			alien.advanceTime(0.05);
			assertEquals(alien.getCurrentSprite(), sprites[2]);
		}
		alien.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[0]);
		
		//WAS MOVING TO THE LEFT
		alien.startMove(false);
		alien.advanceTime(0.05);
		alien.endMove();
		
		for(int i =0; i < 19; i++)
		{
			alien.advanceTime(0.05);
			assertEquals(alien.getCurrentSprite(), sprites[3]);
		}
		alien.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[0]);
		

		//////////////////////
		//JUMPING TO THE RIGHT
		alien.startJump();
		alien.startMove(true);
		alien.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[4]);
		alien.endJump();
		assertEquals(alien.getCurrentSprite(), sprites[4]);
		alien.advanceTime(0.05);
		alien.endMove();
		
		//BACK TO THE GROUND
		for(int i =0; i <= 30; i++)
		{
			alien.advanceTime(0.1);
		}
		
		//JUMPING TO THE LEFT
		alien.startJump();
		alien.startMove(false);
		alien.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[5]);
		alien.endJump();
		assertEquals(alien.getCurrentSprite(), sprites[5]);
		alien.advanceTime(0.05);
		alien.endMove();
		
		//BACK TO THE GROUND
		for(int i =0; i <= 30; i++)
		{
			alien.advanceTime(0.1);
		}
		
		//CHANGING DIRECTION IN AIR
		alien.startJump();
		alien.startMove(true);
		alien.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[4]);
		alien.endMove();
		
		alien.startMove(false);
		alien.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[5]);
		alien.endMove();
		
		//BACK TO THE GROUND
		for(int i =0; i <= 30; i++)
		{
			alien.advanceTime(0.1);
		}
		
		//////////
		//DUCKING
		alien.startDuck();
		alien.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[1]);
		
		//DUCKING AND MOVING RIGHT
		alien.startMove(true);
		assertEquals(alien.getCurrentSprite(), sprites[6]);
		alien.endMove();
		
		//DUCKING AND WAS MOVING RIGHT
		for(int i =0; i < 19; i++)
		{
			alien.advanceTime(0.05);
			assertEquals(alien.getCurrentSprite(), sprites[6]);
		}
		alien.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[1]);
		
		//DUCKING AND MOVING LEFT
		alien.startMove(false);
		assertEquals(alien.getCurrentSprite(), sprites[7]);
		alien.endMove();
		
		//DUCKING AND WAS MOVING LEFT
		for(int i =0; i < 19; i++)
		{
			alien.advanceTime(0.05);
			assertEquals(alien.getCurrentSprite(), sprites[7]);
		}
		alien.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[1]);
		
		//STANDING UP
		alien.endDuck();
		assertEquals(alien.getCurrentSprite(), sprites[0]);
		
		//////////////////////
		//MOVING TO THE RIGHT
		alien.startMove(true);
		for(int i =0; i < 10; i++)
		{
			assertEquals(alien.getCurrentSprite(), sprites[8+i]);
			alien.advanceTime(0.076);
			assertEquals(alien.getCurrentSprite(), sprites[8+1+i]);	
		}
		alien.advanceTime(0.076);
		assertEquals(alien.getCurrentSprite(), sprites[8]);
	

		for(int i =0; i < m; i++){
			assertEquals(alien.getCurrentSprite(), sprites[(int) (8+(alien.getSumdt()/0.075))]);
			alien.advanceTime(0.045);
		}
		alien.endMove();
		
		
		//MOVING TO THE LEFT
		alien.startMove(false);
			for(int i =0; i < m; i++){
				assertEquals(alien.getCurrentSprite(), sprites[9+m+i]);
				alien.advanceTime(0.076);
				assertEquals(alien.getCurrentSprite(), sprites[9+m+1+i]);
			}
		alien.advanceTime(0.076);
		assertEquals(alien.getCurrentSprite(), sprites[9+m]);
		
		for(int i =0; i < m; i++){
			assertEquals(alien.getCurrentSprite(), sprites[(int) (9+m+(alien.getSumdt()/0.075))]);
			alien.advanceTime(0.045);
		}
		alien.endMove();
		
		
	}

	
	@Test
	public void getPosXLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		assertTrue(alien.getPosX() == 512);
	}
	
	@Test
	public void getPosYLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 20, sprites);
		assertTrue(alien.getPosY() == 20);
	}
	
	@Test
	public void setPosXLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.setPosX(0);
		assertTrue(alien.getPosX() == 0);
		
		alien.setPosX(512);
		assertTrue(alien.getPosX() == 512);
		
		alien.setPosX(1023);
		assertTrue(alien.getPosX() == 1023);
		
		
	}
	
	@Test(expected = OutOfBoundsException.class)
	public void setPosXNegativeException(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.setPosX(-10);
		assertFalse(alien.getPosX() == -10);
	}
	
	@Test(expected = OutOfBoundsException.class)
	public void setPosXBigException1(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.setPosX(1200);
		assertFalse(alien.getPosX() == 1200);
	}
	
	@Test(expected = OutOfBoundsException.class)
	public void setPosXBigException2(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(512, 0, sprites);
		
		alien.setPosX(1024);
		assertFalse(alien.getPosX() == 1024);
	}
	
	@Test
	public void setPosYLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 20, sprites);
		alien.setPosY(0);
		assertTrue(alien.getPosY() == 0);
		
		alien.setPosY(100);
		assertTrue(alien.getPosY() == 100);
		
		alien.setPosY(767);
		assertTrue(alien.getPosY() == 767);
	}
	
	@Test(expected = OutOfBoundsException.class)
	public void setPosYNegativeException(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		alien.setPosY(-1);
		assertFalse(alien.getPosY() == -1);
	}
	
	@Test(expected = OutOfBoundsException.class)
	public void setPosYBigException1(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		alien.setPosY(1000);
		assertFalse(alien.getPosY() == 768);
	}
	
	@Test(expected = OutOfBoundsException.class)
	public void setPosYBigException2(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		alien.setPosY(1024);
		assertFalse(alien.getPosY() == 1024);
	}
	
	@Test
	public void getHeightLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		alien.setHeight(97);
		assertTrue(alien.getHeight() == 97);
	}
	
	@Test
	public void setHeightLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		alien.setHeight(1);
		assertTrue(alien.getHeight() == 1);
		alien.setHeight(100);
		assertTrue(alien.getHeight() == 100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setHeightException1(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		alien.setHeight(0);
		assertFalse(alien.getHeight() == 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setHeightException2(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		alien.setHeight(-10);
		assertFalse(alien.getHeight() == -10);
	}

	@Test
	public void getWidthLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		alien.setWidth(60);
		assertTrue(alien.getWidth() == 60);
	}
	
	@Test
	public void setWidthLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		alien.setWidth(1);
		assertTrue(alien.getWidth() == 1);
		alien.setWidth(100);
		assertTrue(alien.getWidth() == 100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setWidthException1(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		alien.setWidth(0);
		assertFalse(alien.getWidth() == 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setWidthException2(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		alien.setWidth(-10);
		assertFalse(alien.getWidth() == -10);
	}
	
	@Test
	public void getOrientationLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		assertTrue(alien.getOrientation() == 'X');
		alien.startMove(false);
		//Alien walks against the wall: orientation stays X
		assertTrue(alien.getOrientation() == 'X');
		alien.endMove();
		
		alien.startMove(true);
		assertTrue(alien.getOrientation() == 'R');
		facade.advanceTime(alien, 0.2);
		assertTrue(alien.getOrientation() == 'R');
		alien.endMove();
		
		alien.startMove(false);
		assertTrue(alien.getOrientation() == 'L');
		facade.advanceTime(alien, 0.2);
		assertTrue(alien.getOrientation() == 'L');
		alien.endMove();
	}
	
	@Test
	public void setOrientationLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		assertTrue(alien.getOrientation() == 'X');
		alien.setOrientation('R');
		assertTrue(alien.getOrientation() == 'R');
		
		alien.setOrientation('L');
		assertTrue(alien.getOrientation() == 'L');
	}
	
	@Test
	public void getLastMoveLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(50, 0, sprites);
		
		alien.startMove(true);
		alien.endMove();
		assertTrue(alien.getLastMove() == 'R');
		
		for(int i =0; i < 19; i++)
		{
			alien.advanceTime(0.05);
			alien.getCurrentSprite();
			assertTrue(alien.getLastMove() == 'R');
		}
		
		alien.advanceTime(0.05);
		alien.getCurrentSprite();
		assertTrue(alien.getLastMove() == 'X');
		
		alien.startMove(false);
		alien.endMove();
		assertTrue(alien.getLastMove() == 'L');
		
		for(int i =0; i < 19; i++)
		{
			alien.advanceTime(0.05);
			alien.getCurrentSprite();
			assertTrue(alien.getLastMove() == 'L');
		}
		
		alien.advanceTime(0.05);
		alien.getCurrentSprite();
		assertTrue(alien.getLastMove() == 'X');
	}
	
	@Test
	public void setLastMoveLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(50, 0, sprites);
		
		alien.setLastMove('R');
		assertTrue(alien.getLastMove() == 'R');
		
		alien.setLastMove('L');
		assertTrue(alien.getLastMove() == 'L');
		
		alien.setLastMove('X');
		assertTrue(alien.getLastMove() == 'X');
	}
	
	
	@Test
	public void getsetVelocityXLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(50, 0, sprites);
		
		assertTrue(alien.getVelocityX() == 0.0);
	
		alien.setVelocityX(1.0);
		assertTrue(alien.getVelocityX() == 1.0);
	}
	
	@Test(expected = IllegalVelocityException.class)
	public void setVelocityXException(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(50, 0, sprites);
		
		alien.setMaxVelocityXCurr(3.0);
		alien.setVelocityX(3.1);
		assertFalse(alien.getVelocityX() == 3.1);
	}
	
	@Test(expected = IllegalVelocityException.class)
	public void setVelocityXNegativeException(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(50, 0, sprites);
		
		alien.setMaxVelocityXCurr(3.0);
		alien.setVelocityX(-3.1);
		assertFalse(alien.getVelocityX() == -3.1);
	}
	
	@Test
	public void getsetVelocityYLegal(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(50, 0, sprites);
		
		assertTrue(alien.getVelocityY() == 0.0);
	
		alien.setVelocityY(1.0);
		assertTrue(alien.getVelocityY() == 1.0);
	}
	
	@Test(expected = IllegalVelocityException.class)
	public void setVelocityYException(){
		IFacade facade = new Facade();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(50, 0, sprites);
		
		alien.setPosY(0);
		alien.setVelocityY(-1.0);
		assertFalse(alien.getVelocityY() == -1.0);
	}
	
	
	
	
	
	
	
	@Test
    public void getInitVelocityLegal(){
           
        int m = 10;
        Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
       
        //Standard constructor
        Mazub alien =  new Mazub(50, 0, sprites);
        assertTrue(alien.getInitVelocityX() == 1.0);
       
        //2nd constructor
        Mazub alien2 = new Mazub(50, 0, sprites, 1.2, 3.0);
        assertTrue(alien2.getInitVelocityX() == 1.2);
	}
	
	@Test
	public void getMaxVelocityLegal(){
		int m = 10;
        Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
       
        //Standard constructor
        Mazub alien =  new Mazub(50, 0, sprites);
        assertTrue(alien.getMaxVelocityX() == 3.0);
       
        //2nd constructor
        Mazub alien2 = new Mazub(50, 0, sprites, 1, 5.0);
        assertTrue(alien2.getMaxVelocityX() == 5.0);
	}
	
	@Test
	public void getsetMaxVelocityXCurrLegal(){
		int m = 10;
        Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
        
        //Initial maxVelocityXCurr with standard constructor
        Mazub alien =  new Mazub(50, 0, sprites);
        assertTrue(alien.getMaxVelocityXCurr() == 3.0);
        
        alien.setMaxVelocityXCurr(2.0);
        assertTrue(alien.getMaxVelocityXCurr() == 2.0);
        
        //Max velocity while ducking
        alien.startDuck();
        assertTrue(alien.getMaxVelocityXCurr() == 1.0);
        
        
        //New max velocity with second constructor
        //First standing, then ducking, end ducking and walking
        Mazub alien2 =  new Mazub(50, 0, sprites, 2.0,6.0);
        assertTrue(alien2.getMaxVelocityXCurr() == 6.0);
        alien2.startDuck();
        assertTrue(alien2.getMaxVelocityXCurr() == 1.0);
        alien2.endDuck();
        assertTrue(alien2.getMaxVelocityXCurr() == 6.0);
	}
	
	@Test
	public void getsetAccXCurr(){
		int m = 10;
        Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
        
        Mazub alien =  new Mazub(50, 0, sprites);
        
        alien.startMove(true);
        assertTrue(alien.getAccXCurr() == 0.9);
        alien.endMove();
        assertTrue(alien.getAccXCurr() == 0.0);
        
        alien.setAccXCurr(2.0);
        assertTrue(alien.getAccXCurr() == 2.0);
        
        
        //At the boundaries
        
        //Left side
        Mazub alien2 =  new Mazub(0, 0, sprites);
        alien.startMove(false);
        assertTrue(alien2.getAccXCurr() == 0.0);
        
        //Right side
        Mazub alien3 = new Mazub(1023,0, sprites);
        alien.startMove(true);
        assertTrue(alien3.getAccXCurr() == 0.0);
        
	}
	
	@Test
	public void isSetJumpedLegal(){
		int m = 10;
        Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
        
        Mazub alien =  new Mazub(50, 0, sprites);
        
        alien.startJump();
        assertTrue(alien.isJumped() == true);
        
        alien.endJump();
        assertTrue(alien.isJumped() == false);
        
        alien.setJumped(true);
        assertTrue(alien.isJumped() == true);

        alien.setJumped(false);
        assertTrue(alien.isJumped() == false);
	}
	
	
	@Test
	public void isSetDuckedLegal(){
		int m = 10;
        Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
        Mazub alien =  new Mazub(50, 0, sprites);
        
        alien.startDuck();
        assertTrue(alien.isDucked() == true);
        
        alien.endDuck();
        assertTrue(alien.isDucked()== false);
        
        alien.setDucked(true);
        assertTrue(alien.isDucked() == true);
        
        alien.setDucked(false);
        assertTrue(alien.isDucked() == false);  
	}
	
	
	@Test
	public void sumdtLegal(){
		int m = 10;
        Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
        Mazub alien =  new Mazub(50, 0, sprites);
        
        assertTrue(alien.getSumdt() == 0.0);
        alien.advanceTime(0.033);
        assertTrue(alien.getSumdt() == 0.033);
        alien.resetSumdt();
        assertTrue(alien.getSumdt() == 0.0);
        alien.addUp(0.69);
        assertTrue(alien.getSumdt() == 0.69);
        
	}
	
	@Test
	
	public void GetSizeLegal(){
		int m = 10;
        Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
        Mazub alien =  new Mazub(50, 0, sprites);
        
        assertTrue(alien.getSize()[0] == alien.getWidth());
        assertTrue(alien.getSize()[1] == alien.getHeight());
        
        alien.startDuck();
        
        assertTrue(alien.getSize()[0] == alien.getWidth());
        assertTrue(alien.getSize()[1] == alien.getHeight());
        
        alien.endDuck();
        
        alien.startMove(true);
        
        assertTrue(alien.getSize()[0] == alien.getWidth());
        assertTrue(alien.getSize()[1] == alien.getHeight());
        
        alien.endMove();

        assertTrue(alien.getSize()[0] == alien.getWidth());
        assertTrue(alien.getSize()[1] == alien.getHeight());
	}

}

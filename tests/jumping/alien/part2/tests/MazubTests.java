package jumping.alien.part2.tests;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Mazub;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;
import org.junit.Test;

public class MazubTests {

	private World createWorldWithGround50px(){
		World world = new World(50, 20, 10,
				200,300,10,0);
		for (int i =0; i<20;i++){
			world.setGeologicalFeature(i, 0, 1);
		}
		return world;
	}

	@Test(expected = OutOfBoundsException.class)
	public void MazubConstructorException1(){

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		@SuppressWarnings("unused")
		Mazub alien = new Mazub(-100, 0, sprites);

	}

	@Test(expected = OutOfBoundsException.class)
	public void MazubConstructorException2(){

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		@SuppressWarnings("unused")
		Mazub alien = new Mazub(0, -100, sprites);

	}


	@Test
	public void startMoveLeftLegal() {

		World world = new World(50, 10, 20,
				200,300,10,15);
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,100, sprites);
		world.setMazub(alien);

		alien.startMove(false);


		assertTrue(alien.getVelocity().getElemx() == -1);
		assertTrue(alien.getAccCurr().getElemx() == -0.9);
	}

	@Test
	public void startMoveRightLegal() {
		World world = new World(50, 10, 20,
				200,300,10,15);
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,100, sprites);
		world.setMazub(alien);

		alien.startMove(true);

		assertTrue(alien.getVelocity().getElemx() == 1);
		assertTrue(alien.getAccCurr().getElemx() == 0.9);
	}

	@Test
	public void endMoveLegal() {

		World world = new World(50, 10, 20,
				200,300,10,15);
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,100, sprites);
		world.setMazub(alien);


		alien.startMove(false);
		world.advanceTime(0.05);
		alien.endMove();

		assertTrue(alien.getVelocity().getElemx() == 0.0);
		assertTrue(alien.getAccCurr().getElemx() == 0.0);

		alien.startMove(true);
		world.advanceTime(0.05);
		alien.endMove();

		assertTrue(alien.getVelocity().getElemx() == 0.0);
		assertTrue(alien.getAccCurr().getElemx() == 0.0);

	}

	@Test
	public void startJumpLegal() {

		World world = createWorldWithGround50px();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);

		world.advanceTime(0.05);

		alien.startJump();
		assertTrue(alien.getVelocity().getElemy() == 8.0);
		assertTrue(alien.isJumped());
		world.advanceTime(0.05);

		assertTrue(alien.getPos().getElemy() > 50);
		assertTrue(alien.getVelocity().getElemy() < 8.0);

	}

	@Test
	public void startJumpDontJumpInAir() {
		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);

		world.advanceTime(0.05);


		alien.startJump();

		assertTrue(alien.isJumped());
		world.advanceTime(0.05);
		alien.endJump();
		assertFalse(alien.isJumped());
		world.advanceTime(0.05);

		alien.startJump();
		assertFalse(alien.getVelocity().getElemy() == 8.0);

	}

	@Test
	public void startJumpDontJumpInAir2() {

		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);

		alien.startJump();
		assertFalse(alien.getVelocity().getElemy() == 8.0);
	}


	@Test
	public void endJumpLegal(){
		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);
		world.advanceTime(0.05);

		alien.startJump();
		world.advanceTime(0.05);
		alien.endJump();
		assertFalse(alien.isJumped()); 
	}

	@Test(expected=IllegalStateException.class)
	public void endJumpException(){
		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);
		world.advanceTime(0.05);

		alien.endJump();
	}

	@Test
	public void startDuckLegal(){
		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);
		world.advanceTime(0.05);

		alien.startDuck();
		assertTrue(alien.isDucked());
		assertTrue(alien.getMaxVelocityXCurr() == 1.0);
	}


	@Test
	public void endDuckLegal(){
		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);
		world.advanceTime(0.05);

		alien.startDuck();
		assertTrue(alien.isDucked());
		alien.pressEndDuck();

		world.advanceTime(0.1);

		assertFalse(alien.isDucked());
	}

	@Test
	public void endDuckInAirLegal(){

		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);
		world.advanceTime(0.05);

		alien.startJump();
		world.advanceTime(0.1);
		alien.startDuck();
		assertTrue(alien.isDucked());
	}

	@Test
	public void getCurrentSpriteLegal(){

		World world = createWorldWithGround50px();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);

		world.advanceTime(0.05);

		//STANDING UP
		assertEquals(alien.getCurrentSprite(), sprites[0]);

		/////////////////////////
		//WAS MOVING TO THE RIGHT
		alien.startMove(true);
		world.advanceTime(0.05);
		alien.endMove();

		for(int i =0; i < 19; i++)
		{
			world.advanceTime(0.05);
			assertEquals(alien.getCurrentSprite(), sprites[2]);
		}
		world.advanceTime(0.1);
		assertEquals(alien.getCurrentSprite(), sprites[0]);

		//WAS MOVING TO THE LEFT
		alien.startMove(false);
		world.advanceTime(0.05);
		alien.endMove();

		for(int i =0; i < 19; i++)
		{
			world.advanceTime(0.05);
			assertEquals(alien.getCurrentSprite(), sprites[3]);
		}
		world.advanceTime(0.1);
		assertEquals(alien.getCurrentSprite(), sprites[0]);


		//////////////////////
		//JUMPING TO THE RIGHT
		alien.startJump();
		alien.startMove(true);
		world.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[4]);
		alien.endJump();
		assertEquals(alien.getCurrentSprite(), sprites[4]);
		world.advanceTime(0.05);
		alien.endMove();

		//BACK TO THE GROUND
		for(int i =0; i <= 30; i++)
		{
			world.advanceTime(0.1);
		}

		//JUMPING TO THE LEFT
		alien.startJump();
		alien.startMove(false);
		world.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[5]);
		alien.endJump();
		assertEquals(alien.getCurrentSprite(), sprites[5]);
		world.advanceTime(0.05);
		alien.endMove();

		//BACK TO THE GROUND
		for(int i =0; i <= 30; i++)
		{
			world.advanceTime(0.1);
		}

		//CHANGING DIRECTION IN AIR
		alien.startJump();
		alien.startMove(true);
		world.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[4]);
		alien.endMove();

		alien.startMove(false);
		world.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[5]);
		alien.endMove();

		//BACK TO THE GROUND
		for(int i =0; i <= 30; i++)
		{
			world.advanceTime(0.1);
		}

		world.advanceTime(0.2);
		world.advanceTime(0.2);
		world.advanceTime(0.2);
		world.advanceTime(0.2);

		//////////
		//DUCKING
		alien.startDuck();

		//DUCKING AND MOVING RIGHT
		alien.startMove(true);
		assertEquals(alien.getCurrentSprite(), sprites[6]);
		alien.endMove();

		//DUCKING AND WAS MOVING RIGHT
		for(int i =0; i < 19; i++)
		{
			world.advanceTime(0.05);
			assertEquals(alien.getCurrentSprite(), sprites[6]);
		}
		world.advanceTime(0.05);

		//DUCKING AND STANDING STILL
		assertEquals(alien.getCurrentSprite(), sprites[1]);


		//DUCKING AND MOVING LEFT
		alien.startMove(false);
		assertEquals(alien.getCurrentSprite(), sprites[7]);
		alien.endMove();

		//DUCKING AND WAS MOVING LEFT
		for(int i =0; i < 19; i++)
		{
			world.advanceTime(0.05);
			assertEquals(alien.getCurrentSprite(), sprites[7]);
		}
		world.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[1]);

		//STANDING UP
		alien.pressEndDuck();
		world.advanceTime(0.05);
		assertEquals(alien.getCurrentSprite(), sprites[0]);


	}


	@Test
	public void getCurrentSpriteWalkingLegal(){

		World world = createWorldWithGround50px();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);

		world.advanceTime(0.05);

		//////////////////////
		//MOVING TO THE RIGHT
		alien.startMove(true);
		for(int i =0; i < 10; i++)
		{
			assertEquals(alien.getCurrentSprite(), sprites[8+i]);
			world.advanceTime(0.076);
			assertEquals(alien.getCurrentSprite(), sprites[8+1+i]);
		}
		world.advanceTime(0.076);
		assertEquals(alien.getCurrentSprite(), sprites[8]);


		for(int i =0; i < m; i++){
			assertEquals(alien.getCurrentSprite(), sprites[(int) (8+(alien.getTimeTimedCurr(
					alien.getStartTime())/0.075))]);
			world.advanceTime(0.045);
		}
		alien.endMove();


		//MOVING TO THE LEFT
		alien.startMove(false);
		for(int i =0; i < m; i++){
			assertEquals(alien.getCurrentSprite(), sprites[9+m+i]);
			world.advanceTime(0.076);
			assertEquals(alien.getCurrentSprite(), sprites[9+m+1+i]);
		}
		world.advanceTime(0.076);
		assertEquals(alien.getCurrentSprite(), sprites[9+m]);

		for(int i =0; i < m; i++){
			assertEquals(alien.getCurrentSprite(), sprites[(int) (9+m+(alien.getTimeTimedCurr(
					alien.getStartTime())/0.075))]);
			world.advanceTime(0.045);
		}
		alien.endMove();

	}

	@Test
	public void getPosXLegal(){

		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(400,49, sprites);
		world.setMazub(alien);
		world.advanceTime(0.05);
		assertTrue(alien.getPos().getElemx() == 400);
	}


	@Test
	public void getHeightLegal(){

		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 10, 10 + 2 * m);
		Mazub alien = new Mazub(400,49, sprites);
		world.setMazub(alien);
		alien.advanceTime(0.1);        
		assertTrue(alien.getSize().getElemy() == 10);
	}



	@Test
	public void getWidthLegal(){
		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 10, 10 + 2 * m);
		Mazub alien = new Mazub(400,49, sprites);
		world.setMazub(alien);
		alien.advanceTime(0.1);
		assertTrue(alien.getSize().getElemx() == 2);
	}

	@Test
	public void InitVelocity1stConstructorLegal(){

		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 10, 10 + 2 * m);

		Mazub alien = new Mazub(400,49, sprites);
		world.setMazub(alien);

		alien.startMove(true);
		assertTrue(alien.getVelocity().getElemx() == 1.0);
	}

	@Test
	public void InitVelocity2ndConstructorLegal(){

		World world = new World(50, 10, 10,
				200,300,10,10);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 10, 10 + 2 * m);

		//2nd constructor
		Mazub alien = new Mazub(50, 0, sprites, 1.2, 3.0);

		world.setMazub(alien);

		alien.startMove(true);
		assertTrue(alien.getVelocity().getElemx() == 1.2);
	}



	@Test
	public void MaxVelocityCurrLegal(){
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);

		//Standard constructor
		Mazub alien =  new Mazub(50, 0, sprites);
		assertTrue(alien.getMaxVelocityXCurr() == 3.0);

		//2nd constructor
		Mazub alien2 = new Mazub(50, 0, sprites, 1, 5.0);
		assertTrue(alien2.getMaxVelocityXCurr() == 5.0);
	}

	@Test
	public void getsetMaxVelocityXCurrLegal(){
		World world1 = createWorldWithGround50px();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world1.setMazub(alien);

		world1.advanceTime(0.05);

		//Initial maxVelocityXCurr with standard constructor
		assertTrue(alien.getMaxVelocityXCurr() == 3.0);

		alien.setMaxVelocityXCurr(2.0);
		assertTrue(alien.getMaxVelocityXCurr() == 2.0);

		//Max velocity while ducking
		alien.startDuck();
		assertTrue(alien.getMaxVelocityXCurr() == 1.0);


		//New max velocity with second constructor
		//First standing, then ducking, end ducking and walking

		World world2 = createWorldWithGround50px();
		Mazub alien2 = new Mazub(100, 49, sprites, 2.0,6.0);
		world2.setMazub(alien2);
		world2.advanceTime(0.05);

		assertTrue(alien2.getMaxVelocityXCurr() == 6.0);
		alien2.startDuck();
		assertTrue(alien2.getMaxVelocityXCurr() == 1.0);
		alien2.pressEndDuck();
		world2.advanceTime(0.01);
		assertTrue(alien2.getMaxVelocityXCurr() == 6.0);
	}

	@Test
	public void getsetAccXCurr(){
		World world = createWorldWithGround50px();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);

		world.advanceTime(0.05);

		alien.startMove(true);
		assertTrue(alien.getAccCurr().getElemx() == 0.9);
		alien.endMove();
		assertTrue(alien.getAccCurr().getElemx() == 0.0);
	}

	@Test
	public void isSetJumpedLegal(){

		World world = createWorldWithGround50px();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);

		world.advanceTime(0.05);

		alien.startJump();
		assertTrue(alien.isJumped() == true);

		alien.endJump();
		assertTrue(alien.isJumped() == false);
	}


	@Test
	public void isSetDuckedLegal(){
		World world = createWorldWithGround50px();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);

		world.advanceTime(0.05);

		alien.startDuck();
		assertTrue(alien.isDucked() == true);

		alien.pressEndDuck();
		world.advanceTime(0.01);
		assertTrue(alien.isDucked()== false);

	}



}
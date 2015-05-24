package jumping.alien.part2.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;

import org.junit.Test;

public class GameObjectTests {


	private World createWorldWithGround50px(){
		World world = new World(50, 10, 10,
				200,300,10,0);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}
		return world;
	}

	private World addMazubToWorld(World world,int posx,int posy){
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(posx,posy, sprites); 
		world.setMazub(alien);
		return world;
	}

	@Test
	public void getWorldTest() {
		World world = createWorldWithGround50px();

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(20,50, sprites); 
		world.setMazub(alien);

		Sprite[] sprites1 = spriteArrayForSize(2, 2, 2);
		Plant plant = new Plant(50,50,sprites1);
		world.addPlant(plant);

		Shark shark = new Shark(50,50,sprites1);
		world.addShark(shark);

		School school = new School();
		Slime slime = new Slime(50,50,sprites1,school);
		world.addSlime(slime);

		assertTrue(alien.getWorld() == world);
		assertTrue(plant.getWorld() == world);
		assertTrue(shark.getWorld() == world);
		assertTrue(slime.getWorld() == world);
	}

	@Test
	public void getHPTest() {

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(20,50, sprites); 

		Sprite[] sprites1 = spriteArrayForSize(2, 2, 2);
		Plant plant = new Plant(50,50,sprites1);

		Shark shark = new Shark(50,50,sprites1);

		School school = new School();
		Slime slime = new Slime(50,50,sprites1,school);

		assertTrue(alien.getHP() == 100);
		assertTrue(plant.getHP() == 1);
		assertTrue(shark.getHP() == 100);
		assertTrue(slime.getHP() == 100);
	}

	@Test
	public void getPosTest() {

		World world = createWorldWithGround50px();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(20,29, sprites); 
		world.setMazub(alien);

		// ADDED 1 Y-px BECAUSE THE GIVEN PROGRAM STARTS MAZUB IN THE GROUND
		// AND IN OUR SOLUTION MAZUB WALKS ON THE TILES.

		Sprite[] sprites1 = spriteArrayForSize(2, 2, 2);
		Plant plant = new Plant(40,50,sprites1);
		world.addPlant(plant);

		Shark shark = new Shark(60,70,sprites1);
		world.addShark(shark);

		School school = new School();
		Slime slime = new Slime(80,90,sprites1,school);
		world.addSlime(slime);

		assertTrue(alien.getPos().getElemx() == 20);
		assertTrue(alien.getPos().getElemy() == 30);

		assertTrue(plant.getPos().getElemx() == 40);
		assertTrue(plant.getPos().getElemy() == 50);

		assertTrue(shark.getPos().getElemx() == 60);
		assertTrue(shark.getPos().getElemy() == 70);

		assertTrue(slime.getPos().getElemx() == 80);
		assertTrue(slime.getPos().getElemy() == 90);
	}
	
	@Test
	public void getSizeTest(){
		World world = createWorldWithGround50px();
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(60, 90, 10 + 2 * m);
		Mazub alien = new Mazub(20,29, sprites); 
		world.setMazub(alien);
		
		assertTrue(alien.getSize().getElemx() == 60);
		assertTrue(alien.getSize().getElemy() == 90);
	}

	@Test
	public void getVelocityTest() {
		World world = createWorldWithGround50px();
		addMazubToWorld(world, 40, 100);

		Sprite[] sprites1 = spriteArrayForSize(2, 2, 2);
		Plant plant = new Plant(40,50,sprites1);
		world.addPlant(plant);

		world.advanceTime(0.1);

		assertTrue(plant.getVelocity().getElemx() == 0.5);
		assertTrue(plant.getVelocity().getElemy() == 0.0);

		for(int i = 0; i<=6; i++){
			world.advanceTime(0.1);
		}

		assertTrue(plant.getVelocity().getElemx() == -0.5);
		assertTrue(plant.getVelocity().getElemy() == 0.0);
	}

	@Test
	public void isDeadTest1() {
		World world = createWorldWithGround50px();
		addMazubToWorld(world, 40, 100);

		Sprite[] sprites1 = spriteArrayForSize(2, 2, 2);
		Plant plant = new Plant(40,100,sprites1);
		world.addPlant(plant);

		assertFalse(plant.isDead());

		world.advanceTime(0.1);
		// Mazub collides with plant and kills the plant.

		assertTrue(plant.isDead());
		assertTrue(plant.getHP() == 0);
	}

//		@Test
//		public void isDeadTest2() {
//			World world = createWorldWithGround50px();
//			world.setGeologicalFeature(0,1,1);
//			world.setGeologicalFeature(0,2,1);
//
//			int m = 10;
//			Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
//			Mazub alien = new Mazub(200, 49, sprites); 
//			world.setMazub(alien);
//			
//			Sprite[] sprites1 = spriteArrayForSize(2, 2, 2);
//			School school = new School();
//			Slime slime = new Slime(55,50,sprites1,school);
//			world.addSlime(slime);
//			
//			assertFalse(slime.isDead());
//			
//			
//			alien.startMove(false);
//			for(int i = 0; i < 100000; i++){
//				
//				world.advanceTime(0.01);
//				
//				if(slime.getHP() == 0){
//					world.advanceTime(0.01);
//					break;
//				}		
//			}		
//			for(int i = 0; i <= 1000; i++){
//				world.advanceTime(0.11);
//			}
//			assertTrue(slime.isDead());
//
//			
//		}

	@Test
	public void getAccCurrTest() {
		World world = createWorldWithGround50px();
		addMazubToWorld(world,120, 50);

		Sprite[] sprites1 = spriteArrayForSize(2, 2, 2);

		School school = new School();
		Slime slime = new Slime(55,50,sprites1,school);
		world.addSlime(slime);

		world.advanceTime(0.01);

		for(int i = 0; i <= 10000; i++){

			world.advanceTime(0.01);
			if (slime.getVelocity().getElemx() > 0){
				assertTrue(slime.getAccCurr().getElemx() == 0.7);
				assertTrue(slime.getAccCurr().getElemy() == 0);

			}else{
				assertTrue(slime.getAccCurr().getElemx() == -0.7);
				assertTrue(slime.getAccCurr().getElemy() == 0);
			}
		}
	}
	
	








	//gameobject
	// -- getWorld()
	// -- getHP()
	// -- getPos()
	// -- getSize()
	// -- getVelocity()
	// -- getAccCurr()
	// -- isDead()


}

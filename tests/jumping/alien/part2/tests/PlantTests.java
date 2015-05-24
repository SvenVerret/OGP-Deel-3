package jumping.alien.part2.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;


import org.junit.Test;

public class PlantTests {

	private World createWorldWithGround50px(){
		World world = new World(50, 10, 10,
				200,300,10,0);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,50, sprites); 
		world.setMazub(alien);

		return world;
	}


	@Test
	public void PlantConstructorLegal() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		Plant plant = new Plant(50,60,sprites);
		assertTrue(plant.getPos().getElemx() == 50);
		assertTrue(plant.getPos().getElemy() == 60);
	}

	@Test(expected = IllegalArgumentException.class)
	public void PlantConstructorIllegalSprites() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 3);
		@SuppressWarnings("unused")
		Plant plant = new Plant(50,50,sprites);
	}

	@Test(expected = OutOfBoundsException.class)
	public void PlantConstructorOutOfBoundsException1() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		@SuppressWarnings("unused")
		Plant plant = new Plant(-10,50,sprites);
	}

	@Test(expected = OutOfBoundsException.class)
	public void PlantConstructorOutOfBoundsException2() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		@SuppressWarnings("unused")
		Plant plant = new Plant(50,-10,sprites);
	}

	@Test
	public void getCurrentSpriteTest(){
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		Plant plant = new Plant(50,50,sprites);

		World world = createWorldWithGround50px();
		world.addPlant(plant);		
		assertTrue(plant.getCurrentSprite() == sprites[1]);

		for(int i = 0; i<=5; i++){
			world.advanceTime(0.1);
		}
		assertTrue(plant.getCurrentSprite() == sprites[1]);
		world.advanceTime(0.01);
		assertTrue(plant.getCurrentSprite() == sprites[0]);

	}
}

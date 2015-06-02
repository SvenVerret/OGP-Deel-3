package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.model.Mazub;
import jumpingalien.model.Shark;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;

import org.junit.Test;

public class SharkTests {
	
	private World createWorldWithGround50px(){
		World world = new World(100, 10, 10,
				200,300,10,0);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 2);
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
	
	@Test(expected = IllegalArgumentException.class)
	public void SharkConstructorIllegalSprites() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 3);
		@SuppressWarnings("unused")
		Shark shark = new Shark(50,50,sprites);
	}

	@Test(expected = OutOfBoundsException.class)
	public void SharkConstructorOutOfBoundsException1() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		@SuppressWarnings("unused")
		Shark shark = new Shark(-10,50,sprites);
	}

	@Test(expected = OutOfBoundsException.class)
	public void SharkConstructorOutOfBoundsException2() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		@SuppressWarnings("unused")
		Shark shark = new Shark(50,-10,sprites);

	}

	@Test
	public void getCurrentSpriteTest(){
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		Shark shark = new Shark(50,50,sprites);

		World world = createWorldWithGround50px();
		addMazubToWorld(world, 200, 200);
		
		world.addShark(shark);		

		for(int i = 0; i<100; i++){
			world.advanceTime(0.1);
			if (shark.getVelocity().getElemx() >= 0)
				assertTrue(shark.getCurrentSprite() == sprites[1]);	
			else
				assertTrue(shark.getCurrentSprite() == sprites[0]);
			
		}
	}
}

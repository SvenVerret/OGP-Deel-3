package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.model.Mazub;
import jumpingalien.model.School;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;

import org.junit.Test;

public class SlimeTests {

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
	public void SlimeConstructorLegal() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		School school = new School();
		Slime slime = new Slime(50,60,sprites,school);
		assertTrue(slime.getPos().getElemx() == 50);
		assertTrue(slime.getPos().getElemy() == 60);
	}

	@Test(expected = IllegalArgumentException.class)
	public void SlimeConstructorIllegalSprites() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 3);
		School school = new School();
		@SuppressWarnings("unused")
		Slime slime = new Slime(50,50,sprites,school);
	}

	@Test(expected = OutOfBoundsException.class)
	public void SlimeConstructorOutOfBoundsException1() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		School school = new School();
		@SuppressWarnings("unused")
		Slime slime = new Slime(-10,50,sprites,school);
	}

	@Test(expected = OutOfBoundsException.class)
	public void SlimeConstructorOutOfBoundsException2() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		School school = new School();
		@SuppressWarnings("unused")
		Slime slime = new Slime(50,-10,sprites,school);

	}
	
	@Test
	public void getSchoolTest() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		School school1 = new School();
		Slime slime = new Slime(50,60,sprites,school1);
		assertTrue(slime.getSchool() == school1);	
	}
	
	@Test
	public void getCurrentSpriteTest() {
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		World world = createWorldWithGround50px();
		world = addMazubToWorld(world, 1, 50);
		
		School school1 = new School();
		Slime slime = new Slime(20,50,sprites,school1);
		world.addSlime(slime);
		
		
		for(int i = 0; i<100; i++){
			world.advanceTime(0.1);
			if (slime.getVelocity().getElemx() >= 0)
				assertTrue(slime.getCurrentSprite() == sprites[1]);	
			else
				assertTrue(slime.getCurrentSprite() == sprites[0]);	
		}
	}
}

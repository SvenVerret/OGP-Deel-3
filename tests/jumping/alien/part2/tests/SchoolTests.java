package jumping.alien.part2.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.School;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;


import org.junit.Test;

public class SchoolTests {


	@Test
	public void hasAsSchoolTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		
		School school1 = new School();
		School school2 = new School();
		Slime slime1 = new Slime(20,20,sprites,school1);
		Slime slime2 = new Slime(20,20,sprites,school2);
		world.addSlime(slime1);
		world.addSlime(slime2);
		
		assertTrue(school1.hasAsSlime(slime1));
		assertTrue(school2.hasAsSlime(slime2));
		
		Slime slime3 = new Slime(20,20,sprites,school1);
		world.addSlime(slime3);
		
		assertTrue(school1.hasAsSlime(slime3));

	}
	
	@Test
	public void getWorldTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		
		School school1 = new School();
		School school2 = new School();
		Slime slime1 = new Slime(20,20,sprites,school1);
		Slime slime2 = new Slime(20,20,sprites,school2);
		world.addSlime(slime1);
		world.addSlime(slime2);
		
		assertTrue(school1.getWorld() == world);
		assertTrue(school2.getWorld() == world);
		
	}
	

}

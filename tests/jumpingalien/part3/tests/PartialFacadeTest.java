package jumpingalien.part3.tests;

import static org.junit.Assert.*;

import java.util.Optional;

import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.facade.IFacadePart3;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.util.Sprite;

import org.junit.Test;

import program.Program;
import program.ProgramFactory;
import program.expression.Expression;
import program.statement.Statement;
import program.type.Type;
import static jumpingalien.tests.util.TestUtils.*;


public class PartialFacadeTest {

	//	@Test
	//	public void testParseSimplestProgram() {
	//		IFacadePart3 facade = new Facade();
	//		ParseOutcome<?> outcome = facade.parse("skip;");
	//		assertTrue(outcome.isSuccess());
	//	}
	//
	//	
	//	
	//	@Test
	//	public void testParseSimplestProgram2() {
	//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
	//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
	//
	//		Optional<Program> parse_outcome = parser.parseString("double y; object o; o := self;");
	//		Program program = parse_outcome.get();
	//		
	//		IFacadePart3 facade = new Facade();
	//		facade.createPlantWithProgram(10, 10, sprites, program);
	//	}
	//	
	//	
	//	
	//	
	//	// OPERATIONS
	//	
	//	@Test
	//	public void testParseSimplestProgramAdd() {
	//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
	//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
	//
	//		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 + 2.0;");
	//		Program program = parse_outcome.get();
	//		
	//		program.advanceTime(0.1);
	//	}
	//	
	//	@Test
	//	public void testParseSimplestProgramSubtract() {
	//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
	//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
	//
	//		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 - 2.0;");
	//		Program program = parse_outcome.get();
	//		
	//		program.advanceTime(0.1);
	//	}
	//	
	//	@Test
	//	public void testParseSimplestProgramMultiply() {
	//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
	//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
	//
	//		Optional<Program> parse_outcome = parser.parseString("double y; y := 4.0 * 2.0;");
	//		Program program = parse_outcome.get();
	//		
	//		program.advanceTime(0.1);
	//		System.out.println(program.getVariables());
	//	}
	//	
	//	@Test
	//	public void testParseSimplestProgramDivide() {
	//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
	//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
	//
	//		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 / 0.0;");
	//		Program program = parse_outcome.get();
	//		
	//		program.advanceTime(0.1);
	//	}
	//	
	//	@Test
	//	public void testParseSimplestProgramEquals() {
	//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
	//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
	//
	//		Optional<Program> parse_outcome = parser.parseString("double y; double x; double rslt;"
	//				+ "y := 3.0; x := 3.0; rslt := x == y;");
	//		Program program = parse_outcome.get();
	//		
	//		program.advanceTime(0.1);
	//		System.out.println(program.getVariables());
	//	}
	//	
	//	
	//	
	//	@Test
	//	public void testParseSimplestProgram4() {
	//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
	//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
	//
	//		Optional<Program> parse_outcome = parser.parseString("direction currentxdir := up; direction newxdir := left;");
	//		Program program = parse_outcome.get();
	//		
	//		program.advanceTime(0.1);
	//
	//	}
	//
	//	
	//	
	//	@Test
	//	public void testParseFails() {
	//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
	//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
	//		Optional<Program> parse_outcome = parser.parseString("print self + 1.45;");
	//		System.out.println(parse_outcome);
	//	}
	//
	//
	//	@Test
	//	public void testBreakNotWellformed() {
	//		IFacadePart3 facade = new Facade();
	//		ParseOutcome<?> outcome = facade.parse("double d := 1.0; break;");
	//		assertFalse(facade.isWellFormed((Program) outcome.getResult()));
	//	}
	//	
	//	
	//	@Test
	//	public void testWellformed() {
	//		IFacadePart3 facade = new Facade();
	//		ParseOutcome<?> outcome = facade.parse("double d := 1.0; while d < 3 do if random 2 <= 1 then break; fi done");
	//		assertTrue(outcome.isSuccess());
	//		assertTrue(facade.isWellFormed((Program) outcome.getResult()));
	//	}
	
//
//	@Test
//	public void testPlantWithProgramMoveToRightAndStop(){
//		World world = new World(50, 10, 20,
//				200,300,10,15);
//		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
//
//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
//
//		Optional<Program> parse_outcome = parser.parseString("start_run right; wait(0.2 - 0.001); stop_run right;wait(1.0 - 0.001);");
//
//		Program program = parse_outcome.get();
//
//		IFacadePart3 facade = new Facade();
//		Plant plant = facade.createPlantWithProgram(10, 10, sprites, program);
//		world.addPlant(plant); 
//		for(int i=0; i<10; i++){
//			world.advanceTime(0.1);
//		}
//		//0.2s * 0.5m/s = rekensom???
//		//TODO
//		assertTrue(plant.getPos().getElemx() == 28.0);
//	}
//	
//	@Test
//	public void testSlimeWithProgramMoveToRightAndStop(){
//		World world = new World(50, 10, 20,
//				200,300,10,15);
//		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
//		
//		School school = new School();
//
//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
//
//		Optional<Program> parse_outcome = parser.parseString("start_run right;");
//
//		Program program = parse_outcome.get();
//
//		IFacadePart3 facade = new Facade();
//		Slime slime = facade.createSlimeWithProgram(0, 0,sprites,school, program);
//		world.addSlime(slime); 
//		for(int i=0; i<10; i++){
//			world.advanceTime(0.1);
//			System.out.println(slime.getPos().getElemx());
//		}
//		//0.2s * 0.5m/s = rekensom???
//		//TODO
//		assertTrue(slime.getPos().getElemx() == 28.0);
//	}

	
	
	// OPERATIONS
	
//	@Test
//	public void testParseSimplestProgramAdd() {
//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
//
//		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 + 2.0;");
//		Program program = parse_outcome.get();
//		
//		program.advanceTime(0.1);
//	}
//	
//	@Test
//	public void testParseSimplestProgramSubtract() {
//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
//
//		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 - 2.0;");
//		Program program = parse_outcome.get();
//		
//		program.advanceTime(0.1);
//	}
//	
//	@Test
//	public void testParseSimplestProgramMultiply() {
//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
//
//		Optional<Program> parse_outcome = parser.parseString("double y; y := 4.0 * 2.0;");
//		Program program = parse_outcome.get();
//		
//		program.advanceTime(0.1);
//		System.out.println(program.getVariables());
//	}
//	
//	@Test
//	public void testParseSimplestProgramDivide() {
//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
//
//		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 / 0.0;");
//		Program program = parse_outcome.get();
//		
//		program.advanceTime(0.1);
//	}
	
//	@Test
//	public void testParseSimplestProgramEquals() {
//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
//
//		Optional<Program> parse_outcome = parser.parseString("double y; double x; double rslt;"
//				+ "y := 3.0; x := 3.0; rslt := x == y;");
//		Program program = parse_outcome.get();
//		
//		program.advanceTime(0.1);
//		System.out.println(program.getVariables());
//	}
	
	
	
//	@Test
//	public void testParseSimplestProgram4() {
//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
//
//		Optional<Program> parse_outcome = parser.parseString("direction currentxdir := up; direction newxdir := left;");
//		Program program = parse_outcome.get();
//		
//		program.advanceTime(0.1);
//
//	}

	
	
//	@Test
//	public void testParseFails() {
//		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
//		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
//		Optional<Program> parse_outcome = parser.parseString("print self + 1.45;");
//		System.out.println(parse_outcome);
//	}
	
//
	@Test
	public void testBreakNotWellformed1() {
		IFacadePart3 facade = new Facade();
		ParseOutcome<?> outcome = facade.parse("double d := 1.0; break;");
		assertFalse(facade.isWellFormed((Program) outcome.getResult()));
	}

	
	@Test
	public void searchObjectLeft(){
		World world = new World(50, 20, 20,
				200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("object o := null; o := searchobj ( left );");
		Program program = parse_outcome.get();
		IFacadePart3 facade = new Facade();
		
		Plant plant1 = facade.createPlant(10, 10, sprites);
		Plant plant2 = facade.createPlant(0, 10, sprites);
		Plant plant3 = facade.createPlant(3, 10, sprites);
		Plant plantProgram = facade.createPlantWithProgram(15, 10, sprites, program);
		
		world.addPlant(plant1);
		System.out.println(plant1.getPos().getElemx()+" "+plant1.getPos().getElemy());
		world.addPlant(plant2); 
		System.out.println(plant2.getPos().getElemx()+" "+plant2.getPos().getElemy());
		world.addPlant(plant3);
		System.out.println(plant3.getPos().getElemx()+" "+plant3.getPos().getElemy());
		world.addPlant(plantProgram);
		System.out.println(plantProgram.getPos().getElemx()+" "+plantProgram.getPos().getElemy());
		
		world.advanceTime(0.1);
		
		
		
	}
}

package jumpingalien.part3.tests;

import static org.junit.Assert.*;

import java.util.Optional;

import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.facade.IFacadePart3;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;

import org.junit.Test;

import program.Program;
import program.ProgramFactory;
import program.expression.Expression;
import program.statement.Statement;
import program.type.Type;

public class PartialFacadeTest {

//	@Test
//	public void testParseSimplestProgram() {
//		IFacadePart3 facade = new Facade();
//		ParseOutcome<?> outcome = facade.parse("skip;");
//		assertTrue(outcome.isSuccess());
//	}
	
//	@Test
//	public void testParseSimplestProgram2() {
//		IFacadePart3 facade = new Facade();
//		ParseOutcome<?> outcome = facade.parse("wait(10);");
//		assertTrue(outcome.isSuccess());
//	}
	
	@Test
	public void testParseSimplestProgram3() {
		IProgramFactory<Expression<?>, Statement, Type, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Type, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; y:= 1.0;");
		Program program = parse_outcome.get();
		
		program.advanceTime(0.1);

	}

	
	
//	@Test
//	public void testParseFails() {
//		IFacadePart3 facade = new Facade();
//		ParseOutcome<?> outcome = facade.parse("skip && 3;");
//		assertFalse(outcome.isSuccess());
//	}
//
//	@Test
//	public void testBreakNotWellformed() {
//		IFacadePart3 facade = new Facade();
//		ParseOutcome<?> outcome = facade.parse("double d := 1.0; break;");
//		assumeTrue(outcome.isSuccess());
//		assertFalse(facade.isWellFormed((Program) outcome.getResult()));
//	}
//	
//	@Test
//	public void testWellformed() {
//		IFacadePart3 facade = new Facade();
//		ParseOutcome<?> outcome = facade.parse("double d := 1.0; while d < 3 do if random 2 <= 1 then break; fi done");
//		assumeTrue(outcome.isSuccess());
//		assertTrue(facade.isWellFormed((Program) outcome.getResult()));
//	}
}

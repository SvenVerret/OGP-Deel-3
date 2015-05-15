package program;

import java.util.List;
import java.util.Map;

import program.expression.Expression;
import program.expression.ValueExpression;
import program.statement.Statement;
import program.type.Type;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.SourceLocation;


public class ProgramFactory implements IProgramFactory<Expression<?>, Statement, Type, Program> {

	@Override
	public Expression<T> createReadVariable(String variableName,
			Type variableType, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<Double> createDoubleConstant(double value,
			SourceLocation sourceLocation) {
		return new ValueExpression<Double>(value, sourceLocation);
	}

	@Override
	public Expression<Boolean> createTrue(SourceLocation sourceLocation) {
		return new ValueExpression<Boolean>(true, sourceLocation);
		
	}

	@Override
	public Expression<Boolean> createFalse(SourceLocation sourceLocation) {
		return new ValueExpression<Boolean>(true, sourceLocation);
	}

	@Override
	public Expression<> createNull(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createSelf(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createDirectionConstant(
			jumpingalien.part3.programs.IProgramFactory.Direction value,
			SourceLocation sourceLocation) {
		return new SingleValueExpression<Direction>(value , sourceLocation);
	}

	@Override
	public Expression<?> createAddition(Expression<E1> left,
			Expression<E1> right, SourceLocation sourceLocation) {
		return new BinaryExpressionOperation(Expression<E1> left, Expression<E1> right, (a,b) -> a+b)
	}

	@Override
	public Expression<T> createSubtraction(Expression<T> left,
			Expression<T> right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createMultiplication(Expression<T> left,
			Expression<T> right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createDivision(Expression<T> left,
			Expression<T> right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createSqrt(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createRandom(Expression<T> maxValue,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createAnd(Expression<T> left, Expression<T> right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createOr(Expression<T> left, Expression<T> right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createNot(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createLessThan(Expression<T> left,
			Expression<T> right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createLessThanOrEqualTo(Expression<T> left,
			Expression<T> right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createGreaterThan(Expression<T> left,
			Expression<T> right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createGreaterThanOrEqualTo(Expression<T> left,
			Expression<T> right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createEquals(Expression<T> left, Expression<T> right,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createNotEquals(Expression<T> left,
			Expression<T> right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createGetX(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createGetY(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createGetWidth(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createGetHeight(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createGetHitPoints(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createGetTile(Expression<T> x, Expression<T> y,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createSearchObject(Expression<T> direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsMazub(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsShark(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsSlime(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsPlant(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsDead(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsTerrain(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsPassable(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsWater(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsMagma(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsAir(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsMoving(Expression<T> expr,
			Expression<T> direction, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsDucking(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<T> createIsJumping(Expression<T> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createAssignment(String variableName, Type variableType,
			Expression<T> value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWhile(Expression<T> condition, Statement body,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createForEach(
			String variableName,
			jumpingalien.part3.programs.IProgramFactory.Kind variableKind,
			Expression<T> where,
			Expression<T> sort,
			jumpingalien.part3.programs.IProgramFactory.SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createIf(Expression<T> condition, Statement ifBody,
			Statement elseBody, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createPrint(Expression<T> value,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartRun(Expression<T> direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopRun(Expression<T> direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWait(Expression<T> duration,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSkip(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSequence(List<Statement> statements,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getDoubleType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getBoolType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getGameObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getDirectionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Program createProgram(Statement mainStatement,
			Map<String, Type> globalVariables) {
		// TODO Auto-generated method stub
		return null;
	}

}

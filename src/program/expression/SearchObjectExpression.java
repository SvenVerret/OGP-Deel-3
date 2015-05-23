package program.expression;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import program.Program;
import jumpingalien.model.GameObject;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;


public class SearchObjectExpression extends Expression<SourceLocation>{

	/**
	 *
	 * @param direction
	 *  
	 */
	public SearchObjectExpression(Expression<Direction> direction, SourceLocation sourceLocation){
		super(sourceLocation);
		dir = direction;
	}

	public Expression<Direction> getDirection(){
		return dir;
	}

	@Override
	public Object evaluate(Program program) {

		GameObject gameObject = program.getGameObject();
		double pixelLeft = gameObject.getPos().getElemx();
		double pixelBottom = gameObject.getPos().getElemy();
		double pixelRight = gameObject.getPos().getElemx() + gameObject.getSize().getElemx();
		double pixelTop = gameObject.getPos().getElemy() + gameObject.getSize().getElemy();
		Set<GameObject> GameObjectsWorld = program.getGameObject().getWorld().getEachAndEveryObject();

		final Comparator<GameObject> compX = (p1, p2) ->
		Double.compare(p1.getPos().getElemx(),p2.getPos().getElemx());

		final Comparator<GameObject> compY = (p1, p2) ->
		Double.compare(p1.getPos().getElemy(),p2.getPos().getElemy());
		
		final Comparator<int[]> compIntX = (p1,p2)->Integer.compare(p1[0],p2[0]);
		final Comparator<int[]> compIntY = (p1,p2)->Integer.compare(p1[1],p2[1]);

		Direction direction = (Direction) getDirection().evaluate(program);

		switch(direction){

		
		
		case DOWN:
			GameObject object1 = GameObjectsWorld.stream().
			filter(e ->e.getPos().getElemy()< pixelBottom &&
					gameObject.overlapsWithX(e)).min(compY).get();

			int[][] tilesUnderNeath = program.getGameObject().getWorld().getTilePositionsIn((int)pixelLeft,
					0, (int)pixelRight, (int)pixelBottom);

			List<int[]> inPassableTilesUnderNeath = new ArrayList<int[]>();

			for(int[] tile: tilesUnderNeath){
				if (program.getGameObject().getWorld().getGeologicalFeatureTiles(tile[0], tile[1]) == 1){
					int[] e = {tile[0],tile[1]};
					inPassableTilesUnderNeath.add(e);
				}
			}

			int[] tile1 = inPassableTilesUnderNeath.stream().min(compIntY).get();
			
			Object result1 = Integer.compare(tile1[1],(int)object1.getPos().getElemy());

			return result1;

			
			
		case UP:
			GameObject object2 = GameObjectsWorld.stream().
			filter(e ->e.getPos().getElemy()> pixelBottom &&
					gameObject.overlapsWithX(e)).min(compY).get();

			int[][] tilesAbove = program.getGameObject().getWorld().getTilePositionsIn((int)pixelLeft, 
					(int)pixelTop, (int)pixelRight,
					(int) program.getGameObject().getWorld().getWorldSize().getElemy());


			List<int[]> inPassableTilesAbove = new ArrayList<int[]>();

			for(int[] tile: tilesAbove){
				if (program.getGameObject().getWorld().getGeologicalFeatureTiles(tile[0], tile[1]) == 1){
					int[] e = {tile[0],tile[1]};
					inPassableTilesAbove.add(e);
				}
			}
			
			int[] tile2 = inPassableTilesAbove.stream().min(compIntY).get();
			
			Object result2 = Integer.compare(tile2[1],(int)object2.getPos().getElemy());

			return result2;

			
			
		case RIGHT:
			GameObject object3 = GameObjectsWorld.stream().
			filter(e ->e.getPos().getElemx()< pixelLeft &&
					gameObject.overlapsWithY(e)).min(compX).get();

			int[][] tilesRightSide = program.getGameObject().getWorld().getTilePositionsIn((int)pixelRight,
					(int)pixelBottom, (int) program.getGameObject().getWorld().getWorldSize().getElemx(), 
					(int)pixelTop);

			List<int[]> inPassableTilesRightSide = new ArrayList<int[]>();

			for(int[] tile: tilesRightSide){
				if (program.getGameObject().getWorld().getGeologicalFeatureTiles(tile[0], tile[1]) == 1){
					int[] e = {tile[0],tile[1]};
					inPassableTilesRightSide.add(e);
				}
			}
			
			int[] tile3 = inPassableTilesRightSide.stream().min(compIntX).get();
			
			Object result3 = Integer.compare(tile3[1],(int)object3.getPos().getElemx());

			return result3;
			
			
			
		case LEFT:
			GameObject object4 = GameObjectsWorld.stream().
			filter(e ->e.getPos().getElemx()< pixelLeft &&
					gameObject.overlapsWithY(e)).min(compX).get();

			int[][] tilesLeftSide = program.getGameObject().getWorld().getTilePositionsIn(0,
					(int)pixelBottom, (int) pixelLeft, 
					(int)pixelTop);

			List<int[]> inPassableTilesLeftSide = new ArrayList<int[]>();

			for(int[] tile: tilesLeftSide){
				if (program.getGameObject().getWorld().getGeologicalFeatureTiles(tile[0], tile[1]) == 1){
					int[] e = {tile[0],tile[1]};
					inPassableTilesLeftSide.add(e);
				}
			}
			
			int[] tile4 = inPassableTilesLeftSide.stream().min(compIntX).get();
			
			Object result4 = Integer.compare(tile4[1],(int)object4.getPos().getElemx());

			return result4;
		default:
			return null;

		}

	}

	private Expression<Direction> dir;

}

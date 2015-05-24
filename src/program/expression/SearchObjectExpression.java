package program.expression;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

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
			try{
			setGameObjectResult(GameObjectsWorld.stream().
			filter(e ->e.getPos().getElemy()< pixelBottom && e.getPos().getElemx() >= pixelLeft &&
			e.getPos().getElemx() <= pixelRight).max(compY).get());
			}catch(NullPointerException|NoSuchElementException e){
				setGameObjectResult(null);
			}

			int[][] tilesUnderNeath = program.getGameObject().getWorld().getTilePositionsIn((int)pixelLeft,
					0, (int)pixelRight, (int)pixelBottom);

			List<int[]> inPassableTilesUnderNeath = new ArrayList<int[]>();

			for(int[] tile: tilesUnderNeath){
				if (program.getGameObject().getWorld().getGeologicalFeatureTiles(tile[0], tile[1]) == 1){
					int[] e = {tile[0],tile[1]};
					inPassableTilesUnderNeath.add(e);
				}
			}

			try{
				setTileResult(inPassableTilesUnderNeath.stream().max(compIntY).get());
			}catch(NullPointerException|NoSuchElementException e){
				setTileResult(null);
			}


			if (getGameObjectResult() != null && getTileResult() != null){
				return Integer.compare(getTileResult()[1],(int)getGameObjectResult().getPos().getElemx());
			}else if(getGameObjectResult() == null){
				return getTileResult();
			}else if(getTileResult() == null){
				return getGameObjectResult();
			}else
				return null;



		case UP:
			try{
			setGameObjectResult(GameObjectsWorld.stream().
			filter(e ->e.getPos().getElemy()> pixelBottom && e.getPos().getElemx() >= pixelLeft &&
			e.getPos().getElemx() <= pixelRight).min(compY).get());
			}catch(NullPointerException|NoSuchElementException e){
				setGameObjectResult(null);
			}

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


			try{
				setTileResult(inPassableTilesAbove.stream().min(compIntY).get());
			}catch(NullPointerException|NoSuchElementException e){
				setTileResult(null);
			}

			if (getGameObjectResult() != null && getTileResult() != null){
				return Integer.compare(getTileResult()[1],(int)getGameObjectResult().getPos().getElemx());
			}else if(getGameObjectResult() == null){
				return getTileResult();
			}else if(getTileResult() == null){
				return getGameObjectResult();
			}else
				return null;



		case RIGHT:
			try{
			setGameObjectResult(GameObjectsWorld.stream().
			filter(e -> e.getPos().getElemx()< pixelLeft && e.getPos().getElemy() >= pixelBottom &&
			e.getPos().getElemy() <= pixelTop).min(compX).get());
			}catch(NullPointerException|NoSuchElementException e){
				setGameObjectResult(null);
			}

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

			
			try{
				setTileResult(inPassableTilesRightSide.stream().min(compIntX).get());
			}catch(NullPointerException|NoSuchElementException e){
				setTileResult(null);
			}


			if (getGameObjectResult() != null && getTileResult() != null){
				return Integer.compare(getTileResult()[1],(int)getGameObjectResult().getPos().getElemx());
			}else if(getGameObjectResult() == null){
				return getTileResult();
			}else if(getTileResult() == null){
				return getGameObjectResult();
			}else
				return null;



		case LEFT:

			try{
			setGameObjectResult(GameObjectsWorld.stream().
					filter(e ->e.getPos().getElemx() < pixelLeft && e.getPos().getElemy() >= pixelBottom &&
					e.getPos().getElemy() <= pixelTop).max(compX).get());
			}catch(NullPointerException|NoSuchElementException e){
				setGameObjectResult(null);
			}

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
			
			try{
				setTileResult(inPassableTilesLeftSide.stream().max(compIntX).get());
			}catch(NullPointerException|NoSuchElementException e){
				setTileResult(null);
			}
			
			
			if (getGameObjectResult() != null && getTileResult() != null){
				return Integer.compare(getTileResult()[1],(int)getGameObjectResult().getPos().getElemx());
			}else if(getGameObjectResult() == null){
				return getTileResult();
			}else if(getTileResult() == null){
				System.out.println(getGameObjectResult().getPos().getElemx() +" "+
			getGameObjectResult().getPos().getElemy());
				return getGameObjectResult();
			}else
				return null;
		default:
			return null;

		}

	}
	
	public GameObject getGameObjectResult(){
		return object;
	}
	
	public void setGameObjectResult(GameObject object){
		this.object = object;
	}

	public int[] getTileResult() {
		return tile;
	}

	public void setTileResult(int[] tile) {
		this.tile = tile;
	}

	private GameObject object;
	private int[] tile;



	private Expression<Direction> dir;

}

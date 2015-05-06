package jumpingalien.util;
import be.kuleuven.cs.som.annotate.*;

public class Vector {
	
	public Vector(double elemx, double elemy){
		this.elemx = elemx; this.elemy = elemy;
	}
	
	public Vector multiplyBy(double number){
	 return new Vector(this.getElemx()*number,this.getElemy()*number);
	}
	
	public Vector addBy(double number){
		 return new Vector(getElemx()+number,getElemy()+number);
	}
	

	public Vector addVector(Vector other){
		double x = this.getElemx() + other.getElemx();
		double y = this.getElemy() + other.getElemy();
		return new Vector(x,y);
	}
	
	public double normOfVector(){
		double x = Math.pow(this.getElemx(),2);
		double y = Math.pow(this.getElemy(),2);
		double result = Math.sqrt(x+y);
		return result;
		
	}
	
	@Basic
	@Immutable
	public double getElemx(){
		return elemx;
	}

	@Basic
	@Immutable
	public double getElemy(){
		return elemy;
	}
	private final double elemx;
	private final double elemy;
	

}
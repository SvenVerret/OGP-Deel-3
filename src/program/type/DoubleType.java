package program.type;

<<<<<<< HEAD
public class DoubleType implements Type {

	public DoubleType(Double value){
		setValue(value);
	}

	public DoubleType(){
		setValue(getDefaultValue());
	}

	public Double getDefaultValue() {
		return 0.0;
	}

	public void setValue(Object value) {
		this.value = (Double) value;
	}

	public Double getValue() {
		return value;
	}

	public Integer getValueInt() {
		Double value = getValue();
		if (value > 0){
			return (int) Math.floor(value);
		}else if (value < 0){
			return (int) Math.ceil(value);
		}else{
			return 0;
		}
	}

	private Double value;   	
=======
public class DoubleType extends Type<Double> {

	public Double getDefaultValue() {
		return 0.0;
	}

	public Integer getValueInt() {
		Double value = getValue();
		if (value > 0){
			return (int) Math.floor(value);
		}else if (value < 0){
			return (int) Math.ceil(value);
		}else{
			return 0;
		}
	}

>>>>>>> refs/remotes/origin/KevinUberBranch
}




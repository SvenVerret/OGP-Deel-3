package program.type;

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

}




package program.type;

public abstract class Type{

	public boolean equals(Type type){
		return (this.getClass() == type.getClass());
	}

	public abstract Object getDefaultValue();
}

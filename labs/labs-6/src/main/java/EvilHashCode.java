public class EvilHashCode {
	private int variable;

	public EvilHashCode(int initialValue) {
		this.variable = initialValue;
	}

	public void setVariable(int variable) {
		this.variable = variable;
	}

	public int getVariable() {
		return variable;
	}

	@Override
	public int hashCode() {
		return variable;// It's okay to do this here. In this case this is a very good hashCode()
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return (variable == ((EvilHashCode) obj).variable);
	}
}
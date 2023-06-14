import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class PersonExternalise implements Externalizable {
	private int age;
	private String fullName;
	private double weightKg;

	public PersonExternalise(int age, double weight, String fullName) {
		this.age = age;
		this.weightKg = weight;
		this.fullName = fullName;
	}

	public PersonExternalise() {

	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getWeightKg() {
		return weightKg;
	}

	public void setWeightKg(double weightKg) {
		this.weightKg = weightKg;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(this.age);
		out.writeUTF(this.fullName);
		out.writeDouble(this.weightKg);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.age = in.readInt();
		this.fullName = in.readUTF();
		this.weightKg = in.readDouble();
	}

	@Override
	public String toString() {
		return "PersonExternalise{" +
				"age=" + age +
				", fullName='" + fullName + '\'' +
				", weightKg=" + weightKg +
				'}';
	}
}

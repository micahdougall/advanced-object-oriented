import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class Animal {
    final String type;
    int age;

    @JsonProperty("descendents")
    List<Animal> descendents;

    public Animal() {
        this.type = "";
    }

    public Animal(String type, int age, List<Animal> descendents) {
        this.type = type;
        this.age = age;
        this.descendents = descendents;
    }

    public String getType() {
        return type;
    }

    public int getAge() {
        return age;
    }

    public List<Animal> getDescendents() {
        return Collections.unmodifiableList(this.descendents);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDescendents(List<Animal> descendents) {
        this.descendents = descendents;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Animal) {
            Animal other = (Animal) obj;
            return (
                    this.type.equals(other.type)
                            && this.age == other.age
                            && this.descendents.equals(other.descendents)
            );
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        int hashSeed = 31;
        hashCode = hashCode * hashSeed + this.type.hashCode();
        hashCode = hashCode * hashSeed + this.age;
        hashCode = hashCode * hashSeed + this.descendents.hashCode();
        return hashCode;
    }
}

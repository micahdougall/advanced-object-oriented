import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        HashMap<String, Animal> animals =
                (HashMap<String, Animal>) parseAnimals("src/main/resources/animals.json")
                .stream()
                .collect(Collectors.toMap((Animal animal) -> animal.type, animal -> animal));

        Animal wombat = parseAnimal("src/main/resources/single.json");

        System.out.printf("\nTask 6.1 -> compare Animals using equals:\n\n");
        animals.forEach((name, animal) ->
                System.out.printf(
                        "\tAnimal %s equal to %s? -> %s\n",
                        animal.type, wombat.type, animal.equals(wombat)
        ));

        System.out.printf("\nTask 6.2 -> check for evil instance in hashset:\n\n");
        EvilHashCode evil = new EvilHashCode(21);
        HashSet<EvilHashCode> evilSet = new HashSet<>(Arrays.asList(evil));
        System.out.println(evilSet.contains(evil));

        System.out.printf("\nStill contained after variable reassignment?\n\n");
        evil.setVariable(10);
        System.out.println(evilSet.contains(evil));

        System.out.printf("\nIs 6.1 safe from this issue?\n\n");
        animals.entrySet()
                .stream()
                .peek(entry -> {
                    if (entry.getKey().equals("wombat")) { entry.getValue().age = 10; }
                })
                .forEach(entry ->
                    System.out.printf(
                            "\tAnimal %s equal to %s? -> %s\n",
                            entry.getValue().type, wombat.type, entry.getValue().equals(wombat)
                ));
    }

    public static Animal parseAnimal(String filename) throws IOException {
        return new ObjectMapper()
                .readValue(new File(filename), Animal.class);
    }

    public static List<Animal> parseAnimals(String filename) throws IOException {
        return new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .readValue(new File(filename), new TypeReference<List<Animal>>() {});
    }
}

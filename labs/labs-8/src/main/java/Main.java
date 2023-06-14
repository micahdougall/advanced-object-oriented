import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        serializePerson();
        gsonSerialize();
    }

    public static void serializePerson() throws IOException, ClassNotFoundException {
        PersonExternalise person = new PersonExternalise(100, 79.5, "Donald");

        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(boas);
        person.writeExternal(oos);
        oos.flush();
        oos.close();
        boas.close();

        byte[] serializedPerson = boas.toByteArray();

        ByteArrayInputStream in = new ByteArrayInputStream(serializedPerson);
        ObjectInputStream oin = new ObjectInputStream(in);
        PersonExternalise p = new PersonExternalise();
        p.readExternal(oin);
        in.close();
        oin.close();

        System.out.println(p);
    }

    public static void gsonSerialize() throws IOException {
        Gson g = new GsonBuilder().create();
        URL url = new URL("https://ipapi.co/json/");
        IPResponse response = g.fromJson(new InputStreamReader(url.openStream()), IPResponse.class);
        System.out.println(response);
    }
}
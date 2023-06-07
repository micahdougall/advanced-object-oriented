import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println(bigSecretMessage());

        System.out.println(decodeCharArray("hctc,cuv,Fgqivmr"));
        System.out.println(decodeCharArray("dfwGfphwls"));
        System.out.println(decodeCharArray("fvkswa"));
        System.out.println(decodeCharArray("mqquv?**p31+ki*u}itM"));
        System.out.println(decodeCharArray("vtohrjh"));
        System.out.println(decodeCharArray("Sofilt'ahu'ebfuni`'pnso'jb'=*."));

        System.out.println(getUltimateSecret());
    }

    public static String bigSecretMessage() {
        try {
            BigSecret bigSecret = new BigSecret();
            Field localSecretCode = bigSecret.getClass().getDeclaredField("localSecretCode");
            localSecretCode.setAccessible(true);

            return bigSecret
                    .unlockAndGetMessage((Long) localSecretCode.get(bigSecret))
                    .orElse("Incorrect key supplied.");
        } catch (ReflectiveOperationException e) {
            System.out.println();
        }
        return "Reflection failed.";
    }

    public static String decodeCharArray(String arr) {
        Function<String, String> hehe = a -> {
            for (int j = ((int) System.getProperties().compute("", (k, v) -> v == null ? 2 : ((int) v) + 1));;)
                return a.chars().map(c -> c ^ j).mapToObj(c -> ((char) c) + "").collect(Collectors.joining());
        };

        String a = hehe.apply(arr);


        return a;
    }

    public static String getUltimateSecret() {
        try {
            BigSecret bigSecret = new BigSecret();
            Field ultimateKey = bigSecret.getClass().getDeclaredField("ultimateKey");
            ultimateKey.setAccessible(true);
            byte[] secretCharArray = (byte[]) ultimateKey.get(bigSecret);

            String xOrSecret = bigSecret.decodeAndGetUltimateSecret();

            return orCharArray(xOrSecret.toCharArray(), secretCharArray);
        } catch (ReflectiveOperationException e) {
            System.out.println();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return "Reflection failed.";
    }

    public static String orCharArray(char[] x, byte[] y) throws UnsupportedEncodingException {
        for (int i = 0; i < x.length - 1; x[i] ^= y[i++]);
        return new String(x);
    }

}
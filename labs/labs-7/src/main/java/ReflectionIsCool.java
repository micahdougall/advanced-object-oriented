import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.util.function.Function;
import java.util.stream.Collectors;
public class ReflectionIsCool {
	public static void main(String[] args) throws Throwable {
		Function<String, String> hehe = a -> {
			for (int j = ((int) System.getProperties().compute("", (k, v) -> v == null ? 2 : ((int) v) + 1));;)
				return a.chars().map(c -> c ^ j).mapToObj(c -> ((char) c) + "").collect(Collectors.joining());
		};
		Object o = Class.forName(hehe.apply("hctc,cuv,Fgqivmr")).getDeclaredMethod(hehe.apply("dfwGfphwls")).invoke(null);
		o.getClass().getDeclaredMethod(hehe.apply("fvkswa"), URI.class).invoke(o, new URL(hehe.apply("mqquv?**p31+ki*u}itM")).toURI());
		PrintStream.class.getDeclaredMethod(hehe.apply("vtohrjh"), String.class).invoke(System.out, hehe.apply("Sofilt'ahu'ebfuni`'pnso'jb'=*."));
	}
}
package mangri.sample.jsf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Http {
    public static String GET(URL url) {
        try (InputStream is = url.openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            Supplier<String> nextLine = () -> {
                try {
                    return reader.readLine();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            };

            return Stream.generate(nextLine)
                .takeWhile(s -> s != null)
                .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
    
}

package com.bearsoft.citiesfetcher;

import com.bearsoft.citiesfetcher.model.City;
import com.bearsoft.citiesfetcher.transforms.CityToCsv;
import com.bearsoft.citiesfetcher.transforms.JsonToCities;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class of the program. It uses {@code Settings}, {@code CitiesFeed} and
 * other needed classes to fetch cities from Json end point to a file.
 *
 * @author mg
 */
public class Fetcher {

    private final Settings settings;

    public Fetcher(Settings aSettings) {
        settings = aSettings;
    }

    public int work() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) settings.getCitySource().openConnection();
        try {
            connection.setRequestProperty("Accept", JSON_MIME_TYPE);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String contentType = connection.getContentType();
                String mimeAndEncoding[] = contentType.split(";");
                if (JSON_MIME_TYPE.equalsIgnoreCase(mimeAndEncoding[0])) {
                    Charset charset;
                    if (mimeAndEncoding.length > 1 && mimeAndEncoding[1].toLowerCase().startsWith(CHARSET_PREFIX)) {
                        charset = Charset.forName(mimeAndEncoding[1].substring(CHARSET_PREFIX.length()));
                    } else {
                        Logger.getLogger(Fetcher.class.getName()).log(Level.WARNING, null, String.format(MISSING_CHARSET_MSG, JSON_MIME_TYPE));
                        charset = StandardCharsets.UTF_8;
                    }
                    int fetched = 0;
                    try (InputStream body = connection.getInputStream();
                            OutputStream out
                            = new BufferedOutputStream(
                                    new FileOutputStream(
                                            settings
                                            .getDestination()))) {
                        Reader reader = new InputStreamReader(body, charset);
                        JsonFactory jsonFactory = new JsonFactory();
                        Function<City, StringBuilder> liner = new CityToCsv();
                        JsonParser parser = jsonFactory.createParser(reader);
                        CitiesFeed feed = new JsonToCities(parser);

                        Optional<City> city = feed.pull();
                        while (city.isPresent()) {
                            String line = liner.apply(city.get()).toString();
                            out.write(line.getBytes(StandardCharsets.UTF_8));
                            fetched++;
                            city = feed.pull();
                        }
                        out.flush();
                    }
                    return fetched;
                } else {
                    throw new IOException(String
                            .format(BAD_CONTENT_TYPE_MSG, JSON_MIME_TYPE));
                }
            } else {
                throw new IOException(connection.getResponseMessage());
            }
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Entry point of the program.
     *
     * @param args Command line arguments array.
     */
    public static void main(String[] args) {
        try {
            run(args);
        } catch (IOException ex) {
            Logger.getLogger(Fetcher.class.getName())
                    .log(Level.SEVERE, ex.getMessage());
        } catch (BadSettingsFormatException ex) {
            System.out.println(ex.getMessage());
            System.out.println();
            System.out.println(HELP_MSG);
        }
    }

    public static int run(String[] args) throws IOException, BadSettingsFormatException {
        Settings settings = Settings.parse(args);
        Fetcher fetcher = new Fetcher(settings);
        int fetched = fetcher.work();
        System.out.println(String.format(REPORT_MSG, fetched));
        System.out.println(settings.getDestination().getAbsolutePath());
        return fetched;
    }

    private static final String REPORT_MSG = "%d cities fetched and written to:";
    private static final String BAD_CONTENT_TYPE_MSG
            = "The endpoint server doesn't provide %s content type";
    private static final String MISSING_CHARSET_MSG
            = "The endpoint server doesn't provide charset information. "
            + "Falling to utf-8";
    private static final String CHARSET_PREFIX = "charset=";
    private static final String JSON_MIME_TYPE = "application/json";
    private static final String HELP_MSG = ""
            + "usage:\n"
            + "java -jar your-jar-module.jar CITY_NAME [file-name.csv]\n"
            + "CITY_NAME - The city name template to narrow the search.\n"
            + "file-name - File, the fetched cities to be written to. Optional.\n"
            + "If CITY_NAME contains spaces, enclose it in qoutes please.\n"
            + "For example: "
            + "java -jar your-jar-module.jar \"Frankfurt am mein\"";
}

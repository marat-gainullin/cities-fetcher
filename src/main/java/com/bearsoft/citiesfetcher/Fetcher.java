package com.bearsoft.citiesfetcher;

import com.bearsoft.citiesfetcher.model.City;
import com.bearsoft.citiesfetcher.transforms.CityToCsv;
import com.bearsoft.citiesfetcher.transforms.JsonToCities;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
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

    /**
     * Entry point of the program.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Settings settings = Settings.parse(args);

            URLConnection connection = settings.getCitySource().openConnection();
            connection.setRequestProperty("Accept", JSON_MIME_TYPE);
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
                try (InputStream body = connection.getInputStream();
                        OutputStream out
                        = new BufferedOutputStream(
                                new FileOutputStream(
                                        settings
                                        .getDestination()))) {
                    Reader reader = new InputStreamReader(body, charset);
                    JsonFactory jsonFactory = new JsonFactory();
                    JsonParser parser = jsonFactory.createParser(reader);
                    JsonToken arrayStart = parser.nextToken();
                    if(arrayStart != JsonToken.START_ARRAY)
                        throw new IOException(JSON_ARRAY_EXPECTED_MSG);
                    CitiesFeed feed = new JsonToCities(parser);
                    Function<City, StringBuilder> liner = new CityToCsv();
                    Optional<City> city;
                    while ((city = feed.pull()).isPresent()) {
                        parser.nextToken();
                        StringBuilder line = liner.apply(city.get());
                        byte[] bLine = line.toString()
                                .getBytes(StandardCharsets.UTF_8);
                        out.write(bLine);
                    }
                    out.flush();
                }
            } else {
                throw new IOException(String
                        .format(BAD_CONTENT_TYPE_MSG, JSON_MIME_TYPE));
            }
        } catch (BadSettingsFormatException | IOException ex) {
            Logger.getLogger(Fetcher.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    private static final String JSON_ARRAY_EXPECTED_MSG = "Json array expected";
    private static final String BAD_CONTENT_TYPE_MSG
            = "The endpoint server doesn't provide %s content type";
    private static final String MISSING_CHARSET_MSG
            = "The endpoint server doesn't provide charset information. "
            + "Falling to utf-8";
    private static final String CHARSET_PREFIX = "charset=";
    private static final String JSON_MIME_TYPE = "application/json";
}

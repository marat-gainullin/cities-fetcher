package com.bearsoft.citiesfetcher;

import com.bearsoft.citiesfetcher.feed.CitiesFeed;
import com.bearsoft.citiesfetcher.model.City;
import com.bearsoft.citiesfetcher.feed.BadCitiesJsonException;
import com.bearsoft.citiesfetcher.model.PartialCityJsonException;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
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
     * Settings to be used by this {@code Fetcher}.
     */
    private final Settings settings;

    /**
     * {@code Fetcher} with settings constructor.
     *
     * @param aSettings {@code Settings} instance for use by constructed
     * {@code Fetcher}
     */
    public Fetcher(final Settings aSettings) {
        settings = aSettings;
    }

    /**
     * Performs all fetching, parsing and writing work.
     *
     * @return Number of fetches cities.
     * @throws IOException if some problem occurs while File IO or while Json
     * handling.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    public final int work() throws IOException, PartialCityJsonException,
            BadCitiesJsonException {
        HttpURLConnection connection = (HttpURLConnection) settings
                .getCitySource().openConnection();
        try {
            connection.setRequestProperty("Accept", JSON_MIME_TYPE);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String contentType = connection.getContentType();
                String[] mimeAndEncoding = contentType.split(";");
                if (JSON_MIME_TYPE.equalsIgnoreCase(mimeAndEncoding[0])) {
                    Charset charset;
                    if (mimeAndEncoding.length > 1
                            && mimeAndEncoding[1]
                            .toLowerCase().startsWith(CHARSET_PREFIX)) {
                        charset = Charset.forName(mimeAndEncoding[1]
                                .substring(CHARSET_PREFIX.length()));
                    } else {
                        Logger.getLogger(Fetcher.class.getName())
                                .log(Level.WARNING, MISSING_CHARSET_MSG);
                        charset = StandardCharsets.UTF_8;
                    }
                    int fetched = 0;
                    try (InputStream body = connection.getInputStream();
                            OutputStream out
                            = new BufferedOutputStream(
                                    new FileOutputStream(
                                            settings
                                            .getDestination()))) {
                        
                        CitiesFeed feed = JsonCitiesFeed.create(body, charset);

                        Optional<City> city = feed.pull();
                        while (city.isPresent()) {
                            String line = Csv.to(city.get()).toString();
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
    public static void main(final String[] args) {
        try {
            run(args);
        } catch (IOException ex) {
            Logger.getLogger(Fetcher.class.getName())
                    .log(Level.SEVERE, ex.getMessage());
        } catch (BadArgumentsException ex) {
            System.out.println(ex.getMessage());
            System.out.println();
            System.out.println(HELP_MSG);
        } catch (BadCitiesJsonException | PartialCityJsonException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Working method of {@code Fetcher}. Unlike main method throws all
     * exceptions.
     *
     * @param args Arguments array.
     * @return number of fetched cities.
     * @throws IOException if it was thrown by {@code Fetcher.work()}
     * @throws BadArgumentsException may be thrown by (@code
     * Settings.parse()}
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    public static int run(final String[] args) throws IOException,
            BadArgumentsException,
            PartialCityJsonException,
            BadCitiesJsonException {
        Settings settings = Settings.parse(args);
        Fetcher fetcher = new Fetcher(settings);
        int fetched = fetcher.work();
        System.out.println("Written file:");
        System.out.println(settings.getDestination().getAbsolutePath());
        if (fetched > 0) {
            System.out.println(String.format(REPORT_MSG, fetched));
        }
        return fetched;
    }

    /**
     * Message about number of fetched cities.
     */
    private static final String REPORT_MSG = "%d cities fetched.";
    /**
     * Message indicating, that server didn't send a content-type header.
     */
    private static final String BAD_CONTENT_TYPE_MSG
            = "The endpoint server doesn't provide %s content type";
    /**
     * Message indicating, that server didn't send a charset information.
     */
    private static final String MISSING_CHARSET_MSG
            = "The endpoint server doesn't provide charset information. "
            + "Falling to utf-8";
    /**
     * "charset=" prefix for content-type header value parsing.
     */
    private static final String CHARSET_PREFIX = "charset=";
    /**
     * Jaon mime type name constant.
     */
    private static final String JSON_MIME_TYPE = "application/json";
    /**
     * Command line help message.
     */
    private static final String HELP_MSG = ""
            + "usage:\n"
            + "java -jar your-jar-file.jar CITY_NAME [file-name.csv]\n"
            + "CITY_NAME - The city name template to narrow the search.\n"
            + "file-name - File, fetched cities to be written to. Optional.\n"
            + "If CITY_NAME contains spaces, enclose it in qoutes please.\n"
            + "For example: "
            + "java -jar your-jar-file.jar \"Frankfurt am mein\"";
}

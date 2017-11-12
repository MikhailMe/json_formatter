import com.google.gson.*;
import com.sun.net.httpserver.HttpServer;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.stream.Collectors;

/**
 * Class implements Server interface
 * <p>
 * Task of class is checking valid Json file or not
 */
public class Formatter implements Server {

    @NotNull
    private final HttpServer server;
    @NotNull
    private final Gson builder;

    private static final int PORT = 80;
    private static final int CODE_OK = 200;
    private static final String ROOT = "/";

    /**
     * Checking Json file
     *
     * @throws IOException because method create() of HttpServer can throw IOException
     */
    public Formatter() throws IOException {
        this.builder = new GsonBuilder().setPrettyPrinting().create();
        this.server = HttpServer.create(new InetSocketAddress(PORT), 0);
        this.server.createContext(ROOT, http -> {
            InputStreamReader isr = new InputStreamReader(http.getRequestBody());
            final String jsonRequest = new BufferedReader(isr).lines().collect(Collectors.joining());
            String jsonResponse;
            try {
                Object object = builder.fromJson(jsonRequest, Object.class);
                jsonResponse = builder.toJson(object);
            } catch (JsonSyntaxException e) {
                JsonObject jsonError = new JsonObject();
                jsonError.addProperty("message", e.getMessage());
                jsonResponse = builder.toJson(jsonError);
            }
            http.sendResponseHeaders(CODE_OK, jsonResponse.length());
            http.getResponseBody().write(jsonResponse.getBytes());
            http.close();
        });
    }

    /**
     * Starting server and waiting for a Json files
     *
     * @param args - does not matter
     * @throws IOException - because constructor of Formatter can throw IOException
     */
    public static void main(String[] args) throws IOException {
        Formatter formatter = new Formatter();
        formatter.start();
        Runtime.getRuntime().addShutdownHook(new Thread(formatter::stop));
    }

    /**
     * Implements method of bind server to HTTP port and start listening.
     */
    @Override
    public void start() {
        this.server.start();
    }

    /**
     * Implements method of stop listening and free all the resources.
     */
    @Override
    public void stop() {
        this.server.stop(0);
    }
}

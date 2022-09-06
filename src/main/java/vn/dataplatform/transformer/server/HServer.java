package vn.dataplatform.transformer.server;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;
import vn.dataplatform.transformer.constants.MainConfig;

import static spark.Service.ignite;

public class HServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HServer.class.getName());

    public static void start() {
        Service router = ignite().port(MainConfig.HTTP_PORT).threadPool(10);
        router.options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        router.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        router.get("/health", (req, res) -> {
            res.type("application/json");
            JsonObject obj = new JsonObject();
            obj.addProperty("status", true);
            obj.addProperty("message", "Ilotusland Worker running...");
            return obj;
        });
    }
}

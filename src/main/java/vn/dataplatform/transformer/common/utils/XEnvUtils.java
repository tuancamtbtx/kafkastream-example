package vn.dataplatform.transformer.common.utils;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.experimental.UtilityClass;

@UtilityClass
public class XEnvUtils {
    public static Dotenv dotenv = Dotenv.configure().load();

    public static String getEnvironmentEntry(String key) {
        return dotenv.get(key);
    }
}

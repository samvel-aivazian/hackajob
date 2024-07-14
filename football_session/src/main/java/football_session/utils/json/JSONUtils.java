package football_session.utils.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONUtils {

    private static final Gson GSON = new Gson();

    private JSONUtils() {
    }

    public static <T> T toJsonObject(final Class<T> jsonClass, final String response) {
        final JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

        return GSON.fromJson(jsonObject, jsonClass);
    }

}

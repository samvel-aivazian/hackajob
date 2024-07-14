package football_session.utils.json.strategy;

import com.google.gson.JsonObject;

public interface JsonParserStrategy {
    <T> T parse(Class<T> jsonClass, JsonObject jsonObject);
}

package football_session.utils.json.strategy.impl;

import com.google.gson.JsonObject;
import football_session.utils.json.strategy.JsonParserStrategy;

public class DefaultJsonParserStrategy implements JsonParserStrategy {

    @Override
    public <T> T parse(Class<T> jsonClass, JsonObject jsonObject) {
        return null;
    }

}

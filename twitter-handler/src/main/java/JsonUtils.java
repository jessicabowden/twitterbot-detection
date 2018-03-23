import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.google.common.collect.Maps;

/**
 * Created by Jessica on 17/02/2015.
 */
public class JsonUtils {

    public Map<String, JSONObject> splitJson(JSONObject jsonObject) {
        Map<String, JSONObject> json = Maps.newHashMap();

        Set<String> keys = jsonObject.keySet();

        for (String key : keys) {
            if (key.equals("user")) {
                JSONObject userJson = jsonObject.getJSONObject("user");
                json.put("user", userJson);
            }
            if (key.equals("entities")) {
                JSONObject entitiesJson = jsonObject.getJSONObject("entities");
                json.put("entities", entitiesJson);
            }
        }

        json.put("tweet", jsonObject);

        return json;
    }

}

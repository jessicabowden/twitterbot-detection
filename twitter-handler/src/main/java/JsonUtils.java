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

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject("{\"in_reply_to_status_id_str\":null,\"in_reply_to_status_id\":null,\"possibly_sensitive\":false,\"coordinates\":null,\"created_at\":\"Sun Feb 08 20:31:16 +0000 2015\",\"truncated\":false,\"in_reply_to_user_id_str\":null,\"source\":\"<a href=\\\"http://instagram.com\\\" rel=\\\"nofollow\\\">Instagram<\\/a>\",\"retweet_count\":0,\"retweeted\":false,\"geo\":null,\"in_reply_to_screen_name\":null,\"entities\":{\"urls\":[{\"display_url\":\"instagram.com/p/y2osCTm-60/\",\"indices\":[39,61],\"expanded_url\":\"http://instagram.com/p/y2osCTm-60/\",\"url\":\"http://t.co/z5VlIYAGtF\"}],\"hashtags\":[],\"user_mentions\":[],\"symbols\":[]},\"id_str\":\"564521866879324161\",\"in_reply_to_user_id\":null,\"favorite_count\":0,\"id\":564521866879324161,\"text\":\"Don't need no diets. Fast food lasagne http://t.co/z5VlIYAGtF\",\"place\":null,\"contributors\":null,\"lang\":\"en\",\"user\":{\"utc_offset\":0,\"friends_count\":383,\"profile_image_url_https\":\"https://pbs.twimg.com/profile_images/522798955285868544/ebu0ovtQ_normal.jpeg\",\"listed_count\":1,\"profile_background_image_url\":\"http://abs.twimg.com/images/themes/theme2/bg.gif\",\"default_profile_image\":false,\"favourites_count\":282,\"description\":\"Junior Java Developer @Brandwatch. Computer Science Student @SussexUni. Also a fan of cuddly animals and hot drinks. Words are my own\",\"created_at\":\"Sat Nov 21 16:53:59 +0000 2009\",\"is_translator\":false,\"profile_background_image_url_https\":\"https://abs.twimg.com/images/themes/theme2/bg.gif\",\"protected\":false,\"screen_name\":\"jessicambowden\",\"id_str\":\"91608478\",\"profile_link_color\":\"1F98C7\",\"is_translation_enabled\":false,\"id\":91608478,\"geo_enabled\":true,\"profile_background_color\":\"C6E2EE\",\"lang\":\"en\",\"profile_sidebar_border_color\":\"C6E2EE\",\"profile_location\":null,\"profile_text_color\":\"663B12\",\"verified\":false,\"profile_image_url\":\"http://pbs.twimg.com/profile_images/522798955285868544/ebu0ovtQ_normal.jpeg\",\"time_zone\":\"London\",\"url\":null,\"contributors_enabled\":false,\"profile_background_tile\":false,\"entities\":{\"description\":{\"urls\":[]}},\"statuses_count\":1919,\"follow_request_sent\":false,\"followers_count\":155,\"profile_use_background_image\":true,\"default_profile\":false,\"following\":false,\"name\":\"Jessica Bowden\",\"location\":\"Brighton\",\"profile_sidebar_fill_color\":\"DAECF4\",\"notifications\":false},\"favorited\":false}");

        JsonUtils jsonUtils = new JsonUtils();

        Map<String, JSONObject> json = jsonUtils.splitJson(jsonObject);
    }

}

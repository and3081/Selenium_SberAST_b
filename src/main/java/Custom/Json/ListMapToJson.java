package Custom.Json;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Map;

public class ListMapToJson {
    public static String listMapToJson(List<Map<String, String>> list) {
        JSONArray json = new JSONArray(list);
        try {
            return json.toString(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}


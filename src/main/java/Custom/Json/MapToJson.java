package Custom.Json;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Map;

public class MapToJson {
    public static void listMapToJson(List<Map<String, String>> list) {
        JSONArray json = new JSONArray(list);
        //JSONArray json = JSONArray.fromObject(list);
        try {
            System.out.println(json.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            String json = mapper.writeValueAsString(map);
//            System.out.println("String is: " + json);
//        } catch (IOException e) { System.out.println("Error Map to Json" + e); }
    }
}


package apiconfigs;

import java.util.HashMap;
import java.util.Map;

public class HeaderConfig {

    /*
    Method to return defaultHeaders
    @return Map<String, String> - Key value pair of default headers
     */
    public static Map<String, String> defaultHeaders() {
        Map<String, String> defaultHeaders = new HashMap<String, String>();
        defaultHeaders.put("Content-Type", "application/json");
        return defaultHeaders;
    }
}

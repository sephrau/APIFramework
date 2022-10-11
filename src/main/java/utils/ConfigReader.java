package utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {

    public static Map<String, String> envDataMap = new HashMap<>();
    public static Properties propMain = new Properties();


    public static Map<String, String> getEnvDataMap() {
        String environment = System.getProperty("env");
        FileInputStream fisDev;
        try {
            if (environment!=null && environment.equalsIgnoreCase("dev"))
                fisDev = new FileInputStream(System.getProperty("user.dir") + "/envData/" + environment + ".properties");
            else
                fisDev = new FileInputStream(System.getProperty("user.dir") + "/envData/dev.properties");

            propMain.load(fisDev);
            envDataMap.put("baseUrl", propMain.getProperty("baseUrl"));

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return envDataMap;
    }

    public static Map<String, String> getConfigReader(){
        if(envDataMap.size() == 0) {
            envDataMap = getEnvDataMap();
        }
        return envDataMap;
    }
}
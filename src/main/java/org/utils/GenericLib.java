package org.utils;

import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class GenericLib {

    public static JSONObject getTestData(String filePath){
        // currently handling only json, if we have others we can add some conditions bassed on format of filepath like ending with .json
        if(filePath.contains(".json"))
            return readJson(filePath);
        else {
            System.out.println("Not Test data is found");
            return null;
        }
    }

    // To be used in future if we want to conver to hashmap
//    public static HashMap<String, String> convertToMap(JSONObject jsonObject) {
//        HashMap<String, String> map = new HashMap<>();
//        Iterator<String> keys = jsonObject.keys();
//
//        while (keys.hasNext()) {
//            String key = keys.next();
//            map.put(key, jsonObject.getString(key));
//        }
//        return map;
//    }
    public static JSONObject readJson(String filePath){
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(filePath);
            Object obj = parser.parse(reader);
            JSONObject jsonObj = (JSONObject) obj;
            return jsonObj;
        }
        catch (Exception e) {
            System.out.println("Issue while workig with json file");
            System.out.println(e.fillInStackTrace());
            System.exit(1);
        }
        return null;
    }



    public static Properties configReader(String filePath) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
        }
        return properties;
    }

    // Method to get a property value by key

//    public static void main(String[] args) {
////        JSONObject obj = GenericLib.readJson("TestData/shared.json");
////        JSONObject jsonObj = (JSONObject)obj.get("imagePost");
////        System.out.println(jsonObj.get("url"));
//
//        Properties property = configReader("config/config.properties");
//        System.out.println(property.getProperty("sc_url"));
//    }

}

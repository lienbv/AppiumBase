package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigProperties {
   private static Properties properties;
   private static void loadProperties(Properties prop, String fileName) throws IOException {
       FileInputStream fis = new FileInputStream(fileName);
       InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
       prop.load(isr);
       fis.close();
   }
   public static String getProperties(String dataFileNameProperties, String key) {
       properties = new Properties();
       String basePath = "src/test/resources/" + dataFileNameProperties +".properties";
       try {
           loadProperties(properties, basePath);
       } catch (IOException e) {
           System.out.println("Not found");
       }
       return properties.getProperty(key);
   }
}

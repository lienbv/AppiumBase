package common;

import config.ConfigProperties;

public class CommonVar {
    static String properties = "common";
    public static String BASE_URI = ConfigProperties.getProperties(properties,  "BASE_URI");
}

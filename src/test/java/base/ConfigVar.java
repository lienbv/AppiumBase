package base;

import config.ConfigProperties;

public class ConfigVar {
    static String properties = "configServer";
    public static String PLATFORM_NAME = ConfigProperties.getProperties(properties,  "PLATFORM_NAME");
    public static String AUTOMATION_NAME = ConfigProperties.getProperties(properties,  "AUTOMATION_NAME");
    public static String DEVICE_NAME = ConfigProperties.getProperties(properties,  "DEVICE_NAME");
    public static String PLATFORM_VERSION = ConfigProperties.getProperties(properties,  "PLATFORM_VERSION");
    public static int PORT = Integer.parseInt(ConfigProperties.getProperties(properties,  "PORT"));
    public static String IPADDRESS = ConfigProperties.getProperties(properties,  "IPADDRESS");
    public static String PATH = ConfigProperties.getProperties(properties,  "PATH");
}

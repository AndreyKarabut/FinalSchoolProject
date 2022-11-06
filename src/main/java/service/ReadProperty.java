package service;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperty {


    public static Properties read(String path) {
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream(path);
            property.load(fis);

            return property;

        } catch (
                IOException e) {
            return null;
        }

    }
}

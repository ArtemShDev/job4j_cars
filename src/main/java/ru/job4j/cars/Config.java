package ru.job4j.cars;

import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final Properties CFG = new Properties();

    public static Properties getCfg() {
        if (CFG.isEmpty()) {
            try {
                CFG.load(Config.class.getClassLoader().getResourceAsStream("dir.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return CFG;
    }
}

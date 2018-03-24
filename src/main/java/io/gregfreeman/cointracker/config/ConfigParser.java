package io.gregfreeman.cointracker.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;

public class ConfigParser
{
    final static Gson gson = new Gson();

    public static Config getConfig(String configFilePath) throws Exception {
        JsonReader reader = new JsonReader(new FileReader(configFilePath));

        return gson.fromJson(reader, Config.class);
    }
}

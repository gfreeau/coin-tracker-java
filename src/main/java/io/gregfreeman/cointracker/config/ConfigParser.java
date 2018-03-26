package io.gregfreeman.cointracker.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.HashMap;

public class ConfigParser
{
    final private static Gson gson = new Gson();

    public static class Config
    {
        public int topCoinLimit = 100;
        public double investmentAmount;
        public HashMap<String, Double> holdings;

        @Override
        public String toString()
        {
            return gson.toJson(this);
        }
    }

    public static Config getConfig(String configFilePath) throws Exception
    {
        JsonReader reader = new JsonReader(new FileReader(configFilePath));

        return gson.fromJson(reader, Config.class);
    }
}

package io.gregfreeman.cointracker.config;

import java.util.HashMap;

public class Config
{
    public float investmentAmount;
    public HashMap<String, Float> holdings;

    @Override
    public String toString() {
        return ConfigParser.gson.toJson(this);
    }
}

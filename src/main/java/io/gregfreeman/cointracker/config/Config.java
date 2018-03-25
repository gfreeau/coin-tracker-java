package io.gregfreeman.cointracker.config;

import com.google.gson.Gson;

import java.util.HashMap;

public class Config
{
    public double investmentAmount;
    public HashMap<String, Double> holdings;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

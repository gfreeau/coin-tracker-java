package io.gregfreeman.cointracker.coindata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CoinDataService
{
    final static Gson gson = new Gson();

    public static List<Coin> getCoinData(String currency, int limit) throws Exception {
        String url = String.format("https://api.coinmarketcap.com/v1/ticker/?convert=%s&limit=%d", currency, limit);
        String jsonText = UrlReader.readUrl(url);

        Type coinListType = new TypeToken<ArrayList<Coin>>(){}.getType();

        return gson.fromJson(jsonText, coinListType);
    }

    public static List<Coin> filterCoins(List<Coin> coins, HashMap<String, Float> portfolio) {
        return coins.stream()
                .filter(coin -> portfolio.containsKey(coin.symbol))
                .collect(Collectors.toList());
    }

    public static float percentDiff(float from, float to) {
        return ((to - from) / from) * 100;
    }
}

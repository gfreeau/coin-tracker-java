package io.gregfreeman.cointracker.coindata;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CoinDataService
{
    final private static Gson gson = new Gson();

    public static class Coin
    {
        public String name;
        public String symbol;
        @SerializedName("price_usd")
        public double priceUSD;
        @SerializedName("price_cad")
        public double priceCAD;
        @SerializedName("price_btc")
        public double priceBTC;
        @SerializedName("percent_change_24h")
        public double percentChange24h;

        @Override
        public String toString()
        {
            return gson.toJson(this);
        }
    }

    public static List<Coin> getCoinData(String currency, int limit) throws Exception
    {
        String url = String.format("https://api.coinmarketcap.com/v1/ticker/?convert=%s&limit=%d", currency, limit);
        String jsonText = UrlReader.readUrl(url);

        Type coinListType = new TypeToken<ArrayList<Coin>>(){}.getType();

        return gson.fromJson(jsonText, coinListType);
    }

    public static List<Coin> filterCoins(List<Coin> coins, HashMap<String, Double> portfolio)
    {
        return coins.stream()
                .filter(coin -> portfolio.containsKey(coin.symbol))
                .collect(Collectors.toList());
    }

    public static Coin findCoin(String symbol, List<Coin> coins)
    {
        return coins.stream()
                .filter(coin -> coin.symbol.equals(symbol))
                .findFirst()
                .orElse(null);
    }

    public static double percentDiff(double from, double to)
    {
        return ((to - from) / from) * 100;
    }
}

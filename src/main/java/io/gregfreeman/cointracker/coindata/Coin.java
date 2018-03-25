package io.gregfreeman.cointracker.coindata;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Coin
{
    public String name;
    public String symbol;
    public @SerializedName("price_usd") double priceUSD;
    public @SerializedName("price_cad") double priceCAD;
    public @SerializedName("price_btc") double priceBTC;
    public @SerializedName("percent_change_24h") double percentChange24h;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

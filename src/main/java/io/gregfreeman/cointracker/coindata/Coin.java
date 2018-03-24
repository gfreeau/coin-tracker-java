package io.gregfreeman.cointracker.coindata;

import com.google.gson.annotations.SerializedName;

public class Coin
{
    public String name;
    public String symbol;
    public @SerializedName("price_usd") float priceUSD;
    public @SerializedName("price_cad") float priceCAD;
    public @SerializedName("price_btc") float priceBTC;
    public @SerializedName("percent_change_24h") float percentChange24h;

    @Override
    public String toString() {
        return CoinDataService.gson.toJson(this);
    }
}

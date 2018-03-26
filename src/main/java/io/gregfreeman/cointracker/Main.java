package io.gregfreeman.cointracker;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import io.gregfreeman.cointracker.coindata.CoinDataService;
import io.gregfreeman.cointracker.coindata.CoinDataService.Coin;
import io.gregfreeman.cointracker.config.ConfigParser;
import io.gregfreeman.cointracker.config.ConfigParser.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        if (args.length == 0) {
            System.out.println("Config file path is a required argument");
            System.exit(1);
        }

        Config config = null;

        try {
            config = ConfigParser.getConfig(args[0]);
        } catch (Exception e) {
            System.out.println("Config file could not be read");
            System.exit(1);
        }

        List<Coin> allCoins = null;

        try {
            allCoins = CoinDataService.getCoinData("cad", config.topCoinLimit);
        } catch (Exception e) {
            System.out.println("Coin data is unavailable");
            System.exit(1);
        }

        List<Coin> coins = CoinDataService.filterCoins(allCoins, config.holdings);

        double totalCAD = 0;
        double totalUSD = 0;
        double totalBTC = 0;
        double totalETH = 0;
        double ETHBTCPrice = 0;

        Coin ETH = CoinDataService.findCoin("ETH", allCoins);

        if (ETH != null) {
            ETHBTCPrice = ETH.priceBTC;
        }

        for (Coin coin : coins) {
            double numberOfCoins = config.holdings.getOrDefault(coin.symbol, 0D);

            totalCAD += numberOfCoins * coin.priceCAD;
            totalUSD += numberOfCoins * coin.priceUSD;
            totalBTC += numberOfCoins * coin.priceBTC;
        }

        if (ETHBTCPrice > 0) {
            totalETH = totalBTC / ETHBTCPrice;
        }

        List<List<String>> rows = new ArrayList<>();

        for (Coin coin : coins) {
            double numberOfCoins = config.holdings.getOrDefault(coin.symbol, 0D);

            double priceCAD = numberOfCoins * coin.priceCAD;
            double priceUSD = numberOfCoins * coin.priceUSD;
            double priceBTC = numberOfCoins * coin.priceBTC;

            double percentage = 0;

            if (totalUSD > 0) {
                percentage = priceUSD / totalUSD * 100;
            }

            double priceETH = 0;
            double coinPriceETH = 0;

            if (ETHBTCPrice > 0) {
                priceETH = priceBTC / ETHBTCPrice;
                coinPriceETH = coin.priceBTC / ETHBTCPrice;
            }

            rows.add(Arrays.asList(
                    coin.symbol,
                    String.format("%.2f%%", percentage),
                    String.format("$%.4f", priceCAD),
                    String.format("$%.4f", coin.priceCAD),
                    String.format("$%.4f", priceUSD),
                    String.format("$%.4f", coin.priceUSD),
                    String.format("%.4f", priceETH),
                    String.format("%.8f", coinPriceETH),
                    String.format("%.4f", priceBTC),
                    String.format("%.8f", coin.priceBTC)
            ));
        }

        CWC_LongestLine cwc = new CWC_LongestLine();
        AsciiTable summaryTable = new AsciiTable();
        summaryTable.addRule();
        summaryTable.addRow("Return %", "Return", "CAD", "USD", "ETH", "BTC");
        summaryTable.addRule();
        summaryTable.addRow(
                String.format("%.2f%%", CoinDataService.percentDiff(config.investmentAmount, totalCAD)),
                String.format("$%.4f", totalCAD - config.investmentAmount),
                String.format("$%.4f", totalCAD),
                String.format("$%.4f", totalUSD),
                String.format("%.4f", totalETH),
                String.format("%.4f", totalBTC)
        );
        summaryTable.addRule();

        summaryTable.setPaddingLeft(1);
        summaryTable.setPaddingRight(1);
        summaryTable.getRenderer().setCWC(cwc);
        System.out.println(summaryTable.render());

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Name", "Alloc", "CAD", "Price (CAD)", "USD", "Price (USD)", "ETH", "Price (ETH)", "BTC", "Price (BTC)");
        table.addRule();
        for (List<String> row : rows) {
            table.addRow(row);
        }
        table.addRule();

        table.setPaddingLeft(1);
        table.setPaddingRight(1);
        table.getRenderer().setCWC(cwc);
        System.out.println(table.render());
    }
}
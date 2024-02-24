package org.example;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.endpoint.account.Account;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.bar.Bar;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.bar.enums.BarTimePeriod;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.trade.Trade;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.crypto.historical.bar.CryptoBarsResponse;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.StockBarsResponse;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.enums.BarAdjustment;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.enums.BarFeed;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.snapshot.Snapshot;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.trade.StockTradesResponse;
import net.jacobpeterson.alpaca.model.properties.DataAPIType;
import net.jacobpeterson.alpaca.model.properties.EndpointAPIType;
import net.jacobpeterson.alpaca.properties.AlpacaProperties;
import net.jacobpeterson.alpaca.rest.AlpacaClientException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {


// This constructor passes in a 'keyID' and 'secretKey' and uses the endpoint API type and data API
// type defined in the 'alpaca.example.properties' file (which default to 'paper' and 'iex' respectively)
//        String keyID = "PKGUU2S4MPHZJDM6INMZ";
//        String secretKey = "0pLfkXwi4Yxkzv7hYaxubfPJSfWjga1bYiV3ebGJ";
        String keyID = AlpacaProperties.KEY_ID;
        String secretKey = AlpacaProperties.SECRET_KEY;

// This constructor passes in a 'keyID' and 'secretKey' and uses the passed in endpoint API type
// and data API type (which are 'LIVE' and 'SIP' respectively in this example)
//        AlpacaAPI alpacaAPI = new AlpacaAPI(keyID, secretKey, EndpointAPIType.PAPER, DataAPIType.IEX);

        AlpacaAPI alpacaAPI = AlpacaAPI.builder()
                .withKeyID(keyID)
                .withSecretKey(secretKey)
                .withEndpointAPIType(EndpointAPIType.PAPER)
                .build();

//        try {
//            // Get 'Account' information and print it out
//            Account account = alpacaAPI.account().get();
//            System.out.println(account);
//        } catch (AlpacaClientException exception) {
//            exception.printStackTrace();
//        }

        try {
            // Get AAPL one hour, split-adjusted bars from 7/6/2021 market open
            // to 7/8/2021 market close from the SIP feed and print them out
            StockBarsResponse aaplBarsResponse = alpacaAPI.stockMarketData().getBars(
                    "AAPL",
                    ZonedDateTime.of(2023, 2, 1, 0, 0, 0, 0, ZoneId.of("America/New_York")),
                    ZonedDateTime.of(2024, 2, 1, 0, 0, 0, 0, ZoneId.of("America/New_York")),
                    null,
                    null,
                    1,
                    BarTimePeriod.DAY,
                    BarAdjustment.SPLIT,
                    BarFeed.SIP);
//            aaplBarsResponse.getBars().forEach(System.out::println);
            List<Double> closePrices = new ArrayList<>();
            for (Bar bar : aaplBarsResponse.getBars()) {
                double closePrice = bar.getClose();
                closePrices.add(closePrice);
            }

            System.out.println("Close Prices: " + closePrices);

            // Get AAPL first 10 trades on 7/8/2021 at market open and print them out
//            StockTradesResponse aaplTradesResponse = alpacaAPI.stockMarketData().getTrades(
//                    "AAPL",
//                    ZonedDateTime.of(2021, 7, 8, 9, 30, 0, 0, ZoneId.of("America/New_York")),
//                    ZonedDateTime.of(2021, 7, 8, 9, 31, 0, 0, ZoneId.of("America/New_York")),
//                    10,
//                    null);
//            aaplTradesResponse.getTrades().forEach(System.out::println);

            // Print out latest AAPL trade
//            Trade latestAAPLTrade = alpacaAPI.stockMarketData().getLatestTrade("AAPL").getTrade();
//            System.out.printf("Latest AAPL Trade: %s\n", latestAAPLTrade);

            // Print out snapshot of AAPL, GME, and TSLA
//            Map<String, Snapshot> snapshots = alpacaAPI.stockMarketData()
//                    .getSnapshots(Arrays.asList("AAPL", "GME", "TSLA"));
//            snapshots.forEach((symbol, snapshot) ->
//                    System.out.printf("Symbol: %s\nSnapshot: %s\n\n", symbol, snapshot));
        } catch (AlpacaClientException exception) {
            exception.printStackTrace();
        }

    }
}
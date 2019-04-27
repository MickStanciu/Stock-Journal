package com.example.tradelog.api.tools;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Function;

public class TsvImporter {
    public static void main(String[] args) {
        String input = "31 Oct 2018	PGR	A1	60.87	27.4%		SELL OPTION	CASH SECURED  PUT	70.0	Nov 16	1	2.50		250	6,750	3.70%	1.2	13 Nov 2018	BUY	-0.38	3.14%	212.00		212.00	13d";
        System.out.println(converter.apply(input));

    }

    private static Function<String, String> converter = p -> {

        String[] dataArray = p.split("\t");

        DateTimeFormatter dateInputFormatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm:ss", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(dataArray[0] + " 00:00:00", dateInputFormatter);
        OffsetDateTime localOffsetDateTime = localDateTime.atOffset(ZoneOffset.ofHours(10));

        DateTimeFormatter expInputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm:ss", Locale.ENGLISH);
        LocalDateTime expDateTime = LocalDateTime.parse(dataArray[9] + " " + localOffsetDateTime.getYear() + " 00:00:00", expInputFormatter);
        OffsetDateTime expOffsetDateTime = expDateTime.atOffset(ZoneOffset.ofHours(10));

        String action = dataArray[6];
        if (action.equals("SELL OPTION")) {
            action = "SELL";
        }

        String actionType = dataArray[7];
        if (actionType.equals("CASH SECURED  PUT")) {
            actionType = "PUT";
        }

        double brokerFee = 0.00;
        try {
            brokerFee = Double.parseDouble(dataArray[12]);
        } catch (NumberFormatException | NullPointerException nfe) {
            brokerFee = 0.00;
        }


        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("\t\"transactionId\": null,\n");
        sb.append("\t\"pairTransactionId\": null,\n");
        sb.append("\t\"accountId\": \"d79ec11a-2011-4423-ba01-3af8de0a3e14\",\n");
        sb.append("\t\"date\": \"" + localOffsetDateTime.toString() + "\"\n");
        sb.append("\t\"stockSymbol\": \"" + dataArray[1] + "\"\n");
        sb.append("\t\"stockPrice\": " + dataArray[3] + "\n");
        sb.append("\t\"strikePrice\": " + dataArray[8] + "\n");
        sb.append("\t\"expiryDate\": \"" + expOffsetDateTime.toString() + "\"\n");
        sb.append("\t\"impliedVolatility\": null,\n");
        sb.append("\t\"historicalImpliedVolatility\": null,\n");
        sb.append("\t\"profitProbability\": null,\n");
        sb.append("\t\"contracts\": " + dataArray[10] + ",\n");
        sb.append("\t\"premium\": " + dataArray[11] + ",\n");
        sb.append("\t\"action\": \"" + action + "\",\n");
        sb.append("\t\"actionType\": \"" + actionType + "\",\n");
        sb.append("\t\"premium\": " + dataArray[11] + ",\n");
        sb.append("\t\"brokerFees\": " + brokerFee + ",\n");
        sb.append("\t\"mark\": null\n");


        sb.append("},\n");
        sb.append("{\n");
        sb.append("}\n");

        for (String  s : dataArray) {
            System.out.println(s);
        }
        return sb.toString();
    };
}


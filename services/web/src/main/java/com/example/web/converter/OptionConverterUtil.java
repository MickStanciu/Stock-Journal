package com.example.web.converter;

import com.example.gateway.api.spec.model.ActionGW;
import com.example.gateway.api.spec.model.OptionJournalGWModel;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Function;

public class OptionConverterUtil {

    Function<OptionJournalGWModel, String> generateName = model -> {
        String operation = "";

        if (ActionGW.SELL == model.getAction()) {
            operation = "SOLD";
        } else if (ActionGW.BUY == model.getAction()){
            operation = "BOUGHT";
        }

        operation += " " + model.getContracts();
        operation += " " + model.getStockSymbol();
        operation += " " + model.getExpiryDate().format(DateTimeFormatter.ofPattern("MMMdd''yy").withLocale(Locale.US));

        DecimalFormat df = new DecimalFormat("#.##");

        operation += " " + df.format(model.getStrikePrice());
        operation += " " + model.getOptionType().toString();
        operation += " " + "@";
        operation += " " + df.format(model.getPremium());
        return operation;
    };

    Function<OptionJournalGWModel, String> generateOppositeActionName = model -> {
        String operation = "";

        if (ActionGW.SELL == model.getAction()) {
            operation = "BOUGHT";
        } else if (ActionGW.BUY == model.getAction()){
            operation = "SOLD";
        }

        operation += " " + model.getContracts();
        operation += " " + model.getStockSymbol();
        operation += " " + model.getExpiryDate().format(DateTimeFormatter.ofPattern("MMMdd''yy").withLocale(Locale.US));

        DecimalFormat df = new DecimalFormat("#.##");

        operation += " " + df.format(model.getStrikePrice());
        operation += " " + model.getOptionType().toString();
        operation += " " + "@";
        operation += " " + df.format(model.getPremium());
        return operation;
    };
}

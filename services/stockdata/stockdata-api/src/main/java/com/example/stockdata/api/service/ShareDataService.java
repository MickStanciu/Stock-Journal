package com.example.stockdata.api.service;

import com.example.stockdata.api.repository.ShareDataRepository;
import com.example.stockdata.api.model.ShareDataModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class ShareDataService {

    private ShareDataRepository repository;

    public ShareDataService(ShareDataRepository repository) {
        this.repository = repository;
    }

    public Optional<ShareDataModel> getShareData(String symbol) {
        Optional<ShareDataModel> optionalShareDataModel = this.repository.getBySymbol(symbol);

        if (optionalShareDataModel.isEmpty()) {
            //will generate error in Controller
            return optionalShareDataModel;
        }

        ShareDataModel model = optionalShareDataModel.get();

        if (model.getCalculatedTarget() != null) {
            return Optional.of(model);
        }

        //temporary code until batch function will write values in the calculated target
        BigDecimal peRatioFuture = BigDecimal.valueOf(model.getPeRatioFuture());
        BigDecimal epsFuture = BigDecimal.valueOf(model.getEpsFuture());

        MathContext mc = new MathContext(10, RoundingMode.HALF_UP);
        BigDecimal calculatedTarget = peRatioFuture.multiply(epsFuture).multiply(peRatioFuture.add(BigDecimal.ONE)).divide(peRatioFuture, mc);
        model.setCalculatedTarget(calculatedTarget);
        return Optional.of(model);
    }

    public Optional<ShareDataModel> setShareData(String symbol, ShareDataModel model) {

        boolean success = repository.setForSymbol(symbol, model);
        if (success) {
            return this.getShareData(symbol);
        }
        return Optional.empty();
    }
}

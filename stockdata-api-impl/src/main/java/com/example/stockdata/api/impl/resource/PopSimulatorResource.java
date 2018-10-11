package com.example.stockdata.api.impl.resource;

import com.example.stockdata.api.impl.service.SimulatorService;
import com.example.stockdata.api.spec.model.ProbabilitySimulatorResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/v1/sim", produces = "application/json")
public class PopSimulatorResource {

    private SimulatorService simulatorService;

    public PopSimulatorResource(SimulatorService simulatorService) {
        this.simulatorService = simulatorService;
    }

    @RequestMapping(value = "/probability", method = RequestMethod.GET)
    @ResponseBody
    public ProbabilitySimulatorResponse simulate(
            @RequestParam(name = "initialAmount") int initialAmount,
            @RequestParam(name = "winValue") int winValue,
            @RequestParam(name = "lossValue") int lossValue,
            @RequestParam(name = "winRate") int winRate
    ) {
        return simulatorService.simulatePop(initialAmount, winValue, lossValue, winRate);
    }
}

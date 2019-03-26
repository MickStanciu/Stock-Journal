package com.example.web.resource;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.web.service.TradeJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class MainController {

    private TradeJournalService tradeJournalService;

    @Autowired
    public MainController(TradeJournalService tradeJournalService) {
        this.tradeJournalService = tradeJournalService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("eventName", "FIFA 2018");

        List<OptionJournalGWModel> gwModelList = tradeJournalService.getAllByAccountId("d79ec11a-2011-4423-ba01-3af8de0a3e14");
        gwModelList.forEach(p -> System.out.println(p.getStockSymbol() + "  " + p.getPremium()));
        return mav;
    }


}

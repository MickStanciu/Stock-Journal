package com.example.web.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("eventName", "FIFA 2018");
        return mav;
    }

    /*
    [
    {
        "transactionId": "af94a639-e16f-463c-b2f3-129eae25db47",
        "accountId": "d79ec11a-2011-4423-ba01-3af8de0a3e14",
        "date": "2018-10-17T21:00:00+11:00",
        "mark": null,
        "stockSymbol": "MAT",
        "stockPrice": 14.83,
        "impliedVolatility": 53.6,
        "historicalImpliedVolatility": 0,
        "action": "SELL_OPTION",
        "actionType": "CASH_SECURED_PUT",
        "brokerFees": 0
    },
    {
        "transactionId": "a29806c6-5e8b-4133-a56c-803e8fca2827",
        "accountId": "d79ec11a-2011-4423-ba01-3af8de0a3e14",
        "date": "2018-10-31T21:00:00+11:00",
        "mark": null,
        "stockSymbol": "PGR",
        "stockPrice": 60.87,
        "impliedVolatility": 27.4,
        "historicalImpliedVolatility": 0,
        "action": "SELL_OPTION",
        "actionType": "CASH_SECURED_PUT",
        "brokerFees": 0
    }
]
     */

}

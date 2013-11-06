package ru.avm.sum.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.avm.sum.data.model.Basket;
import ru.avm.sum.data.model.Category;
import ru.avm.sum.data.model.Currency;
import ru.avm.sum.data.model.Deal;
import ru.avm.sum.business.service.SAccountant;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/rest" + CAccountant.BASE_URI)
public class CAccountant {

    public static final String BASE_URI = "/balance";

    public static final String TOTAL_URI = "/total";

    public static final String BASKET_URI = "/basket";

    public static final String BALANCE_URI = "/list";

    public static final String DEAL_URI = "/deal";

    public static final String GRAPH_URI = "/graph";

    public static final String MONEY_URI = "/money";

    private static long MILLIS_IN_DAY = 24 * 60 * 60 * 1000L;

    @Resource(name = "accountant")
    private SAccountant accountant;

    @RequestMapping(value = CAccountant.TOTAL_URI, method = RequestMethod.POST)
    @ResponseBody
    public List<Basket> total() {
        return accountant.total();
    }

    @RequestMapping(value = CAccountant.BASKET_URI, method = RequestMethod.POST)
    @ResponseBody
    public Basket update(@RequestBody Basket basket) {
        accountant.update(basket);
        return basket;
    }

    @RequestMapping(value = CAccountant.BASKET_URI, method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(@RequestBody Basket basket) {
        accountant.remove(basket);
    }

    @RequestMapping(value = CAccountant.BALANCE_URI, method = RequestMethod.POST)
    @ResponseBody
    public List<Deal> list(@RequestBody SelectRequest request) {
        return accountant.list(new Date(Math.max(request.getFrom() * MILLIS_IN_DAY - 1, 0L)),
                               new Date(request.getTill() * MILLIS_IN_DAY + 1),
                               request.getBasket(),
                               request.getCategory());
    }

    @RequestMapping(value = CAccountant.DEAL_URI, method = RequestMethod.POST)
    @ResponseBody
    public Deal update(@RequestBody Deal deal) {
        accountant.update(deal);
        return deal;
    }

    @RequestMapping(value = CAccountant.DEAL_URI, method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(@RequestBody Deal deal) {
        accountant.remove(deal);
    }

    @RequestMapping(value = CAccountant.GRAPH_URI, method = RequestMethod.POST)
    @ResponseBody
    public List<Category> graph() {
        return accountant.graph();
    }


    @RequestMapping(value = CAccountant.MONEY_URI, method = RequestMethod.POST)
    @ResponseBody
    public List<Currency> money() {
        return accountant.money();
    }

}

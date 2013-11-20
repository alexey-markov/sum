package ru.avm.sum.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.avm.sum.business.service.SAccountant;
import ru.avm.sum.data.model.money.Basket;
import ru.avm.sum.data.model.money.Category;
import ru.avm.sum.data.model.money.Currency;
import ru.avm.sum.data.model.money.Deal;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * uid: almarkov
 * Date: 20.02.13
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/rest" + CAccountant.BASE_URI)
public class CAccountant {

    public static final String BASE_URI = "/balance";

    public static final String TOTAL_URI = "/{uid}/total";

    public static final String BASKET_URI = "/{uid}/basket";

    public static final String BALANCE_URI = "/{uid}/list";

    public static final String DEAL_URI = "/{uid}/deal";

    public static final String GRAPH_URI = "/{uid}/graph";

    public static final String MONEY_URI = "/{uid}/money";

    private static long MILLIS_IN_DAY = 24 * 60 * 60 * 1000L;

    @Resource(name = "accountant")
    private SAccountant accountant;

    @RequestMapping(value = CAccountant.TOTAL_URI, method = RequestMethod.POST)
    @ResponseBody
    public List<Basket> total(@PathVariable String uid) {
        Assert.hasText(uid);
        return accountant.total(uid);
    }

    @RequestMapping(value = CAccountant.BASKET_URI, method = RequestMethod.POST)
    @ResponseBody
    public Basket update(@PathVariable String uid, @RequestBody Basket basket) {
        Assert.hasText(uid);
        accountant.update(uid, basket);
        return basket;
    }

    @RequestMapping(value = CAccountant.BASKET_URI, method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(@PathVariable String uid, @RequestBody Basket basket) {
        Assert.hasText(uid);
        accountant.remove(uid, basket);
    }

    @RequestMapping(value = CAccountant.BALANCE_URI, method = RequestMethod.POST)
    @ResponseBody
    public List<Deal> list(@PathVariable String uid, @RequestBody SelectRequest request) {
        Assert.hasText(uid);
        return accountant.list(uid,
                               new Date(Math.max(request.getFrom() * MILLIS_IN_DAY - 1, 0L)),
                               new Date(request.getTill() * MILLIS_IN_DAY + 1),
                               request.getBasket(),
                               request.getCategory());
    }

    @RequestMapping(value = CAccountant.DEAL_URI, method = RequestMethod.POST)
    @ResponseBody
    public Deal update(@PathVariable String uid, @RequestBody Deal deal) {
        Assert.hasText(uid);
        accountant.update(uid, deal);
        return deal;
    }

    @RequestMapping(value = CAccountant.DEAL_URI, method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(@PathVariable String uid, @RequestBody Deal deal) {
        Assert.hasText(uid);
        accountant.remove(uid, deal);
    }

    @RequestMapping(value = CAccountant.GRAPH_URI, method = RequestMethod.POST)
    @ResponseBody
    public List<Category> graph(@PathVariable String uid) {
        Assert.hasText(uid);
        return accountant.graph(uid);
    }

    @RequestMapping(value = CAccountant.MONEY_URI, method = RequestMethod.POST)
    @ResponseBody
    public List<Currency> money(@PathVariable String uid) {
        Assert.hasText(uid);
        return accountant.money(uid);
    }

}

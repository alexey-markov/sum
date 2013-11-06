package ru.avm.sum.business.service;

import org.springframework.stereotype.Service;
import ru.avm.sum.data.dao.IBalance;
import ru.avm.sum.data.model.Basket;
import ru.avm.sum.data.model.Category;
import ru.avm.sum.data.model.Currency;
import ru.avm.sum.data.model.Deal;
import ru.avm.sum.data.model.Money;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SAccountant implements IBalance {

    @Resource(name = "balance")
    private IBalance balance;

    @Override
    public boolean insert(Basket basket) {
        return balance.insert(basket);
    }

    @Override
    public boolean update(Basket basket) {
        return balance.update(basket);
    }

    @Override
    public boolean remove(Basket basket) {
        return balance.remove(basket);
    }

    @Override
    public List<Basket> total() {
        List<Basket> total = balance.total();
        if (!total.isEmpty()) {
            return total;
        }
        Currency currency = money().get(0);
        Basket basket = new Basket("NONAME", new Money(0L, currency), new Money(0L, currency));
        if (balance.insert(basket)) {
            return Collections.singletonList(basket);
        } else {
            throw new IllegalStateException("Can't save data.");
        }
    }

    @Override
    public boolean insert(Deal deal) {
        return balance.insert(deal);
    }

    @Override
    public boolean update(Deal deal) {
        return balance.insert(deal);
    }

    @Override
    public boolean remove(Deal deal) {
        return balance.insert(deal);
    }

    @Override
    public List<Deal> list(Date from, Date till, Basket basket, Category category) {
        return balance.list(from, till, basket, category);
    }

    @Override
    public boolean insert(Category category) {
        return balance.insert(category);
    }

    @Override
    public boolean update(Category category) {
        return balance.update(category);
    }

    @Override
    public boolean remove(Category category) {
        return balance.remove(category);
    }

    @Override
    public List<Category> graph() {
        List<Category> graph = balance.graph();
        if (!graph.isEmpty()) {
            return graph;
        }
        Category category = new Category("NONAME");
        if (balance.insert(category)) {
            return Collections.singletonList(category);
        } else {
            throw new IllegalStateException("Can't save data.");
        }
    }

    @Override
    public boolean insert(Currency currency) {
        return balance.insert(currency);
    }

    @Override
    public boolean update(Currency currency) {
        return balance.update(currency);
    }

    @Override
    public boolean remove(Currency currency) {
        return balance.remove(currency);
    }

    @Override
    public List<Currency> money() {
        List<Currency> money = balance.money();
        if (!money.isEmpty()) {
            return money;
        }
        Currency currency = new Currency("EGG", 1D);
        if (balance.insert(currency)) {
            return Collections.singletonList(currency);
        } else {
            throw new IllegalStateException("Can't save data.");
        }
    }
}

package ru.avm.sum.business.service;

import org.springframework.stereotype.Service;
import ru.avm.sum.data.dao.IBalance;
import ru.avm.sum.data.dao.file.Balance;
import ru.avm.sum.data.model.money.Basket;
import ru.avm.sum.data.model.money.Category;
import ru.avm.sum.data.model.money.Currency;
import ru.avm.sum.data.model.money.Deal;
import ru.avm.sum.data.model.money.Money;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SAccountant {

    private final Map<String, IBalance> balances = new HashMap<>();

    private final String root;

    public SAccountant() {
        this(null);
    }

    public SAccountant(String root) {
        this.root = root;
    }

    public boolean insert(String user, Basket basket) {
        return balance(user).insert(basket);
    }

    public boolean update(String user, Basket basket) {
        return balance(user).update(basket);
    }

    public boolean remove(String user, Basket basket) {
        return balance(user).remove(basket);
    }

    public List<Basket> total(String user) {
        List<Basket> total = balance(user).total();
        if (!total.isEmpty()) {
            return total;
        }
        Currency currency = money(user).get(0);
        Basket basket = new Basket("NONAME", new Money(0L, currency), new Money(0L, currency));
        if (balance(user).insert(basket)) {
            return Collections.singletonList(basket);
        } else {
            throw new IllegalStateException("Can't save data.");
        }
    }

    public boolean insert(String user, Deal deal) {
        return balance(user).insert(deal);
    }

    public boolean update(String user, Deal deal) {
        return balance(user).insert(deal);
    }

    public boolean remove(String user, Deal deal) {
        return balance(user).insert(deal);
    }

    public List<Deal> list(String user, Date from, Date till, Basket basket, Category category) {
        return balance(user).list(from, till, basket, category);
    }

    public boolean insert(String user, Category category) {
        return balance(user).insert(category);
    }

    public boolean update(String user, Category category) {
        return balance(user).update(category);
    }

    public boolean remove(String user, Category category) {
        return balance(user).remove(category);
    }

    public List<Category> graph(String user) {
        List<Category> graph = balance(user).graph();
        if (!graph.isEmpty()) {
            return graph;
        }
        Category category = new Category("NONAME");
        if (balance(user).insert(category)) {
            return Collections.singletonList(category);
        } else {
            throw new IllegalStateException("Can't save data.");
        }
    }

    public boolean insert(String user, Currency currency) {
        return balance(user).insert(currency);
    }

    public boolean update(String user, Currency currency) {
        return balance(user).update(currency);
    }

    public boolean remove(String user, Currency currency) {
        return balance(user).remove(currency);
    }

    public List<Currency> money(String user) {
        List<Currency> money = balance(user).money();
        if (!money.isEmpty()) {
            return money;
        }
        Currency currency = new Currency("EGG", 1D);
        if (balance(user).insert(currency)) {
            return Collections.singletonList(currency);
        } else {
            throw new IllegalStateException("Can't save data.");
        }
    }

    private IBalance balance(String user) {
        IBalance balance = balances.get(user);
        if (balance == null) {
            balances.put(user, balance = new Balance(root + File.separator + user));
        }
        return balance;
    }
}

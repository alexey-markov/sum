package ru.avm.sum.data.dao.stub;

import ru.avm.sum.data.dao.IBalance;
import ru.avm.sum.data.model.Basket;
import ru.avm.sum.data.model.Currency;
import ru.avm.sum.data.model.Deal;
import ru.avm.sum.data.model.Event;
import ru.avm.sum.data.model.Category;
import ru.avm.sum.data.model.Money;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public class Balance implements IBalance {

    @Override
    public boolean insert(Basket basket) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean update(Basket basket) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean remove(Basket basket) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Basket> total() {
        List<Basket> result = new ArrayList<>();
        Currency currency = new Currency("EGG", 1D);
        result.add(new Basket("FIRST", new Money(0L, currency), new Money(10L, currency)));
        result.add(new Basket("SECOND", new Money(0L, currency), new Money(-10L, currency)));
        return result;
    }

    @Override
    public boolean insert(Deal deal) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean update(Deal deal) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean remove(Deal deal) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Deal> list(Date from, Date till, Basket basket, Category category) {
        List<Deal> result = new ArrayList<>();
        Currency currency = new Currency("DEFAULT", 1D);
        result.add(new Deal(new Event(new Date(0L), new Category("one"), "one comment"), new Money(100L, currency), basket));
        result.add(new Deal(new Event(new Date(10L), new Category("two"), "two comment"), new Money(200L, currency), basket));
        result.add(new Deal(new Event(new Date(20L), new Category("one"), "an comment"), new Money(300L, currency), basket));
        return result;
    }

    @Override
    public boolean insert(Category category) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean update(Category category) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean remove(Category category) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Category> graph() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean insert(Currency currency) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean update(Currency currency) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean remove(Currency currency) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Currency> money() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

package ru.avm.sum.data.dao.sql;

import ru.avm.sum.data.dao.IBalance;
import ru.avm.sum.data.model.money.Basket;
import ru.avm.sum.data.model.money.Category;
import ru.avm.sum.data.model.money.Currency;
import ru.avm.sum.data.model.money.Deal;

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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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

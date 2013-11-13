package ru.avm.sum.data.dao;

import ru.avm.sum.data.model.money.Basket;
import ru.avm.sum.data.model.money.Currency;
import ru.avm.sum.data.model.money.Deal;
import ru.avm.sum.data.model.money.Category;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */
public interface IBalance {

    boolean insert(Basket basket);

    boolean update(Basket basket);

    boolean remove(Basket basket);

    List<Basket> total();

    boolean insert(Deal deal);

    boolean update(Deal deal);

    boolean remove(Deal deal);

    List<Deal> list(Date from, Date till, Basket basket, Category category);

    boolean insert(Category category);

    boolean update(Category category);

    boolean remove(Category category);

    List<Category> graph();

    boolean insert(Currency currency);

    boolean update(Currency currency);

    boolean remove(Currency currency);

    List<Currency> money();
}

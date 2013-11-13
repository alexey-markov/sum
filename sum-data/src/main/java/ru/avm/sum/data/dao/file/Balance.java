package ru.avm.sum.data.dao.file;

import org.apache.log4j.Logger;
import ru.avm.sum.data.dao.IBalance;
import ru.avm.sum.data.model.money.Basket;
import ru.avm.sum.data.model.money.Category;
import ru.avm.sum.data.model.money.Currency;
import ru.avm.sum.data.model.money.Deal;
import ru.avm.sum.data.model.money.Event;
import ru.avm.sum.data.model.money.Money;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public class Balance extends AFileStore implements IBalance {

    private static final Logger LOG = Logger.getLogger(Balance.class);

    public Balance(String path) {
        super(path);
    }

    @Override
    public boolean insert(Basket basket) {
        return update(basket, Basket.class);
    }

    @Override
    public boolean update(Basket basket) {
        return update(basket, Basket.class);
    }

    @Override
    public boolean remove(Basket basket) {
        return remove(basket, Basket.class)
               && remove(basket.getLand(), Money.class)
               && remove(basket.getLeft(), Money.class);
    }

    @Override
    public List<Basket> total() {
        return load(Basket.class);
    }

    @Override
    public boolean insert(Deal deal) {
        return update(deal, Deal.class);
    }

    @Override
    public boolean update(Deal deal) {
        return update(deal, Deal.class);
    }

    @Override
    public boolean remove(Deal deal) {
        return remove(deal, Deal.class)
               && remove(deal.getEvent(), Event.class)
               && remove(deal.getMoney(), Money.class);
    }

    @Override
    public List<Deal> list(Date from, Date till, Basket basket, Category category) {
        List<Deal> list = load(Deal.class);
        for (Iterator<Deal> iterator = list.iterator(); iterator.hasNext(); ) {
            Deal deal = iterator.next();
            // check basket
            if ((basket != null) && !deal.getBasket().equals(basket)) {
                iterator.remove();
                continue;
            }
            Event event = deal.getEvent();
            // check category
            if ((category != null) && !category.contains(event.getCategory())) {
                iterator.remove();
                continue;
            }
            Date date = event.getDate();
            // check date
            if (date.after(till) || date.before(from)) {
                iterator.remove();
                continue;
            }
        }
        return list;
    }

    @Override
    public boolean insert(Category category) {
        return update(category, Category.class);
    }

    @Override
    public boolean update(Category category) {
        return update(category, Category.class);
    }

    @Override
    public boolean remove(Category category) {
        for (Category child : category.getChildren()) {
            if (!remove(child)) {
                LOG.warn("can't remove child " + child + " from " + category);
            }
        }
        return remove(category, Category.class);
    }

    @Override
    public List<Category> graph() {
        return load(Category.class);
    }

    @Override
    public boolean insert(Currency currency) {
        return update(currency, Currency.class);
    }

    @Override
    public boolean update(Currency currency) {
        return update(currency, Currency.class);
    }

    @Override
    public boolean remove(Currency currency) {
        return remove(currency, Currency.class);
    }

    @Override
    public List<Currency> money() {
        return load(Currency.class);
    }

}

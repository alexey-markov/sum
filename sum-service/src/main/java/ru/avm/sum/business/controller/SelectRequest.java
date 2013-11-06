package ru.avm.sum.business.controller;

import org.codehaus.jackson.annotate.JsonProperty;
import ru.avm.sum.data.model.Basket;
import ru.avm.sum.data.model.Category;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Long: 20.02.13
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class SelectRequest {

    private final Long from;

    private final Long till;

    private final Basket basket;

    private final Category category;

    public SelectRequest(@JsonProperty("from") Long from,
                         @JsonProperty("till") Long till,
                         @JsonProperty("basket") Basket basket,
                         @JsonProperty("category") Category category) {
        this.from = from;
        this.till = till;
        this.basket = basket;
        this.category = category;
    }

    public Long getFrom() {
        return from;
    }

    public Long getTill() {
        return till;
    }

    public Category getCategory() {
        return category;
    }

    public Basket getBasket() {
        return basket;
    }
}

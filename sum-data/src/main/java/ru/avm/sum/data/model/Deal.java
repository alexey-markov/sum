package ru.avm.sum.data.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class Deal extends Identity {

    private final Event event;

    private final Money money;

    private final Basket basket;

    public Deal(@JsonProperty("event") Event event, @JsonProperty("money") Money money, @JsonProperty("basket") Basket basket) {
        this.event = event;
        this.money = money;
        this.basket = basket;
    }

    public Event getEvent() {
        return event;
    }

    public Money getMoney() {
        return money;
    }

    public Basket getBasket() {
        return basket;
    }
}

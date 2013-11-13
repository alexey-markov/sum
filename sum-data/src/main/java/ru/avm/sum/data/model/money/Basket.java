package ru.avm.sum.data.model.money;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 22.02.13
 * Time: 17:55
 * To change this template use File | Settings | File Templates.
 */
public class Basket extends Identity {

    private final String name;

    private final Money left;

    private final Money land;

    public Basket(@JsonProperty("name") String name, @JsonProperty("land") Money land, @JsonProperty("left") Money left) {
        this.name = name;
        this.land = land;
        this.left = left;
    }

    public String getName() {
        return name;
    }

    public Money getLeft() {
        return left;
    }

    public Money getLand() {
        return land;
    }

}

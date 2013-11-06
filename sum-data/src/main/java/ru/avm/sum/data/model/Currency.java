package ru.avm.sum.data.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class Currency extends Identity {

    private final String name;

    private final double rate;

    public Currency(@JsonProperty("name") String name, @JsonProperty("rate") double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

}

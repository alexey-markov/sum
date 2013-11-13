package ru.avm.sum.data.model.money;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class Event extends Identity {

    private final Date date;

    private final Category category;

    private final String comment;

    public Event(@JsonProperty("date") Date date, @JsonProperty("category") Category category, @JsonProperty("comment") String comment) {
        this.date = date;
        this.category = category;
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }

    public String getComment() {
        return comment;
    }
}

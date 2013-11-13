package ru.avm.sum.data.model.money;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public class Category extends Identity {

    private String name;

    private final List<Category> children = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public Category(@JsonProperty("name") String name, @JsonProperty("children") List<Category> list) {
        this.name = name;
        this.children.addAll((null == list) ? Collections.<Category>emptyList() : list);
    }

    public String getName() {
        return name;
    }

    public boolean add(Category category) {
        return children.add(category);
    }

    public boolean remove(Category category) {
        return children.remove(category);
    }

    public void clear() {
        children.clear();
    }

    public List<Category> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public boolean contains(Category category) {
        if (this.equals(category)) {
            return true;
        }
        for (Category child : children) {
            if (child.contains(category)) {
                return true;
            }
        }
        return false;
    }
}

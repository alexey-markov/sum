package ru.avm.sum.data.model.authority;

import org.codehaus.jackson.annotate.JsonProperty;
import ru.avm.sum.data.model.money.Identity;

public class Role extends Identity {

    private String name;

    public Role(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

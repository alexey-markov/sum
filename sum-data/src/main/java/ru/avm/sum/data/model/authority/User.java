package ru.avm.sum.data.model.authority;

import org.codehaus.jackson.annotate.JsonProperty;
import ru.avm.sum.data.model.money.Identity;

import java.io.Serializable;

public class User extends Identity implements Serializable {

    private String username;

    private String password;

    private Boolean enabled;

    private Role authority;

    public User(@JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("enabled") Boolean enabled,
                @JsonProperty("authority") Role authority) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Role getAuthority() {
        return this.authority;
    }

}
package com.gtan.singerdream.model.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by user on 15-7-30.
 */
public class ManagerLoginBody {
    private String name;
    private String password;

    public ManagerLoginBody() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagerLoginBody)) return false;

        ManagerLoginBody that = (ManagerLoginBody) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return !(getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null);

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
            return "ManagerLoginBody{" +
                    "name:'" + name + ',' +
                    ", password:'" + password+
                    '}';
    }
}

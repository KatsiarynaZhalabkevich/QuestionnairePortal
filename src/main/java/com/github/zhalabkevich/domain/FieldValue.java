package com.github.zhalabkevich.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class FieldValue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value="N/A";
    @ManyToOne
    private Field field;
    @ManyToOne
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldValue that = (FieldValue) o;
        return getValue().equals(that.getValue()) &&
                getField().equals(that.getField()) &&
                Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getField(), getUser());
    }

    @Override
    public String toString() {
        return "FieldValue{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", field=" + field +
                ", user=" + user +
                '}';
    }
}

package com.github.zhalabkevich.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
 public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Users.class)
    private Users user;
    @OneToOne(cascade = CascadeType.ALL)
    private FieldValue fieldValue;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public FieldValue getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(FieldValue fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return getUser().equals(userInfo.getUser()) &&
                getFieldValue().equals(userInfo.getFieldValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getFieldValue());
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", user=" + user +
                ", fieldValue=" + fieldValue +
                '}';
    }
}

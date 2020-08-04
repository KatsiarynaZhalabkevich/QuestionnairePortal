package com.github.zhalabkevich.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FieldOption implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String option;
    @ManyToOne
    private Field field;

    public FieldOption(){}

    public FieldOption(String option){
        this.option = option;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}

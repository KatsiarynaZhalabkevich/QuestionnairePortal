package com.github.zhalabkevich.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Field implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String label;
    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
    private List<FieldValue> values;
    @Enumerated(EnumType.STRING)
    private Type type;
    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
    private List<FieldOption> options;
    private boolean required;
    private boolean active;

    public Field(){}

    public Field(@NotNull String label, Type type, boolean required, boolean active) {
        this.label = label;
        this.type = type;
        this.required = required;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<FieldValue> getValues() {
        return values;
    }

    public void setValues(List<FieldValue> values) {
        this.values = values;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<FieldOption> getOptions() {
        return options;
    }

    public void setOptions(List<FieldOption> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return isRequired() == field.isRequired() &&
                isActive() == field.isActive() &&
                getLabel().equals(field.getLabel()) &&
                Objects.equals(getValues(), field.getValues()) &&
                getType() == field.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabel(), getValues(), getType(), isRequired(), isActive());
    }

    @Override
    public String toString() {
        return "Field{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", values=" + values +
                ", type=" + type +
                ", required=" + required +
                ", active=" + active +
                '}';
    }
}

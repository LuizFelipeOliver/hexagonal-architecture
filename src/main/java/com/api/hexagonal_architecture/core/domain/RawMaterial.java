package com.api.hexagonal_architecture.core.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class RawMaterial {
    private Long id;
    private String name;
    private BigDecimal price;

    public RawMaterial(Long id, String name, BigDecimal price) {
        validate(id, name, price);
        this.id = id;
        this.name = name;
        this.price = price;

    }

    public static RawMaterial of(Long id, String name, BigDecimal price) {
        return new RawMaterial(id, name, price);
    }

    private void validate(Long id, String name, BigDecimal price) {
        if (id == null)
            throw new IllegalArgumentException("ID cannot be null");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Price must be greater than zero");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RawMaterial))
            return false;
        RawMaterial that = (RawMaterial) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);

    }

    @Override
    public String toString() {
        return "RawMaterial{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

}

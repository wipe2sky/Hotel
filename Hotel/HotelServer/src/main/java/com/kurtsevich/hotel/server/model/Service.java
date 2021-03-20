package com.kurtsevich.hotel.server.model;

import java.util.Objects;

public class Service extends AEntity{
private String name;
private Float price;

    public Service(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(getId(), service.getId()) && Objects.equals(name, service.name) && Objects.equals(price, service.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, price);
    }

    @Override
    public String toString() {
        return "Service{" +
                "id= " + getId()+
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

package com.github.supercoding.web.dto;

import com.github.supercoding.repository.ItemEntity;

import java.util.Objects;

public class Item {

    private String id;
    private String name;
    private String type;
    private Integer price;
    private Spec spec;

    public Item(){

    }

    public Item(Integer id, ItemBody itemBody){
        this.id = String.valueOf(id);
        this.name = itemBody.getName();
        this.type = itemBody.getType();
        this.price = itemBody.getPrice();
        this.spec = itemBody.getSpec();
    }

    public Item(ItemEntity itemEntity){
        this.id = String.valueOf(itemEntity.getId());
        this.name = itemEntity.getName();
        this.type = itemEntity.getType();
        this.price = itemEntity.getPrice();
        this.spec = new Spec(itemEntity.getCpu(), itemEntity.getCapacity());
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getPrice() {
        return price;
    }

    public Spec getSpec() {
        return spec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id) && Objects.equals(name, item.name) && Objects.equals(type, item.type) && Objects.equals(price, item.price) && Objects.equals(spec, item.spec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

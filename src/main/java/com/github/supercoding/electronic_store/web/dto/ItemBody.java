package com.github.supercoding.electronic_store.web.dto;

public class ItemBody {

    private String id;
    private String name;
    private String type;
    private Integer price;
    private Spec spec;

    public ItemBody() {
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
}

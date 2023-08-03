package com.github.supercoding.repository.items;

import com.github.supercoding.web.dto.ItemBody;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "item")
public class ItemEntity {

    //필드 정의
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "type", length = 20, nullable = false)
    private String type;

    @Column(name = "price")
    private Integer price;

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "stock", columnDefinition = "DEFAULT 0 CHECK(stock) >= 0")
    private Integer stock;

    @Column(name = "cpu", length = 30)
    private String cpu;

    @Column(name = "capacity", length = 30)
    private String capacity;

    //생성자
    public ItemEntity(Integer id, String name, String type, Integer price, String cpu, String capacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.storeId = null;
        this.stock = 0;
        this.cpu = cpu;
        this.capacity = capacity;
    }

    public void setItemBody(ItemBody itemBody) {
        this.name = itemBody.getName();
        this.type = itemBody.getType();
        this.price = itemBody.getPrice();
        this.cpu = itemBody.getSpec().getCpu();
        this.capacity = itemBody.getSpec().getCapacity();
    }

//    public ItemEntity(Integer id, String name, String type, Integer price, Integer storeId, Integer stock, String cpu, String capacity) {
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.price = price;
//        this.storeId = storeId;
//        this.stock = stock;
//        this.cpu = cpu;
//        this.capacity = capacity;
//    }

    //getter
//    public Integer getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public Integer getPrice() {
//        return price;
//    }
//
//    public String getCpu() {
//        return cpu;
//    }
//
//    public Integer getStoreId() {
//        return storeId;
//    }
//
//    public void setStoreId(Integer storeId) {
//        this.storeId = storeId;
//    }
//
//    public Integer getStock() {
//        return stock;
//    }
//
//    public void setStock(Integer stock) {
//        this.stock = stock;
//    }
//
//    public String getCapacity() {
//        return capacity;
//    }



    //setter
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public void setPrice(Integer price) {
//        this.price = price;
//    }
//
//    public void setCpu(String cpu) {
//        this.cpu = cpu;
//    }
//
//    public void setCapacity(String capacity) {
//        this.capacity = capacity;
//    }

    //equals, hashcode

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ItemEntity that = (ItemEntity) o;
//        return id.equals(that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}

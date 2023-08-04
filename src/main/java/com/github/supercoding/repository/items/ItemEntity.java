package com.github.supercoding.repository.items;

import com.github.supercoding.repository.storeSales.StoreSalesEntity;
import com.github.supercoding.web.dto.ItemBody;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id") //FK
    private StoreSalesEntity storeSales;
    //private Integer storeId;

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
        //this.storeId = null;
        this.storeSales = null; //연관관계 매핑 여파
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

    public Optional<StoreSalesEntity> getStoreSales() {
        return Optional.ofNullable(storeSales);
    }
}

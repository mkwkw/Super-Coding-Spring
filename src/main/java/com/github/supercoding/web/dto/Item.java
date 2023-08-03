package com.github.supercoding.web.dto;

import com.github.supercoding.repository.items.ItemEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

    @ApiModelProperty(name = "id", value = "Item ID", example = "1")
    private String id;

    @ApiModelProperty(name = "name", value = "Item 이름", example = "Dell XPS 15")
    private String name;

    @ApiModelProperty(name = "type", value = "Item 기기 유형", example = "노트북")
    private String type;

    @ApiModelProperty(name = "price", value = "Item 가격", example = "1250000")
    private Integer price;

    //내부 객체가 있는 경우, 내부 객체에서 ApiModelProperty 지정
    private Spec spec;

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


}

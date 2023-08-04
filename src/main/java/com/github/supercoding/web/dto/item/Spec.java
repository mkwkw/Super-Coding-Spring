package com.github.supercoding.web.dto.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Spec {

    @ApiModelProperty(name = "cpu", value = "Item 스펙 - cpu", example = "i7")
    private String cpu;

    @ApiModelProperty(name = "capacity", value = "Item 스펙 - capacity", example = "512GB")
    private String capacity;
}

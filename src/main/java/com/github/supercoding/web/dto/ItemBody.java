package com.github.supercoding.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class ItemBody {

    private String id;
    private String name;
    private String type;
    private Integer price;
    private Spec spec;

}

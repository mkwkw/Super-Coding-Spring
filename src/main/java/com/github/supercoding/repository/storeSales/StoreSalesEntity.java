package com.github.supercoding.repository.storeSales;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Builder
public class StoreSalesEntity {

    private Integer id;
    private String storeName;
    private Integer amount;

}

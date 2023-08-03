package com.github.supercoding.repository.items;

import com.github.supercoding.web.dto.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectronicStoreItemJpaRepository extends JpaRepository<ItemEntity, Integer> { //사용하는 엔티티, pk 데이터형

    //types 내에 있는 type인 객체 탐색
    List<ItemEntity> findItemEntitiesByTypeIn(List<String> types);

    List<ItemEntity> findItemEntitiesByPriceLessThanEqualOrderByPriceAsc(Integer maxValue);
}

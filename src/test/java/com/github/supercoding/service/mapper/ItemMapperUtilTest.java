package com.github.supercoding.service.mapper;

import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.repository.storeSales.StoreSalesEntity;
import com.github.supercoding.web.dto.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ItemMapperUtilTest {

    @DisplayName("ItemMapper의 itemEntityToItem 메소드 테스트")
    @Test
    void itemEntityToItem() {
        //given
        ItemEntity itemEntity = ItemEntity.builder()
                .name("name")
                .type("type")
                .id(1)
                .price(1000)
                .stock(0)
                .cpu("CPU 1")
                .capacity("5G")
                .storeSales(new StoreSalesEntity())
                .build();

        //when
        Item item = ItemMapper.INSTANCE.itemEntityToItem(itemEntity);

        //then
        log.info("만들어진 item: "+item);
        assertEquals(itemEntity.getPrice(), item.getPrice());
        assertEquals(itemEntity.getId().toString(), item.getId());
        assertEquals(itemEntity.getCpu(), item.getSpec().getCpu());
        assertEquals(itemEntity.getCapacity(), item.getSpec().getCapacity());
    }
}
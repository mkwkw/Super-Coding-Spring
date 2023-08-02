package com.github.supercoding.service.mapper;

import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.web.dto.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    //싱글톤
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    //메소드
    @Mapping(target = "spec.cpu", source = "cpu")
    @Mapping(target = "spec.capacity", source = "capacity")
    //ItemEntity에서는 cpu -> Item에서는 spec 하위에 cpu
    //ItemEntity에서는 capacity -> Item에서는 spec 하위에 capacity
    Item itemEntityToItem(ItemEntity itemEntity); //자동으로 ItemEntity -> Item
}

package com.github.supercoding.web.controller.sample;

import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.item.Item;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class Chapter109Controller {

    private final ElectronicStoreItemService electronicStoreItemService; //@RequiredArgsConstructor로 생성자 따로 코드 작성 없이 Bean 주입 가능

    @ApiOperation("item price로 검색 - 쿼리 스트링")
    @GetMapping("/items-price")
    public List<Item> findItemByPrice( //httpSServletRequest 이용
            //@ApiParam(name = "price", value = "item price", example = "1000")
            HttpServletRequest httpServletRequest,
            //@RequestParam("max") Integer maxValue
            HttpServletResponse httpServletResponse
    ){ //items-query?id=1
        Integer maxValue = Integer.valueOf(httpServletRequest.getParameter("max"));
        return electronicStoreItemService.findItemsOrderByPrice(maxValue);
        //httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }
}

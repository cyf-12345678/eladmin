/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.gen.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.gen.domain.CarRentalOrder;
import me.zhengjie.gen.service.CarRentalOrderService;
import me.zhengjie.gen.service.dto.CarRentalOrderQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author itxc
* @date 2022-06-20
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "订单接口管理")
@RequestMapping("/api/carRentalOrder")
public class CarRentalOrderController {

    private final CarRentalOrderService carRentalOrderService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('carRentalOrder:list')")
    public void exportCarRentalOrder(HttpServletResponse response, CarRentalOrderQueryCriteria criteria) throws IOException {
        carRentalOrderService.download(carRentalOrderService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询订单接口")
    @ApiOperation("查询订单接口")
    @PreAuthorize("@el.check('carRentalOrder:list')")
    public ResponseEntity<Object> queryCarRentalOrder(CarRentalOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(carRentalOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping("/selectThisYearCount")
    @Log("查询订单接口")
    @ApiOperation("查询订单接口")
//    @PreAuthorize("@el.check('carRentalOrder:list')")
    public ResponseEntity<Object> selectCount(){
        return new ResponseEntity<>(carRentalOrderService.selectCount(),HttpStatus.OK);
    }


    @GetMapping("/selectMonthCount")
    @Log("查询订单接口")
    @ApiOperation("查询订单接口")
//    @PreAuthorize("@el.check('carRentalOrder:list')")
    public ResponseEntity<Object> selectMonthCount(){
        return new ResponseEntity<>(carRentalOrderService.selectMonthCount(),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增订单接口")
    @ApiOperation("新增订单接口")
    @PreAuthorize("@el.check('carRentalOrder:add')")
    public ResponseEntity<Object> createCarRentalOrder(@Validated @RequestBody CarRentalOrder resources){
        return new ResponseEntity<>(carRentalOrderService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改订单接口")
    @ApiOperation("修改订单接口")
    @PreAuthorize("@el.check('carRentalOrder:edit')")
    public ResponseEntity<Object> updateCarRentalOrder(@Validated @RequestBody CarRentalOrder resources){
        carRentalOrderService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除订单接口")
    @ApiOperation("删除订单接口")
    @PreAuthorize("@el.check('carRentalOrder:del')")
    public ResponseEntity<Object> deleteCarRentalOrder(@RequestBody Long[] ids) {
        carRentalOrderService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
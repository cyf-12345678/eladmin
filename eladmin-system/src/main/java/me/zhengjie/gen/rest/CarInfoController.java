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
import me.zhengjie.gen.domain.CarInfo;
import me.zhengjie.gen.service.CarInfoService;
import me.zhengjie.gen.service.dto.CarInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author itxc
* @date 2022-06-18
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "车辆管理接口管理")
@RequestMapping("/api/carInfo")
public class CarInfoController {

    private final CarInfoService carInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('carInfo:list')")
    public void exportCarInfo(HttpServletResponse response, CarInfoQueryCriteria criteria) throws IOException {
        carInfoService.download(carInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询车辆管理接口")
    @ApiOperation("查询车辆管理接口")
    @PreAuthorize("@el.check('carInfo:list')")
    public ResponseEntity<Object> queryCarInfo(CarInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(carInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping("/list")
    @Log("查询车辆管理接口(不分页)")
    @ApiOperation("查询车辆管理接口(不分页)")
//    @PreAuthorize("@el.check('carInfo:list')")
    public ResponseEntity<Object> list(CarInfoQueryCriteria criteria){
        return new ResponseEntity<>(carInfoService.queryAll(criteria).stream().filter(carInfo -> carInfo.getIsRent() == 0).collect(Collectors.toList()),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增车辆管理接口")
    @ApiOperation("新增车辆管理接口")
    @PreAuthorize("@el.check('carInfo:add')")
    public ResponseEntity<Object> createCarInfo(@Validated @RequestBody CarInfo resources){
        return new ResponseEntity<>(carInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改车辆管理接口")
    @ApiOperation("修改车辆管理接口")
    @PreAuthorize("@el.check('carInfo:edit')")
    public ResponseEntity<Object> updateCarInfo(@Validated @RequestBody CarInfo resources){
        carInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/doSure")
    @Log("车辆管理接口确认")
    @ApiOperation("车辆管理接口确认")
//    @PreAuthorize("@el.check('carInfo:edit')")
    public ResponseEntity<Object> doSure(@Validated @RequestBody CarInfo resources){
        carInfoService.doSure(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除车辆管理接口")
    @ApiOperation("删除车辆管理接口")
    @PreAuthorize("@el.check('carInfo:del')")
    public ResponseEntity<Object> deleteCarInfo(@RequestBody Long[] ids) {
        carInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
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
import me.zhengjie.gen.domain.CustomerInfo;
import me.zhengjie.gen.service.CustomerInfoService;
import me.zhengjie.gen.service.dto.CustomerInfoQueryCriteria;
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
* @date 2022-06-18
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "客户管理接口管理")
@RequestMapping("/api/customerInfo")
public class CustomerInfoController {

    private final CustomerInfoService customerInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('customerInfo:list')")
    public void exportCustomerInfo(HttpServletResponse response, CustomerInfoQueryCriteria criteria) throws IOException {
        customerInfoService.download(customerInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询客户管理接口")
    @ApiOperation("查询客户管理接口")
    @PreAuthorize("@el.check('customerInfo:list')")
    public ResponseEntity<Object> queryCustomerInfo(CustomerInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(customerInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping("/list")
    @Log("查询客户管理接口(不分页)")
    @ApiOperation("查询客户管理接口(不分页)")
//    @PreAuthorize("@el.check('customerInfo:list')")
    public ResponseEntity<Object> list(CustomerInfoQueryCriteria criteria){
        return new ResponseEntity<>(customerInfoService.queryAll(criteria),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增客户管理接口")
    @ApiOperation("新增客户管理接口")
    @PreAuthorize("@el.check('customerInfo:add')")
    public ResponseEntity<Object> createCustomerInfo(@Validated @RequestBody CustomerInfo resources){
        return new ResponseEntity<>(customerInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改客户管理接口")
    @ApiOperation("修改客户管理接口")
    @PreAuthorize("@el.check('customerInfo:edit')")
    public ResponseEntity<Object> updateCustomerInfo(@Validated @RequestBody CustomerInfo resources){
        customerInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除客户管理接口")
    @ApiOperation("删除客户管理接口")
    @PreAuthorize("@el.check('customerInfo:del')")
    public ResponseEntity<Object> deleteCustomerInfo(@RequestBody Long[] ids) {
        customerInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
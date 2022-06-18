package me.zhengjie.gen.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.gen.domain.CarInfo;
import me.zhengjie.gen.domain.CarRentalInfo;
import me.zhengjie.gen.service.ICarRentalService;
import me.zhengjie.gen.service.dto.CarInfoQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author 楼下小程
 * @date 2022/6/18
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "租车接口管理")
@RequestMapping("/api/carRentalInfo")
public class CarRentalController {

    private final ICarRentalService carRentalService;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('carRentalInfo:list')")
    public void exportCarInfo(HttpServletResponse response, CarInfoQueryCriteria criteria) throws IOException {
        carRentalService.download(carRentalService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询租车管理接口")
    @ApiOperation("查询租车管理接口")
    @PreAuthorize("@el.check('carRentalInfo:list')")
    public ResponseEntity<Object> queryCarInfo(CarInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(carRentalService.queryAll(criteria,pageable),HttpStatus.OK);
    }


    @PostMapping
    @Log("新增租车管理接口")
    @ApiOperation("新增租车管理接口")
    @PreAuthorize("@el.check('carRentalInfo:add')")
    public ResponseEntity<Object> createCarInfo(@Validated @RequestBody CarRentalInfo resources){
        carRentalService.create(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @Log("修改租车管理接口")
    @ApiOperation("修改租车管理接口")
    @PreAuthorize("@el.check('carRentalInfo:edit')")
    public ResponseEntity<Object> updateCarInfo(@Validated @RequestBody CarRentalInfo resources){
        carRentalService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除租车管理接口")
    @ApiOperation("删除租车管理接口")
    @PreAuthorize("@el.check('carRentalInfo:del')")
    public ResponseEntity<Object> deleteCarInfo(@RequestBody Long[] ids) {
        carRentalService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}

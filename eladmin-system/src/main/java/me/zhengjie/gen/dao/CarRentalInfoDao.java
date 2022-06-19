package me.zhengjie.gen.dao;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.gen.domain.CarRentalInfo;
import me.zhengjie.gen.service.dto.CarInfoQueryCriteria;
import me.zhengjie.gen.service.dto.CarRentalInfoDto;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 楼下小程
 * @date 2022/6/18
 */
@Repository
public interface CarRentalInfoDao extends BaseMapper<CarRentalInfoDto, CarRentalInfo> {

    List<CarRentalInfoDto> selectAll(@Param("query") CarInfoQueryCriteria criteria, Pageable pageable);

    CarRentalInfoDto findById(Long id);
}

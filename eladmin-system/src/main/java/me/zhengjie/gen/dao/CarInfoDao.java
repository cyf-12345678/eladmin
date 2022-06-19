package me.zhengjie.gen.dao;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.gen.domain.CarInfo;
import me.zhengjie.gen.service.dto.CarInfoDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 楼下小程
 * @date 2022/6/20
 */
@Repository
public interface CarInfoDao extends BaseMapper<CarInfoDto, CarInfo> {
    CarInfo getById(Long id);

    void updateById(CarInfo carInfo);
}

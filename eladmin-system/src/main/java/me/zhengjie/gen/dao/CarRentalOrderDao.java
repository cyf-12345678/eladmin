package me.zhengjie.gen.dao;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.gen.domain.CarRentalOrder;
import me.zhengjie.gen.service.dto.CarRentalOrderDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 楼下小程
 * @date 2022/6/22
 */
@Repository
public interface CarRentalOrderDao extends BaseMapper<CarRentalOrderDto, CarRentalOrder> {
    CarRentalOrderDto selectCount();

    CarRentalOrderDto selectMonthCount();
}

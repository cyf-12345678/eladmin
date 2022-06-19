package me.zhengjie.gen.dao;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.gen.domain.CustomerInfo;
import me.zhengjie.gen.service.dto.CustomerInfoDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 楼下小程
 * @date 2022/6/19
 */
@Repository
public interface CustomerInfoDao extends BaseMapper<CustomerInfoDto, CustomerInfo> {

    List<CustomerInfoDto> queryAll();

    CustomerInfo getById(Long customerId);

    void updateById(CustomerInfo customerInfo);
}

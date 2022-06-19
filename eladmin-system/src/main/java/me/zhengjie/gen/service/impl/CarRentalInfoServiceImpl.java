package me.zhengjie.gen.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.gen.dao.CarRentalInfoDao;
import me.zhengjie.gen.domain.CarInfo;
import me.zhengjie.gen.domain.CarRentalInfo;
import me.zhengjie.gen.domain.CustomerInfo;
import me.zhengjie.gen.repository.CarInfoRepository;
import me.zhengjie.gen.repository.CustomerInfoRepository;
import me.zhengjie.gen.service.ICarRentalService;
import me.zhengjie.gen.service.dto.CarInfoQueryCriteria;
import me.zhengjie.gen.service.dto.CarRentalInfoDto;
import me.zhengjie.gen.service.mapstruct.CarInfoMapper;
import me.zhengjie.gen.service.mapstruct.CustomerInfoMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 楼下小程
 * @date 2022/6/18
 */
@Service
@RequiredArgsConstructor
public class CarRentalInfoServiceImpl implements ICarRentalService {

    private final CarRentalInfoDao carRentalInfoDao;

    private final CarInfoRepository carInfoRepository;
    private final CarInfoMapper carInfoMapper;

    private final CustomerInfoRepository customerInfoRepository;
    private final CustomerInfoMapper customerInfoMapper;

    @Override
    public Map<String, Object> queryAll(CarInfoQueryCriteria criteria, Pageable pageable) {
        List<CarRentalInfoDto> list = carRentalInfoDao.selectAll(criteria, pageable);
        return PageUtil.toPage(list, list.size());
    }

    @Override
    public List<CarRentalInfoDto> queryAll(CarInfoQueryCriteria criteria) {
        return null;
    }

    @Override
    @Transactional
    public CarRentalInfoDto findById(Long id) {
        CarRentalInfoDto carRentalInfoDto = carRentalInfoDao.findById(id);
        ValidationUtil.isNull(carRentalInfoDto.getId(),"CarRentalInfoDto","id",id);
        return carRentalInfoDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CarRentalInfo resources) {
        // 这里的新增是修改数据库中的值，设置外键customer_id和is_rent状态改为被租
        CarInfo carInfo = carInfoRepository.findById(resources.getId()).orElseGet(CarInfo::new);
        ValidationUtil.isNull( carInfo.getId(),"CarInfo","id",resources.getId());
        carInfo.setCustomerId(resources.getCustomerId());
        // 已租用
        carInfo.setIsRent(1);
        carInfo.setUpdateTime(new Timestamp(new Date().getTime()));
        carInfo.setUpdateUser(SecurityUtils.getCurrentUserId());
        carInfoRepository.save(carInfo);

        CustomerInfo customerInfo = customerInfoRepository.findById(resources.getCustomerId()).orElseGet(CustomerInfo::new);
        ValidationUtil.isNull(customerInfo.getId(), "CustomerInfo", "id", resources.getCustomerId());
        customerInfo.setCarRentalStart(new Timestamp(new Date().getTime()));
        customerInfoRepository.save(customerInfo);
    }

    @Override
    public void update(CarRentalInfo resources) {

    }

    @Override
    @Transactional
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            CarInfo carInfo = carInfoRepository.getById(id);
            carInfoRepository.deleteById(id);
            Long customerId = carInfo.getCustomerId();
            customerInfoRepository.deleteById(customerId);
        }
    }

    @Override
    public void download(List<CarRentalInfoDto> all, HttpServletResponse response) throws IOException {
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (CarRentalInfoDto carRentalInfoDto : all) {
//        }
    }
}

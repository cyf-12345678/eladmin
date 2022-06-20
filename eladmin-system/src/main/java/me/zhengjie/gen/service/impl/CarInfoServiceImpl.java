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
package me.zhengjie.gen.service.impl;

import me.zhengjie.gen.dao.CarInfoDao;
import me.zhengjie.gen.domain.CarInfo;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.gen.repository.CarInfoRepository;
import me.zhengjie.gen.service.CarInfoService;
import me.zhengjie.gen.service.dto.CarInfoDto;
import me.zhengjie.gen.service.dto.CarInfoQueryCriteria;
import me.zhengjie.gen.service.mapstruct.CarInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author itxc
* @date 2022-06-18
**/
@Service
@RequiredArgsConstructor
public class CarInfoServiceImpl implements CarInfoService {

    private final CarInfoRepository carInfoRepository;
    private final CarInfoMapper carInfoMapper;

    private final CarInfoDao carInfoDao;

    @Override
    public Map<String,Object> queryAll(CarInfoQueryCriteria criteria, Pageable pageable){
        Page<CarInfo> page = carInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(carInfoMapper::toDto));
    }

    @Override
    public List<CarInfoDto> queryAll(CarInfoQueryCriteria criteria){
        return carInfoMapper.toDto(carInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CarInfoDto findById(Long id) {
        CarInfo carInfo = carInfoRepository.findById(id).orElseGet(CarInfo::new);
        ValidationUtil.isNull(carInfo.getId(),"CarInfo","id",id);
        return carInfoMapper.toDto(carInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CarInfoDto create(CarInfo resources) {
        return carInfoMapper.toDto(carInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CarInfo resources) {
        CarInfo carInfo = carInfoRepository.findById(resources.getId()).orElseGet(CarInfo::new);
        ValidationUtil.isNull( carInfo.getId(),"CarInfo","id",resources.getId());
        carInfo.copy(resources);
        carInfoRepository.save(carInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            carInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CarInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CarInfoDto carInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("车辆类型", carInfo.getCarType());
            map.put("车辆品牌", carInfo.getCarBrand());
            map.put("创建人", carInfo.getCreateUser());
            map.put("创建时间", carInfo.getCreateTime());
            map.put("更新人", carInfo.getUpdateUser());
            map.put("更新时间", carInfo.getUpdateTime());
            map.put("客户id", carInfo.getCustomerId());
            map.put("是否损坏", carInfo.getIsDamaged());
            map.put("车牌号", carInfo.getCarCode());
            map.put("座位数", carInfo.getCarSeat());
            map.put("每小时租车费", carInfo.getCarRentalFee());
            map.put("押金", carInfo.getCarDeposit());
            map.put("是否被租", carInfo.getIsRent());
            map.put("损坏赔偿", carInfo.getCarCompensate());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional
    public void doSure(CarInfo resources) {
        CarInfo carInfo = carInfoDao.getById(resources.getId());


        if (resources.getCarCompensate() != null) {
            carInfo.setIsRent(0);
            carInfo.setIsDamaged(1);
            carInfo.setCarCompensate(resources.getCarCompensate());
        } else {
            carInfo.setIsRent(0);
            carInfo.setIsDamaged(0);
        }
        carInfo.setUpdateUser(SecurityUtils.getCurrentUserId());
        carInfo.setUpdateTime(new Timestamp(new Date().getTime()));
        carInfoDao.updateById(carInfo);
    }
}
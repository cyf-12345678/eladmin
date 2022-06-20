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

import me.zhengjie.gen.domain.CarRentalOrder;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.gen.repository.CarRentalOrderRepository;
import me.zhengjie.gen.service.CarRentalOrderService;
import me.zhengjie.gen.service.dto.CarRentalOrderDto;
import me.zhengjie.gen.service.dto.CarRentalOrderQueryCriteria;
import me.zhengjie.gen.service.mapstruct.CarRentalOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author itxc
* @date 2022-06-20
**/
@Service
@RequiredArgsConstructor
public class CarRentalOrderServiceImpl implements CarRentalOrderService {

    private final CarRentalOrderRepository carRentalOrderRepository;
    private final CarRentalOrderMapper carRentalOrderMapper;

    @Override
    public Map<String,Object> queryAll(CarRentalOrderQueryCriteria criteria, Pageable pageable){
        Page<CarRentalOrder> page = carRentalOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(carRentalOrderMapper::toDto));
    }

    @Override
    public List<CarRentalOrderDto> queryAll(CarRentalOrderQueryCriteria criteria){
        return carRentalOrderMapper.toDto(carRentalOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CarRentalOrderDto findById(Long id) {
        CarRentalOrder carRentalOrder = carRentalOrderRepository.findById(id).orElseGet(CarRentalOrder::new);
        ValidationUtil.isNull(carRentalOrder.getId(),"CarRentalOrder","id",id);
        return carRentalOrderMapper.toDto(carRentalOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CarRentalOrderDto create(CarRentalOrder resources) {
        return carRentalOrderMapper.toDto(carRentalOrderRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CarRentalOrder resources) {
        CarRentalOrder carRentalOrder = carRentalOrderRepository.findById(resources.getId()).orElseGet(CarRentalOrder::new);
        ValidationUtil.isNull( carRentalOrder.getId(),"CarRentalOrder","id",resources.getId());
        carRentalOrder.copy(resources);
        carRentalOrderRepository.save(carRentalOrder);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            carRentalOrderRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CarRentalOrderDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CarRentalOrderDto carRentalOrder : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("客户姓名", carRentalOrder.getName());
            map.put("联系电话", carRentalOrder.getUserPhone());
            map.put("证件号码", carRentalOrder.getUserCertifcode());
            map.put("租车时长", carRentalOrder.getCarRentalTime());
            map.put("租车开始时间", carRentalOrder.getCarRentalStart());
            map.put("租车结束时间", carRentalOrder.getCarRentalEnd());
            map.put("车辆类型(字典)", carRentalOrder.getCarType());
            map.put("车牌号", carRentalOrder.getCarCode());
            map.put("座位数", carRentalOrder.getCarSeat());
            map.put("车辆品牌（字典）", carRentalOrder.getCarBrand());
            map.put("客户id", carRentalOrder.getCustomerId());
            map.put("每小时租车费", carRentalOrder.getCarRentalFee());
            map.put("押金", carRentalOrder.getCarDeposit());
            map.put("是否被租", carRentalOrder.getIsRent());
            map.put("是否损坏", carRentalOrder.getIsDamaged());
            map.put("损坏赔偿", carRentalOrder.getCarCompensate());
            map.put("创建人", carRentalOrder.getCreateUser());
            map.put("创建时间", carRentalOrder.getCreateTime());
            map.put("更新人", carRentalOrder.getUpdateUser());
            map.put("更新时间", carRentalOrder.getUpdateTime());
            map.put("用户来源", carRentalOrder.getUserSource());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
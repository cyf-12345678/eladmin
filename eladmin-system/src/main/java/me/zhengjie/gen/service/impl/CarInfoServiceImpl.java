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

import me.zhengjie.gen.domain.CarInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
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
* @description ����ʵ��
* @author itxc
* @date 2022-06-17
**/
@Service
@RequiredArgsConstructor
public class CarInfoServiceImpl implements CarInfoService {

    private final CarInfoRepository carInfoRepository;
    private final CarInfoMapper carInfoMapper;

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
            map.put("��������", carInfo.getCarType());
            map.put("��������", carInfo.getCarOwner());
            map.put("��ϵ�绰", carInfo.getCarPhone());
            map.put("���ƺ�", carInfo.getCarCode());
            map.put("������Ƭ", carInfo.getCarPhoto());
            map.put("����Ʒ��", carInfo.getCarBrand());
            map.put("������ɫ", carInfo.getCarColor());
            map.put("������", carInfo.getCreateUser());
            map.put("����ʱ��", carInfo.getCreateTime());
            map.put("������", carInfo.getUpdateUser());
            map.put("����ʱ��", carInfo.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
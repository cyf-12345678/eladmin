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

import me.zhengjie.gen.dao.CustomerInfoDao;
import me.zhengjie.gen.domain.CustomerInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.gen.repository.CustomerInfoRepository;
import me.zhengjie.gen.service.CustomerInfoService;
import me.zhengjie.gen.service.dto.CustomerInfoDto;
import me.zhengjie.gen.service.dto.CustomerInfoQueryCriteria;
import me.zhengjie.gen.service.mapstruct.CustomerInfoMapper;
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
* @date 2022-06-18
**/
@Service
@RequiredArgsConstructor
public class CustomerInfoServiceImpl implements CustomerInfoService {

    private final CustomerInfoRepository customerInfoRepository;
    private final CustomerInfoMapper customerInfoMapper;

    private final CustomerInfoDao customerInfoDao;

    @Override
    public Map<String,Object> queryAll(CustomerInfoQueryCriteria criteria, Pageable pageable){
        Page<CustomerInfo> page = customerInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(customerInfoMapper::toDto));
    }

    @Override
    public List<CustomerInfoDto> queryAll(CustomerInfoQueryCriteria criteria){
        List<CustomerInfoDto> list = customerInfoDao.queryAll();
        return list;
    }

    @Override
    @Transactional
    public CustomerInfoDto findById(Long id) {
        CustomerInfo customerInfo = customerInfoRepository.findById(id).orElseGet(CustomerInfo::new);
        ValidationUtil.isNull(customerInfo.getId(),"CustomerInfo","id",id);
        return customerInfoMapper.toDto(customerInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerInfoDto create(CustomerInfo resources) {
        return customerInfoMapper.toDto(customerInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CustomerInfo resources) {
        CustomerInfo customerInfo = customerInfoRepository.findById(resources.getId()).orElseGet(CustomerInfo::new);
        ValidationUtil.isNull( customerInfo.getId(),"CustomerInfo","id",resources.getId());
        customerInfo.copy(resources);
        customerInfoRepository.save(customerInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            customerInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CustomerInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CustomerInfoDto customerInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("客户姓名", customerInfo.getName());
            map.put("联系电话", customerInfo.getUserPhone());
            map.put("证件号码", customerInfo.getUserCertifcode());
            map.put("创建人", customerInfo.getCreateUser());
            map.put("创建时间", customerInfo.getCreateTime());
            map.put("更新人", customerInfo.getUpdateUser());
            map.put("更新时间", customerInfo.getUpdateTime());
            map.put("租车时长", customerInfo.getCarRentalTime());
            map.put("租车开始时间", customerInfo.getCarRentalStart());
            map.put("租车结束时间", customerInfo.getCarRentalEnd());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
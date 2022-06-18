package me.zhengjie.gen.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.gen.dao.CarRentalInfoMapper;
import me.zhengjie.gen.domain.CarInfo;
import me.zhengjie.gen.domain.CarRentalInfo;
import me.zhengjie.gen.service.ICarRentalService;
import me.zhengjie.gen.service.dto.CarInfoQueryCriteria;
import me.zhengjie.gen.service.dto.CarRentalInfoDto;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 楼下小程
 * @date 2022/6/18
 */
@Service
@RequiredArgsConstructor
public class CarRentalInfoServiceImpl implements ICarRentalService {

    private final CarRentalInfoMapper carRentalInfoMapper;

    @Override
    public Map<String, Object> queryAll(CarInfoQueryCriteria criteria, Pageable pageable) {
        List<CarRentalInfoDto> list = carRentalInfoMapper.selectAll(criteria, pageable);
        return PageUtil.toPage(list, list.size());
    }

    @Override
    public List<CarRentalInfoDto> queryAll(CarInfoQueryCriteria criteria) {
        return null;
    }

    @Override
    public CarRentalInfoDto findById(Long id) {
        return null;
    }

    @Override
    public CarRentalInfoDto create(CarRentalInfo resources) {
        return null;
    }

    @Override
    public void update(CarRentalInfo resources) {

    }

    @Override
    public void deleteAll(Long[] ids) {

    }

    @Override
    public void download(List<CarRentalInfoDto> all, HttpServletResponse response) throws IOException {

    }
}

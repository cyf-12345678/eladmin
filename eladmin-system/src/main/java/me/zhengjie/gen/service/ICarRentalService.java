package me.zhengjie.gen.service;

import me.zhengjie.gen.domain.CarRentalInfo;
import me.zhengjie.gen.service.dto.*;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 楼下小程
 * @date 2022/6/18
 */
public interface ICarRentalService {
    /**
     * 查询数据分页
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String,Object> queryAll(CarInfoQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     * @param criteria 条件参数
     * @return List<CarRentalInfoDto>
     */
    List<CarRentalInfoDto> queryAll(CarInfoQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return CarRentalInfoDto
     */
    CarRentalInfoDto findById(Long id);

    /**
     * 创建
     * @param resources /
     */
    void create(CarRentalInfo resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(CarRentalInfo resources);

    /**
     * 多选删除
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 导出数据
     * @param all 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<CarRentalInfoDto> all, HttpServletResponse response) throws IOException;

    /**
     * 还车
     * @param id
     */
    void editMethod(Long id);
}

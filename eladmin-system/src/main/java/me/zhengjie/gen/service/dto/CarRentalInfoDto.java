package me.zhengjie.gen.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 楼下小程
 * @date 2022/6/18
 */
@Data
public class CarRentalInfoDto implements Serializable {

    /** 用户id */
    private Long id;

    /** 客户姓名 */
    private String name;

    /** 联系电话 */
    private String userPhone;

    /** 证件号码 */
    private String userCertifcode;

    /** 车辆类型 */
    private Integer carType;

    /** 车辆品牌 */
    private Integer carBrand;

    /** 车牌号 */
    private String carCode;

    /** 每小时租车费 */
    private BigDecimal carRentalFee;

    /** 押金 */
    private BigDecimal carDeposit;

    /** 是否损坏 */
    private Integer isDamaged;

    /** 损坏赔偿 */
    private BigDecimal carCompensate;
}

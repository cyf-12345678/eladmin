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
package me.zhengjie.gen.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author itxc
* @date 2022-06-18
**/
@Entity
@Data
@Table(name="car_info")
public class CarInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`car_type`")
    @ApiModelProperty(value = "车辆类型")
    private Integer carType;

    @Column(name = "`car_brand`")
    @ApiModelProperty(value = "车辆品牌")
    private Integer carBrand;

    @Column(name = "`create_user`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "创建人")
    private Long createUser;

    @Column(name = "`create_time`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "`update_user`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "更新人")
    private Long updateUser;

    @Column(name = "`update_time`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "`customer_id`")
    @ApiModelProperty(value = "客户id")
    private Long customerId;

    @Column(name = "`is_damaged`")
    @ApiModelProperty(value = "是否损坏")
    private Integer isDamaged;

    @Column(name = "`car_code`")
    @ApiModelProperty(value = "车牌号")
    private String carCode;

    @Column(name = "`car_seat`")
    @ApiModelProperty(value = "座位数")
    private Long carSeat;

    @Column(name = "`car_rental_fee`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "每小时租车费")
    private BigDecimal carRentalFee;

    @Column(name = "`car_deposit`")
    @ApiModelProperty(value = "押金")
    private BigDecimal carDeposit;

    @Column(name = "`is_rent`")
    @ApiModelProperty(value = "是否被租")
    private Integer isRent;

    @Column(name = "`car_compensate`")
    @ApiModelProperty(value = "损坏赔偿")
    private BigDecimal carCompensate;

    public void copy(CarInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

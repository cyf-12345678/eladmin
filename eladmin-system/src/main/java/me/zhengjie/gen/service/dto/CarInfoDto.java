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
package me.zhengjie.gen.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author itxc
* @date 2022-06-18
**/
@Data
public class CarInfoDto implements Serializable {

    /** id */
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

    /** 创建人 */
    private Long createUser;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新人 */
    private Long updateUser;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 客户id */
    private Long customerId;

    /** 是否损坏 */
    private Integer isDamaged;

    /** 车牌号 */
    private String carCode;

    /** 座位数 */
    private Long carSeat;

    /** 每小时租车费 */
    private BigDecimal carRentalFee;

    /** 押金 */
    private BigDecimal carDeposit;

    /** 是否被租 */
    private Integer isRent;

    /** 损坏赔偿 */
    private BigDecimal carCompensate;

}
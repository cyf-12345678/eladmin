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
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author itxc
* @date 2022-06-17
**/
@Data
public class CarInfoDto implements Serializable {

    /** id */
    private Long id;

    /** 车辆类型 */
    private Integer carType;

    /** 车主姓名 */
    private String carOwner;

    /** 联系电话 */
    private String carPhone;

    /** 车牌号 */
    private String carCode;

    /** 车辆照片 */
    private String carPhoto;

    /** 车辆品牌 */
    private Integer carBrand;

    /** 车身颜色 */
    private Integer carColor;

    /** 创建人 */
    private Long createUser;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新人 */
    private Long updateUser;

    /** 更新时间 */
    private Timestamp updateTime;
}
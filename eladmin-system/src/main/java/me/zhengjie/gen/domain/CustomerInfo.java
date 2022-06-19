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
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author itxc
* @date 2022-06-18
**/
@Entity
@Data
@Table(name="customer_info")
public class CustomerInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`name`")
    @ApiModelProperty(value = "客户姓名")
    private String name;

    @Column(name = "`user_phone`")
    @ApiModelProperty(value = "联系电话")
    private String userPhone;

    @Column(name = "`user_certifcode`")
    @ApiModelProperty(value = "证件号码")
    private String userCertifcode;

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

    @Column(name = "`car_rental_Time`")
    @ApiModelProperty(value = "租车时长")
    private Double carRentalTime;

    @Column(name = "`car_rental_start`")
    @ApiModelProperty(value = "租车开始时间")
    private Timestamp carRentalStart;

    @Column(name = "`car_rental_end`")
    @ApiModelProperty(value = "租车结束时间")
    private Timestamp carRentalEnd;

    public void copy(CustomerInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

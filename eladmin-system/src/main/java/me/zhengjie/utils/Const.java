package me.zhengjie.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Const", description="通用类")
public class Const {

    @ApiModelProperty(value = "字典管理 cache key")
    public static final String SYS_DICT_KEY = "sys_dict:";

}

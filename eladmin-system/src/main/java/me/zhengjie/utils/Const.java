package me.zhengjie.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Const", description="通用类")
public class Const {

    @ApiModelProperty(value = "UTF-8 字符集")
    public static final String UTF8 = "UTF-8";

    @ApiModelProperty(value = "GBK 字符集")
    public static final String GBK = "GBK";

    @ApiModelProperty(value = "http请求")
    public static final String HTTP = "http://";

    @ApiModelProperty(value = "https请求")
    public static final String HTTPS = "https://";

    @ApiModelProperty(value = "通用成功标识")
    public static final Integer SUCCESS = 0;

    @ApiModelProperty(value = "通用失败标识")
    public static final Integer FAIL = 1;

    @ApiModelProperty(value = "登录成功")
    public static final String LOGIN_SUCCESS = "Success";

    @ApiModelProperty(value = "注销")
    public static final String LOGOUT = "Logout";

    @ApiModelProperty(value = "登录失败")
    public static final String LOGIN_FAIL = "Error";

    @ApiModelProperty(value = "验证码 redis key")
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    @ApiModelProperty(value = "登录用户 redis key")
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    @ApiModelProperty(value = "防重提交 redis key")
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    @ApiModelProperty(value = "验证码有效期（分钟）")
    public static final Integer CAPTCHA_EXPIRATION = 2;

    @ApiModelProperty(value = "令牌")
    public static final String TOKEN = "token";

    @ApiModelProperty(value = "令牌前缀")
    public static final String TOKEN_PREFIX = "Bearer ";

    @ApiModelProperty(value = "令牌前缀")
    public static final String LOGIN_USER_KEY = "login_user_key";

    @ApiModelProperty(value = "用户ID")
    public static final String JWT_USERID = "userid";

    @ApiModelProperty(value = "用户名称")
    public static final String JWT_USERNAME = "sub";

    @ApiModelProperty(value = "用户头像")
    public static final String JWT_AVATAR = "avatar";

    @ApiModelProperty(value = "创建时间")
    public static final String JWT_CREATED = "created";

    @ApiModelProperty(value = "用户权限")
    public static final String JWT_AUTHORITIES = "authorities";

    @ApiModelProperty(value = "参数管理 cache key")
    public static final String SYS_CONFIG_KEY = "sys_config:";

    @ApiModelProperty(value = "字典管理 cache key")
    public static final String SYS_DICT_KEY = "sys_dict:";

    @ApiModelProperty(value = "资源映射路径 前缀")
    public static final String RESOURCE_PREFIX = "/profile";

    @ApiModelProperty(value = "凭证 Access Token")
    public static final String Access_Token = "access_token:";
}

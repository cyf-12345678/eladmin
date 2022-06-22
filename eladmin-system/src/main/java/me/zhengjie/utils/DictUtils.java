package me.zhengjie.utils;


import me.zhengjie.modules.system.domain.DictDetail;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 字典工具类
 * 
 *
 */
public class DictUtils {
    /**
     * 分隔符
     */
    public static final String SEPARATOR = ",";

    /**
     * 设置字典缓存
     * 
     * @param key 参数键
     * @param dictDatas 字典数据列表
     */
    public static void setDictCache(String key, List<DictDetail> dictDatas){
        SpringContextHolder.getBean(RedisUtils.class).setCacheObject(getCacheKey(key), dictDatas);
    }

    /**
     * 获取字典缓存
     * 
     * @param key 参数键
     * @return dictDatas 字典数据列表
     */
    public static List<DictDetail> getDictCache(String key){
        Object cacheObj = SpringContextHolder.getBean(RedisUtils.class).getCacheObject(getCacheKey(key));
        if (StringUtil.isNotNull(cacheObj)){
            List<DictDetail> dictDatas = StringUtil.cast(cacheObj);
            return dictDatas;
        }
        return null;
    }

    /**
     * 根据字典类型和字典值获取字典标签
     * 
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue){
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     * 
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel){
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }

    /**
     * 根据字典类型和字典值获取字典标签
     * 
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue, String separator){
        StringBuilder propertyString = new StringBuilder();
        List<DictDetail> datas = getDictCache(dictType);

        if (StringUtil.containsAny(separator, dictValue) && StringUtil.isNotEmpty(datas)){
            for (DictDetail dict : datas){
                for (String value : dictValue.split(separator)){
                    if (value.equals(dict.getValue())){
                        propertyString.append(dict.getLabel() + separator);
                        break;
                    }
                }
            }
        }
        else{
            for (DictDetail dict : datas){
                if (dictValue.equals(dict.getValue())){
                    return dict.getLabel();
                }
            }
        }
        return StringUtil.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     * 
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel, String separator){
        StringBuilder propertyString = new StringBuilder();
        List<DictDetail> datas = getDictCache(dictType);

        if (StringUtil.containsAny(separator, dictLabel) && StringUtil.isNotEmpty(datas)){
            for (DictDetail dict : datas){
                for (String label : dictLabel.split(separator)){
                    if (label.equals(dict.getLabel())){
                        propertyString.append(dict.getValue() + separator);
                        break;
                    }
                }
            }
        }
        else{
            for (DictDetail dict : datas){
                if (dictLabel.equals(dict.getLabel())){
                    return dict.getValue();
                }
            }
        }
        return StringUtil.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 清空字典缓存
     */
    public static void clearDictCache(){
        Set<Object> keys = SpringContextHolder.getBean(RedisUtils.class).keys(Const.SYS_DICT_KEY+ "*");
        SpringContextHolder.getBean(RedisUtils.class).deleteObject(keys);
    }

    /**
     * 设置cache key
     * 
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey){
        return Const.SYS_DICT_KEY + configKey;
    }
}

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
package me.zhengjie.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.*;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.domain.GenConfig;
import me.zhengjie.domain.ColumnInfo;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.*;

import static me.zhengjie.utils.FileUtil.SYS_TEM_DIR;

/**
 * ��������
 *
 * @author Zheng Jie
 * @date 2019-01-02
 */
@Slf4j
@SuppressWarnings({"unchecked", "all"})
public class GenUtil {

    private static final String TIMESTAMP = "Timestamp";

    private static final String BIGDECIMAL = "BigDecimal";

    public static final String PK = "PRI";

    public static final String EXTRA = "auto_increment";

    /**
     * ��ȡ��˴���ģ������
     *
     * @return List
     */
    private static List<String> getAdminTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("Entity");
        templateNames.add("Dto");
        templateNames.add("Mapper");
        templateNames.add("Controller");
        templateNames.add("QueryCriteria");
        templateNames.add("Service");
        templateNames.add("ServiceImpl");
        templateNames.add("Repository");
        return templateNames;
    }

    /**
     * ��ȡǰ�˴���ģ������
     *
     * @return List
     */
    private static List<String> getFrontTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("index");
        templateNames.add("api");
        return templateNames;
    }

    public static List<Map<String, Object>> preview(List<ColumnInfo> columns, GenConfig genConfig) {
        Map<String, Object> genMap = getGenMap(columns, genConfig);
        List<Map<String, Object>> genList = new ArrayList<>();
        // ��ȡ���ģ��
        List<String> templates = getAdminTemplateNames();
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        for (String templateName : templates) {
            Map<String, Object> map = new HashMap<>(1);
            Template template = engine.getTemplate("generator/admin/" + templateName + ".ftl");
            map.put("content", template.render(genMap));
            map.put("name", templateName);
            genList.add(map);
        }
        // ��ȡǰ��ģ��
        templates = getFrontTemplateNames();
        for (String templateName : templates) {
            Map<String, Object> map = new HashMap<>(1);
            Template template = engine.getTemplate("generator/front/" + templateName + ".ftl");
            map.put(templateName, template.render(genMap));
            map.put("content", template.render(genMap));
            map.put("name", templateName);
            genList.add(map);
        }
        return genList;
    }

    public static String download(List<ColumnInfo> columns, GenConfig genConfig) throws IOException {
        // ƴ�ӵ�·����/tmpeladmin-gen-temp/�����·����Linux����Ҫroot�û�����Ȩ�޴���,��root�û���Ȩ�޴����ʧ�ܣ�����Ϊ�� /tmp/eladmin-gen-temp/
        // String tempPath =SYS_TEM_DIR + "eladmin-gen-temp" + File.separator + genConfig.getTableName() + File.separator;
        String tempPath = SYS_TEM_DIR + "eladmin-gen-temp" + File.separator + genConfig.getTableName() + File.separator;
        Map<String, Object> genMap = getGenMap(columns, genConfig);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        // ���ɺ�˴���
        List<String> templates = getAdminTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("generator/admin/" + templateName + ".ftl");
            String filePath = getAdminFilePath(templateName, genConfig, genMap.get("className").toString(), tempPath + "eladmin" + File.separator);
            assert filePath != null;
            File file = new File(filePath);
            // ����Ǹ�������
            if (!genConfig.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // ���ɴ���
            genFile(file, template, genMap);
        }
        // ����ǰ�˴���
        templates = getFrontTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("generator/front/" + templateName + ".ftl");
            String path = tempPath + "eladmin-web" + File.separator;
            String apiPath = path + "src" + File.separator + "api" + File.separator;
            String srcPath = path + "src" + File.separator + "views" + File.separator + genMap.get("changeClassName").toString() + File.separator;
            String filePath = getFrontFilePath(templateName, apiPath, srcPath, genMap.get("changeClassName").toString());
            assert filePath != null;
            File file = new File(filePath);
            // ����Ǹ�������
            if (!genConfig.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // ���ɴ���
            genFile(file, template, genMap);
        }
        return tempPath;
    }

    public static void generatorCode(List<ColumnInfo> columnInfos, GenConfig genConfig) throws IOException {
        Map<String, Object> genMap = getGenMap(columnInfos, genConfig);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        // ���ɺ�˴���
        List<String> templates = getAdminTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("generator/admin/" + templateName + ".ftl");
            String rootPath = System.getProperty("user.dir");
            String filePath = getAdminFilePath(templateName, genConfig, genMap.get("className").toString(), rootPath);

            assert filePath != null;
            File file = new File(filePath);

            // ����Ǹ�������
            if (!genConfig.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // ���ɴ���
            genFile(file, template, genMap);
        }

        // ����ǰ�˴���
        templates = getFrontTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("generator/front/" + templateName + ".ftl");
            String filePath = getFrontFilePath(templateName, genConfig.getApiPath(), genConfig.getPath(), genMap.get("changeClassName").toString());

            assert filePath != null;
            File file = new File(filePath);

            // ����Ǹ�������
            if (!genConfig.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // ���ɴ���
            genFile(file, template, genMap);
        }
    }

    // ��ȡģ������
    private static Map<String, Object> getGenMap(List<ColumnInfo> columnInfos, GenConfig genConfig) {
        // �洢ģ���ֶ�����
        Map<String, Object> genMap = new HashMap<>(16);
        // �ӿڱ���
        genMap.put("apiAlias", genConfig.getApiAlias());
        // ������
        genMap.put("package", genConfig.getPack());
        // ģ������
        genMap.put("moduleName", genConfig.getModuleName());
        // ����
        genMap.put("author", genConfig.getAuthor());
        // ��������
        genMap.put("date", LocalDate.now().toString());
        // ����
        genMap.put("tableName", genConfig.getTableName());
        // ��д��ͷ������
        String className = StringUtils.toCapitalizeCamelCase(genConfig.getTableName());
        // Сд��ͷ������
        String changeClassName = StringUtils.toCamelCase(genConfig.getTableName());
        // �ж��Ƿ�ȥ����ǰ׺
        if (StringUtils.isNotEmpty(genConfig.getPrefix())) {
            className = StringUtils.toCapitalizeCamelCase(StrUtil.removePrefix(genConfig.getTableName(), genConfig.getPrefix()));
            changeClassName = StringUtils.toCamelCase(StrUtil.removePrefix(genConfig.getTableName(), genConfig.getPrefix()));
            changeClassName = StringUtils.uncapitalize(changeClassName);
        }
        // ��������
        genMap.put("className", className);
        // ����Сд��ͷ������
        genMap.put("changeClassName", changeClassName);
        // ���� Timestamp �ֶ�
        genMap.put("hasTimestamp", false);
        // ��ѯ���д��� Timestamp �ֶ�
        genMap.put("queryHasTimestamp", false);
        // ���� BigDecimal �ֶ�
        genMap.put("hasBigDecimal", false);
        // ��ѯ���д��� BigDecimal �ֶ�
        genMap.put("queryHasBigDecimal", false);
        // �Ƿ���Ҫ������ѯ
        genMap.put("hasQuery", false);
        // ��������
        genMap.put("auto", false);
        // �����ֵ�
        genMap.put("hasDict", false);
        // ��������ע��
        genMap.put("hasDateAnnotation", false);
        // �����ֶ���Ϣ
        List<Map<String, Object>> columns = new ArrayList<>();
        // �����ѯ�ֶε���Ϣ
        List<Map<String, Object>> queryColumns = new ArrayList<>();
        // �洢�ֵ���Ϣ
        List<String> dicts = new ArrayList<>();
        // �洢 between ��Ϣ
        List<Map<String, Object>> betweens = new ArrayList<>();
        // �洢��Ϊ�յ��ֶ���Ϣ
        List<Map<String, Object>> isNotNullColumns = new ArrayList<>();

        for (ColumnInfo column : columnInfos) {
            Map<String, Object> listMap = new HashMap<>(16);
            // �ֶ�����
            listMap.put("remark", column.getRemark());
            // �ֶ�����
            listMap.put("columnKey", column.getKeyType());
            // ��������
            String colType = ColUtil.cloToJava(column.getColumnType());
            // Сд��ͷ���ֶ���
            String changeColumnName = StringUtils.toCamelCase(column.getColumnName());
            // ��д��ͷ���ֶ���
            String capitalColumnName = StringUtils.toCapitalizeCamelCase(column.getColumnName());
            if (PK.equals(column.getKeyType())) {
                // �洢��������
                genMap.put("pkColumnType", colType);
                // �洢Сд��ͷ���ֶ���
                genMap.put("pkChangeColName", changeColumnName);
                // �洢��д��ͷ���ֶ���
                genMap.put("pkCapitalColName", capitalColumnName);
            }
            // �Ƿ���� Timestamp ���͵��ֶ�
            if (TIMESTAMP.equals(colType)) {
                genMap.put("hasTimestamp", true);
            }
            // �Ƿ���� BigDecimal ���͵��ֶ�
            if (BIGDECIMAL.equals(colType)) {
                genMap.put("hasBigDecimal", true);
            }
            // �����Ƿ�����
            if (EXTRA.equals(column.getExtra())) {
                genMap.put("auto", true);
            }
            // ���������ֵ�
            if (StringUtils.isNotBlank(column.getDictName())) {
                genMap.put("hasDict", true);
                if(!dicts.contains(column.getDictName()))
                    dicts.add(column.getDictName());
            }

            // �洢�ֶ�����
            listMap.put("columnType", colType);
            // �洢��ԭʼ������
            listMap.put("columnName", column.getColumnName());
            // ��Ϊ��
            listMap.put("istNotNull", column.getNotNull());
            // �ֶ��б���ʾ
            listMap.put("columnShow", column.getListShow());
            // ����ʾ
            listMap.put("formShow", column.getFormShow());
            // ���������
            listMap.put("formType", StringUtils.isNotBlank(column.getFormType()) ? column.getFormType() : "Input");
            // Сд��ͷ���ֶ�����
            listMap.put("changeColumnName", changeColumnName);
            //��д��ͷ���ֶ�����
            listMap.put("capitalColumnName", capitalColumnName);
            // �ֵ�����
            listMap.put("dictName", column.getDictName());
            // ����ע��
            listMap.put("dateAnnotation", column.getDateAnnotation());
            if (StringUtils.isNotBlank(column.getDateAnnotation())) {
                genMap.put("hasDateAnnotation", true);
            }
            // ��ӷǿ��ֶ���Ϣ
            if (column.getNotNull()) {
                isNotNullColumns.add(listMap);
            }
            // �ж��Ƿ��в�ѯ��������Ѳ�ѯ���ֶ�set��columnQuery
            if (!StringUtils.isBlank(column.getQueryType())) {
                // ��ѯ����
                listMap.put("queryType", column.getQueryType());
                // �Ƿ���ڲ�ѯ
                genMap.put("hasQuery", true);
                if (TIMESTAMP.equals(colType)) {
                    // ��ѯ�д洢 Timestamp ����
                    genMap.put("queryHasTimestamp", true);
                }
                if (BIGDECIMAL.equals(colType)) {
                    // ��ѯ�д洢 BigDecimal ����
                    genMap.put("queryHasBigDecimal", true);
                }
                if ("between".equalsIgnoreCase(column.getQueryType())) {
                    betweens.add(listMap);
                } else {
                    // ��ӵ���ѯ�б���
                    queryColumns.add(listMap);
                }
            }
            // ��ӵ��ֶ��б���
            columns.add(listMap);
        }
        // �����ֶ��б�
        genMap.put("columns", columns);
        // �����ѯ�б�
        genMap.put("queryColumns", queryColumns);
        // �����ֶ��б�
        genMap.put("dicts", dicts);
        // �����ѯ�б�
        genMap.put("betweens", betweens);
        // ����ǿ��ֶ���Ϣ
        genMap.put("isNotNullColumns", isNotNullColumns);
        return genMap;
    }

    /**
     * �������ļ�·���Լ�����
     */
    private static String getAdminFilePath(String templateName, GenConfig genConfig, String className, String rootPath) {
        String projectPath = rootPath + File.separator + genConfig.getModuleName();
        String packagePath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (!ObjectUtils.isEmpty(genConfig.getPack())) {
            packagePath += genConfig.getPack().replace(".", File.separator) + File.separator;
        }

        if ("Entity".equals(templateName)) {
            return packagePath + "domain" + File.separator + className + ".java";
        }

        if ("Controller".equals(templateName)) {
            return packagePath + "rest" + File.separator + className + "Controller.java";
        }

        if ("Service".equals(templateName)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if ("ServiceImpl".equals(templateName)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if ("Dto".equals(templateName)) {
            return packagePath + "service" + File.separator + "dto" + File.separator + className + "Dto.java";
        }

        if ("QueryCriteria".equals(templateName)) {
            return packagePath + "service" + File.separator + "dto" + File.separator + className + "QueryCriteria.java";
        }

        if ("Mapper".equals(templateName)) {
            return packagePath + "service" + File.separator + "mapstruct" + File.separator + className + "Mapper.java";
        }

        if ("Repository".equals(templateName)) {
            return packagePath + "repository" + File.separator + className + "Repository.java";
        }

        return null;
    }

    /**
     * ����ǰ���ļ�·���Լ�����
     */
    private static String getFrontFilePath(String templateName, String apiPath, String path, String apiName) {

        if ("api".equals(templateName)) {
            return apiPath + File.separator + apiName + ".js";
        }

        if ("index".equals(templateName)) {
            return path + File.separator + "index.vue";
        }

        return null;
    }

    private static void genFile(File file, Template template, Map<String, Object> map) throws IOException {
        // ����Ŀ���ļ�
        Writer writer = null;
        try {
            FileUtil.touch(file);
            writer = new FileWriter(file);
            template.render(map, writer);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert writer != null;
            writer.close();
        }
    }
}

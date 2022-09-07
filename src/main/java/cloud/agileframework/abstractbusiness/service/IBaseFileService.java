package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.annotation.Excel;
import cloud.agileframework.abstractbusiness.annotation.ExcelDeserialize;
import cloud.agileframework.abstractbusiness.annotation.ExcelSerialize;
import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.util.clazz.ClassUtil;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.file.FileUtil;
import cloud.agileframework.common.util.file.ResponseFile;
import cloud.agileframework.common.util.file.poi.CellInfo;
import cloud.agileframework.common.util.file.poi.ExcelFile;
import cloud.agileframework.common.util.file.poi.POIUtil;
import cloud.agileframework.common.util.file.poi.SheetData;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.dictionary.util.ConvertDicAnnotation;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.spring.util.BeanUtil;
import cloud.agileframework.spring.util.MultipartFileUtil;
import cloud.agileframework.spring.util.POIUtilOfMultipartFile;
import cloud.agileframework.validate.ValidateMsg;
import cloud.agileframework.validate.ValidateUtil;
import cloud.agileframework.validate.group.Insert;
import cloud.agileframework.validate.group.Query;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 佟盟
 * 日期 2021-02-20 10:53
 * 描述 基础查询控制器
 * @version 1.0
 * @since 1.0
 */
public interface IBaseFileService<E extends IBaseEntity, I extends BaseInParamVo, O extends IBaseOutParamVo> extends IBaseQueryService<E, I, O> {

    /**
     * 列表查询
     *
     * @return 列表
     */
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.upload:/upload}", "/import"}, method = RequestMethod.POST)
    default RETURN upload(@AgileInParam("file") MultipartFile file) throws Exception {
        return upload(file, false);
    }

    default RETURN upload(MultipartFile file, boolean exportAllIfError) throws Exception {
        Set<ClassUtil.Target<Excel>> annotations = ClassUtil.getAllFieldAnnotation(getInVoClass(), Excel.class);
        List<CellInfo> cellInfos = annotations.stream()
                .map(IBaseFileService::getCellInfo)
                .collect(Collectors.toList());

        List<I> data = Lists.newArrayList();
        TypeReference<I> typeReference = new TypeReference<>(getInVoClass());

        //清空表
        Workbook workbook = POIUtilOfMultipartFile.readFile(file);
        for (Sheet sheet : workbook) {
            List<CellInfo> columnInfo = POIUtil.readColumnInfo(cellInfos, sheet);

            int rowTotal = sheet.getLastRowNum() - 1;
            Integer max = BeanUtil.getApplicationContext().getEnvironment().getProperty("agile.base-service.importMaxNum", Integer.class, 500);
            if (rowTotal > max) {
                throw new AgileArgumentException("最大只允许导入" + max + "条数据");
            }

            int maxRowNum = sheet.getLastRowNum();
            int rowNum = 1;
            while (rowNum <= maxRowNum) {
                Row row = sheet.getRow(rowNum++);

                I rowData = POIUtil.readRow(typeReference, columnInfo, row, workbook);
                ConvertDicAnnotation.cover(rowData);
                data.add(rowData);
            }
        }

        List<ProxyData<I>> success = Lists.newArrayList();
        List<ProxyData<I>> error = Lists.newArrayList();
        List<ProxyData<I>> allData = data.stream().map(ProxyData::new).collect(Collectors.toList());
        for (ProxyData<I> in : allData) {
            List<ValidateMsg> validateMsg = validateRowData(data, in.in);
            in.setMsg(validateMsg);
            if (validateMsg.isEmpty()) {
                success.add(in);
            } else {
                error.add(in);
            }
        }

        if (error.isEmpty()) {
            handleSuccessData(success.stream().map(a -> a.in).collect(Collectors.toList()));
            return RETURN.SUCCESS;
        } else if (exportAllIfError) {
            handleSuccessData(success.stream().map(a -> a.in).collect(Collectors.toList()));
            handleErrorData(error);
            exportExcel(allData);
        } else {
            handleErrorData(error);
            exportExcel(error);
        }

        return RETURN.PARAMETER_ERROR;

    }

    @Data
    class ProxyData<I> {
        private I in;
        private List<ValidateMsg> msg;

        public ProxyData(I in) {
            this.in = in;
        }
    }

    default List<ValidateMsg> validateRowData(List<I> data, I rowData) throws Exception {
        return ValidateUtil.validate(rowData, Insert.class);
    }

    /**
     * 处理读取的excel数据
     *
     * @param data 数据
     * @throws Exception 异常
     */
    default void handleSuccessData(List<I> data) throws Exception {
        final TypeReference<List<E>> toClass = new TypeReference<List<E>>() {
        };
        ParameterizedType parameterizedType = (ParameterizedType) toClass.getType();
        parameterizedType = TypeUtils.parameterizeWithOwner(parameterizedType.getOwnerType(),
                (Class<?>) parameterizedType.getRawType(),
                getEntityClass());
        toClass.replace(parameterizedType);

        Objects.requireNonNull(BeanUtil.getBean(getClass())).saveDataWithNewTransaction(ObjectUtil.to(data, toClass));
    }

    default void handleErrorData(List<ProxyData<I>> proxyData) throws Exception {

    }

    default void exportExcel(List<ProxyData<I>> proxyData) throws Exception {
        Object templateFile = template();
        Workbook workbook;
        if (templateFile instanceof ResponseFile) {
            workbook = POIUtil.readFile(((ResponseFile) templateFile).getFileName(), ((ResponseFile) templateFile).getInputStream());
        } else if (templateFile instanceof ExcelFile) {
            workbook = ((ExcelFile) templateFile).getWorkbook();
        } else {
            throw new AgileArgumentException("未找到合适的错误数据存储文件格式");
        }
        Set<ClassUtil.Target<Excel>> annotations = ClassUtil.getAllFieldAnnotation(getInVoClass(), Excel.class);
        List<CellInfo> cellInfos = annotations.stream()
                .map(IBaseFileService::getCellInfo)
                .collect(Collectors.toList());


        for (Sheet sheet : workbook) {
            List<CellInfo> columnInfo = POIUtil.readColumnInfo(cellInfos, sheet);
            if (columnInfo.isEmpty()) {
                continue;
            }
            for (int rowNum = 0; rowNum < proxyData.size(); rowNum++) {
                ProxyData<I> proxyDataRow = proxyData.get(rowNum);
                Map<String, ValidateMsg> map = proxyDataRow.getMsg().stream().collect(Collectors.toMap(ValidateMsg::getItem, row -> row));
                Row row = sheet.createRow(rowNum + 1);
                for (int colNum = 0; colNum < columnInfo.size(); colNum++) {
                    String columnKey = columnInfo.get(colNum).getKey();
                    ValidateMsg error = map.get(columnKey);
                    Cell cell = row.createCell(colNum);
                    if (error == null) {
                        Object value = ObjectUtil.getFieldValue(proxyDataRow.in, columnKey);
                        String text = value == null ? "" : value.toString();
                        POIUtil.addCellValue(workbook, cell, text, workbook.createFont());
                        continue;
                    }

                    Font font = workbook.createFont();
                    font.setColor(IndexedColors.RED.getIndex());
                    Object itemValue = error.getItemValue();
                    CellStyle style = workbook.createCellStyle();
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    style.setFillForegroundColor(IndexedColors.WHITE.getIndex());

                    style.setBorderBottom(BorderStyle.THICK);
                    style.setBottomBorderColor(IndexedColors.RED1.getIndex());
                    style.setBorderLeft(BorderStyle.THICK);
                    style.setLeftBorderColor(IndexedColors.RED1.getIndex());
                    style.setBorderRight(BorderStyle.THICK);
                    style.setRightBorderColor(IndexedColors.RED1.getIndex());
                    style.setBorderTop(BorderStyle.THICK);
                    style.setTopBorderColor(IndexedColors.RED1.getIndex());
                    cell.setCellStyle(style);
                    POIUtil.addCellValue(workbook, cell, itemValue == null ? "" : itemValue.toString(), font);
                    POIUtil.addComment(workbook, cell, error.getMessage());
                }
            }
        }

        AgileReturn.add(new ExcelFile("导入失败数据", workbook));
    }

    /**
     * 列表查询
     *
     * @return 列表
     */
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.download:/download}", "/export"}, method = {RequestMethod.POST, RequestMethod.GET})
    default ExcelFile download() throws Exception {
        I inParam = AgileParam.getInParam(getInVoClass());
        validate(inParam, Query.class);
        String sql = parseOrder(inParam, listSql());
        List<?> list;
        if (sql != null) {
            list = list(getOutVoClass(), inParam, sql);
        } else {
            list = list(getEntityClass(), inParam);
        }

        List<O> result = toOutVo(list);

        Set<ClassUtil.Target<Excel>> remarks = ClassUtil.getAllFieldAnnotation(getOutVoClass(), Excel.class);
        List<CellInfo> cellInfos = remarks.stream()
                .map(IBaseFileService::getCellInfo)
                .collect(Collectors.toList());

        Workbook workbook = POIUtil.creatExcel(version(), SheetData.builder().setCells(cellInfos).setData((JSONArray) JSON.toJSON(result)).build());

        return new ExcelFile(fileName(), workbook);
    }

    /**
     * 列表查询
     *
     * @return 列表
     */
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.template:/template}"}, method = {RequestMethod.GET, RequestMethod.POST})
    default Object template() throws Exception {
        try {
            String filePath = templatePath();
            File file;
            if (filePath.startsWith("classpath:")) {
                String substring = filePath.substring(10);
                InputStream stream = getClass().getResourceAsStream(FileUtil.parseClassPath(substring));
                if (stream == null) {
                    throw new NoSuchFileException(filePath);
                }
                file = new File(FileUtil.parseFilePath(MultipartFileUtil.getTempPath() + substring));
                FileUtils.copyInputStreamToFile(stream, file);
            } else {
                filePath = URLDecoder.decode(filePath, Charset.defaultCharset().name());
                file = new File(filePath);
            }

            if (!file.exists()) {
                throw new NoSuchFileException(filePath);
            }
            return new ResponseFile(file.getName(), file);
        } catch (NoSuchFileException e) {
            Set<ClassUtil.Target<Excel>> remarks = ClassUtil.getAllFieldAnnotation(getInVoClass(), Excel.class);
            List<CellInfo> cellInfos = remarks.stream()
                    .map(IBaseFileService::getCellInfo)
                    .collect(Collectors.toList());

            Workbook workbook = POIUtil.creatExcel(version(), SheetData.builder().setCells(cellInfos).build());

            return new ExcelFile(fileName(), workbook);
        }
    }

    /**
     * 构建excel列信息
     *
     * @param target 标注Excel注解的对象
     * @return 列信息
     */
    static CellInfo getCellInfo(ClassUtil.Target<Excel> target) {
        CellInfo.Builder builder = CellInfo.builder()
                .key(target.getMember().getName())
                .name(target.getAnnotation().name().trim())
                .sort(target.getAnnotation().sort())
                .type(target.getAnnotation().type());
        try {
            Class<? extends ExcelSerialize> serializeClass = target.getAnnotation().serialize();
            if (ExcelSerialize.class != serializeClass) {
                ExcelSerialize obj = ClassUtil.newInstance(serializeClass);
                Method method = serializeClass.getMethod("to", Object.class);
                builder.serialize(a -> {
                    try {
                        return method.invoke(obj, a);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            } else if (getType(target) == Date.class && !StringUtils.isBlank(target.getAnnotation().format())) {
                builder.serialize(a -> {
                    try {
                        SimpleDateFormat simple = new SimpleDateFormat(target.getAnnotation().format());
                        return simple.format((Date) a);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            Class<? extends ExcelDeserialize> deserializeClass = target.getAnnotation().deserialize();
            if (ExcelDeserialize.class != deserializeClass) {
                ExcelDeserialize obj = ClassUtil.newInstance(deserializeClass);
                Method method = deserializeClass.getMethod("to", Object.class);
                builder.deserialize(a -> {
                    try {
                        return method.invoke(obj, a);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            } else if (getType(target) == Date.class && !StringUtils.isBlank(target.getAnnotation().format())) {
                builder.deserialize(a -> {
                    try {
                        if (a instanceof Date || a == null) {
                            return a;
                        }
                        SimpleDateFormat simple = new SimpleDateFormat(target.getAnnotation().format());
                        return simple.parse(a.toString());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    static Class<?> getType(ClassUtil.Target<Excel> target) {
        Member member = target.getMember();
        if (member instanceof Field) {
            return ((Field) member).getType();
        } else if (member instanceof Method) {
            return ((Method) member).getReturnType();
        }
        return Object.class;
    }

    /**
     * 下载的文件名字
     */
    default String fileName() {
        return "下载文件";
    }

    /**
     * 模板文件的路径
     */
    default String templatePath() throws NoSuchFileException {
        throw new NoSuchFileException("未配置模板文件目录");
    }

    default POIUtil.VERSION version() {
        return POIUtil.VERSION.V2007;
    }

}

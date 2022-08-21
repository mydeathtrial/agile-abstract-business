package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.ImportFileFormatException;
import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.annotation.Remark;
import cloud.agileframework.common.util.clazz.ClassUtil;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.file.FileUtil;
import cloud.agileframework.common.util.file.ResponseFile;
import cloud.agileframework.common.util.file.poi.CellInfo;
import cloud.agileframework.common.util.file.poi.ExcelFile;
import cloud.agileframework.common.util.file.poi.POIUtil;
import cloud.agileframework.common.util.file.poi.SheetData;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.dictionary.util.DictionaryUtil;
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
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
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
import java.lang.reflect.ParameterizedType;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
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
        Set<ClassUtil.Target<Remark>> remarks = ClassUtil.getAllFieldAnnotation(getInVoClass(), Remark.class);
        List<CellInfo> cellInfos = remarks.stream()
                .filter(r -> r.getAnnotation().excelHead())
                .map(r -> CellInfo.builder()
                        .setKey(r.getMember().getName())
                        .setShowName(r.getAnnotation().value())
                        .build())
                .collect(Collectors.toList());

        List<I> data = Lists.newArrayList();
        List<I> errorData = Lists.newArrayList();
        TypeReference<I> typeReference = new TypeReference<>(getInVoClass());

        //清空表
        Workbook workbook = POIUtilOfMultipartFile.readFile(file);
        for (Sheet sheet : workbook) {
            List<String> columnInfo = POIUtil.readColumnInfo(cellInfos, sheet);
            if(columnInfo.stream().anyMatch(Objects::isNull)){
                throw new ImportFileFormatException();
            }

            int rowTotal = sheet.getLastRowNum() - 1;
            Integer max = BeanUtil.getApplicationContext().getEnvironment().getProperty("agile.base-service.importMaxNum", Integer.class, 500);
            if (rowTotal > max) {
                throw new AgileArgumentException("最大只允许导入" + max + "条数据");
            }

            int maxRowNum = sheet.getLastRowNum();
            int rowNum = 0;
            while (rowNum <= maxRowNum) {

                if (rowNum == 0) {
                    rowNum++;
                    continue;
                }
                Row row = sheet.getRow(rowNum);

                I rowData = POIUtil.readRow(typeReference, columnInfo, row);
                if(rowData==null){
                    rowNum++;
                    continue;
                }
                DictionaryUtil.cover(rowData);

                List<ValidateMsg> msg = validateRowData(rowData);
                if (msg.isEmpty()) {
                    data.add(rowData);
                    if (!exportAllIfError) {
                        sheet.shiftRows(rowNum, maxRowNum, -1);
                        maxRowNum--;
                    } else {
                        rowNum++;
                    }
                } else {
                    errorData.add(rowData);
                    Map<String, ValidateMsg> map = msg.stream().collect(Collectors.toMap(ValidateMsg::getItem, a -> a));
                    for (int i = 0; i < columnInfo.size(); i++) {
                        String columnKey = columnInfo.get(i);
                        ValidateMsg error = map.get(columnKey);
                        if (error == null) {
                            continue;
                        }
                        Cell cell = row.getCell(i);
                        cell = cell == null ? row.createCell(i) : cell;

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
                    rowNum++;
                }

            }
        }
        if (errorData.isEmpty() || !exportAllIfError) {
            handleSuccessData(data);
        }
        
        if (!errorData.isEmpty()) {
            handleErrorData(errorData, workbook);
            return RETURN.PARAMETER_ERROR;
        }
        return RETURN.SUCCESS;
    }

    default List<ValidateMsg> validateRowData(I rowData) throws Exception {
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

    default void handleErrorData(List<I> errorData, Workbook workbook) throws Exception {
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
        String sql = IBaseQueryService.parseOrder(inParam, listSql());
        List<?> list;
        if (sql != null) {
            list = list(getOutVoClass(), inParam, sql);
        } else {
            list = list(getEntityClass(), inParam);
        }

        List<O> result = toOutVo(list);

        Set<ClassUtil.Target<Remark>> remarks = ClassUtil.getAllFieldAnnotation(getOutVoClass(), Remark.class);
        List<CellInfo> cellInfos = remarks.stream()
                .map(r -> CellInfo.builder()
                        .setKey(r.getMember().getName())
                        .setShowName(r.getAnnotation().value())
                        .setSort(r.getAnnotation().sort())
                        .build())
                .collect(Collectors.toList());

        Workbook workbook = POIUtil.creatExcel(version(), SheetData.builder().setCells(cellInfos).setData(new ArrayList<>(result)).build());

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
            Set<ClassUtil.Target<Remark>> remarks = ClassUtil.getAllFieldAnnotation(getInVoClass(), Remark.class);
            List<CellInfo> cellInfos = remarks.stream()
                    .map(r -> CellInfo.builder()
                            .setKey(r.getMember().getName())
                            .setShowName(r.getAnnotation().value())
                            .setSort(r.getAnnotation().sort())
                            .build())
                    .collect(Collectors.toList());

            Workbook workbook = POIUtil.creatExcel(version(), SheetData.builder().setCells(cellInfos).build());

            return new ExcelFile(fileName(), workbook);
        }
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

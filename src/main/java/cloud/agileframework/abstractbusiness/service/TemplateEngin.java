package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.template.curd.ButtonType;
import cloud.agileframework.abstractbusiness.pojo.template.curd.Column;
import cloud.agileframework.abstractbusiness.pojo.template.curd.Table;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.FormValidate;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.data.DicFormElementData;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.data.FormElementData;
import cloud.agileframework.abstractbusiness.pojo.template.view.form.data.FormElementDataFactory;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.common.constant.Constant;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.collection.TreeBase;
import cloud.agileframework.common.util.collection.TreeUtil;
import cloud.agileframework.common.util.file.poi.CellInfo;
import cloud.agileframework.common.util.file.poi.ExcelFile;
import cloud.agileframework.common.util.file.poi.POIUtil;
import cloud.agileframework.common.util.file.poi.SheetData;
import cloud.agileframework.common.util.http.NotFoundRequestMethodException;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.dictionary.annotation.DirectionType;
import cloud.agileframework.dictionary.util.ConvertConf;
import cloud.agileframework.dictionary.util.ConvertDicMap;
import cloud.agileframework.jpa.dao.Dao;
import cloud.agileframework.mvc.annotation.AgileService;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.mvc.param.AgileReturn;
import cloud.agileframework.spring.util.BeanUtil;
import cloud.agileframework.spring.util.POIUtilOfMultipartFile;
import cloud.agileframework.spring.util.RequestWrapper;
import cloud.agileframework.validate.ValidateConfig;
import cloud.agileframework.validate.ValidateMsg;
import cloud.agileframework.validate.ValidateUtil;
import cloud.agileframework.validate.group.Insert;
import cloud.agileframework.validate.group.Update;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@AgileService
public class TemplateEngin {
    @Autowired(required = false)
    private TemplateService templateService;

    @Value("${agile.base-service.page:/page}")
    private String page;
    @Value("${agile.base-service.deleteByIds:}")
    private String deleteByIds;
    @Value("${agile.base-service.save:}")
    private String save;
    @Value("${agile.base-service.update:}")
    private String update;
    @Value("${agile.base-service.query:/list}")
    private String query;
    @Value("${agile.base-service.queryById:/{id}}")
    private String queryById;
    @Value("${agile.base-service.deleteById:/{id}}")
    private String deleteById;
    @Value("${agile.base-service.tree:/tree}")
    private String tree;
    @Value("${agile.base-service.template:/template}")
    private String template;
    @Value("${agile.base-service.upload:/upload}")
    private String upload;
    @Value("${agile.base-service.download:/download}")
    private String download;


    @Autowired
    private Dao dao;
 

    @Mapping(value = {"/api/v${version:1}/*", "/api/v${version:1}/**"})
    public Object router(HttpServletRequest request) throws NotFoundRequestMethodException, IOException, AgileArgumentException {
        String path = request.getRequestURI();
        Table table = templateService.getTableByUrl(path);
        ButtonType type = byUrl(request);

        return parse(table, type);
    }

    public Object parse(String id, ButtonType type) throws IOException, AgileArgumentException {
        return parse(templateService.getTable(id), type);
    }

    public Object parse(Table table, ButtonType type) throws AgileArgumentException, IOException {
        Object result;
        Map<String, Object> inParam = AgileParam.getInParam();
        switch (type) {
            case ADD:
                validateAndThrow(table, Insert.class);
                inParam.putAll(templateService.generatorId(table.getId()));
                dao.updateBySQL(table.getMeta().getInsert(), inParam);
                break;
            case DELETE:
                dao.updateBySQL(table.getMeta().getDelete(), inParam);
                break;
            case ROW_DELETE:
                dao.updateBySQL(table.getMeta().getDelete(), inParam);
                break;
            case ROW_UPDATE:
                validateAndThrow(table, Update.class);
                dao.updateBySQL(table.getMeta().getUpdate(), inParam);
                break;
            case PAGE:
                BaseInParamVo baseInParamVo = AgileParam.getInParam(BaseInParamVo.class);
                String sql = baseInParamVo.parseOrder(table.getMeta().getPage());
                result = dao.pageBySQL(sql, baseInParamVo.getPageNum(), baseInParamVo.getPageSize(), inParam);

                AgileReturn.add(Constant.ResponseAbout.RESULT, result);
                break;
            case ROW_DETAIL:
                result = dao.findOne(table.getMeta().getDetail(), inParam);

                AgileReturn.add(Constant.ResponseAbout.RESULT, result);
                break;
            case TREE:
                List<TreeBase> list = dao.findBySQL(table.getMeta().getTree(), TreeBase.class, inParam);
                result = TreeUtil.createTree(list, null);
                AgileReturn.add(Constant.ResponseAbout.RESULT, result);
                break;
            case TEMPLATE:
                return template(table, table.getName() + "导入模板");
            case DOWNLOAD:
                BaseInParamVo baseInParamVo2 = AgileParam.getInParam(BaseInParamVo.class);
                String sql2 = baseInParamVo2.parseOrder(table.getMeta().getPage());
                List<Map<String, Object>> list2 = dao.findBySQL(sql2, inParam);

                return IBaseFileService.createExcel(list2, cellInfos(table), table.getName() + "导出数据", POIUtil.VERSION.V2007);
            case UPLOAD:
                MultipartFile file = AgileParam.getInParamOfFile("file");
                List<Map<String, Object>> data = Lists.newArrayList();
                Workbook workbook = POIUtilOfMultipartFile.readFile(file);
                List<CellInfo> cellInfos3 = table.getColumn().values().stream()
                        .map(Column::to)
                        .collect(Collectors.toList());

                for (Sheet sheet : workbook) {
                    POIUtil.readColumnInfo(cellInfos3, sheet);

                    int rowTotal = sheet.getLastRowNum() - 1;
                    Integer max = BeanUtil.getApplicationContext().getEnvironment().getProperty("agile.base-service.importMaxNum", Integer.class, 500);
                    if (rowTotal > max) {
                        throw new AgileArgumentException("最大只允许导入" + max + "条数据");
                    }

                    int maxRowNum = sheet.getLastRowNum();
                    int rowNum = 1;
                    while (rowNum <= maxRowNum) {
                        Row row = sheet.getRow(rowNum++);

                        Map<String, Object> rowData = POIUtil.readRow(new TypeReference<Map<String, Object>>() {
                        }, cellInfos3, row, workbook);
                        ConvertDicMap.coverMapDictionary(rowData, dicConfigs(table));
                        data.add(rowData);
                    }
                }

                List<IBaseFileService.ProxyData<Map<String, Object>>> success = Lists.newArrayList();
                List<IBaseFileService.ProxyData<Map<String, Object>>> error = Lists.newArrayList();
                List<IBaseFileService.ProxyData<Map<String, Object>>> allData = data.stream().map(IBaseFileService.ProxyData::new).collect(Collectors.toList());
                for (IBaseFileService.ProxyData<Map<String, Object>> in : allData) {
                    List<ValidateMsg> validateMsg = ValidateUtil.handleValidateData(data, validateConfigs(table));
                    in.setMsg(validateMsg);
                    if (validateMsg.isEmpty()) {
                        success.add(in);
                    } else {
                        error.add(in);
                    }
                }

                if (error.isEmpty()) {
                    dao.save(success.stream().map(IBaseFileService.ProxyData::getIn).collect(Collectors.toList()));
                } else {
                    return exportError(table, cellInfos(table), error);

                }
                break;
            default:
        }
        return RETURN.SUCCESS;
    }

    private static final Map<String, List<CellInfo>> excelCache = Maps.newHashMap();
    private static final Map<String, List<ValidateConfig>> validateConfigCache = Maps.newHashMap();
    private static final Map<String, List<ConvertConf>> dicConfigCache = Maps.newHashMap();

    private static List<CellInfo> cellInfos(Table table) {
        String id = table.getId();
        return excelCache.computeIfAbsent(id, key -> {
            List<CellInfo> value = table.getColumn().values().stream()
                    .map(Column::to)
                    .collect(Collectors.toList());
            excelCache.put(id, value);
            return value;
        });
    }

    private static List<ValidateConfig> validateConfigs(Table table) {
        String id = table.getId();
        return validateConfigCache.computeIfAbsent(id, key -> {
            List<ValidateConfig> value = table.getColumn().values()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(a -> {
                        FormValidate validate = a.getValidate();
                        if (validate == null) {
                            return null;
                        }
                        ValidateConfig c = validate.to();
                        c.setValue(a.getCode());
                        return c;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            validateConfigCache.put(id, value);
            return value;
        });
    }

    private static List<ConvertConf> dicConfigs(Table table) {
        String id = table.getId();
        return dicConfigCache.computeIfAbsent(id, key -> {
            List<ConvertConf> value = table.getColumn()
                    .values()
                    .stream()
                    .map(a -> {
                        FormElementData data = a.getForm().getData();
                        if (data instanceof DicFormElementData) {
                            return ConvertConf.builder()
                                    .ref(a.getCode())
                                    .toRef(a.getCode() + "_convert_name")
                                    .directionType(DirectionType.ID_TO_NAME)
                                    .dataSource(((DicFormElementData) data).getDataSource())
                                    .build();
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            dicConfigCache.put(id, value);
            return value;
        });
    }

    private static ExcelFile template(Table table, String filename) throws IOException {
        List<CellInfo> cellInfos = table.getColumn().values().stream()
                .map(Column::to)
                .collect(Collectors.toList());

        Workbook workbook = POIUtil.creatExcel(POIUtil.VERSION.V2007, SheetData.builder().setCells(cellInfos).setData(Lists.newArrayList()).build());
        return new ExcelFile(filename, workbook);
    }

    private static ExcelFile exportError(Table table, List<CellInfo> cellInfos, List<IBaseFileService.ProxyData<Map<String, Object>>> data) throws IOException {
        ExcelFile excelFile = template(table, table.getName() + "导入错误数据");
        Workbook workbook = excelFile.getWorkbook();

        for (Sheet sheet : workbook) {
            POIUtil.readColumnInfo(cellInfos, sheet);

            for (int rowNum = 0; rowNum < data.size(); rowNum++) {
                IBaseFileService.ProxyData<?> proxyDataRow = data.get(rowNum);
                Map<String, ValidateMsg> map = proxyDataRow.getMsg().stream().collect(Collectors.toMap(ValidateMsg::getItem, row -> row));
                Row row = sheet.createRow(rowNum + 1);

                for (CellInfo cellInfo : cellInfos) {
                    if (cellInfo.getSort() < 0) {
                        continue;
                    }
                    String columnKey = cellInfo.getKey();
                    ValidateMsg error = map.get(columnKey);
                    Cell cell = row.createCell(cellInfo.getSort());
                    if (error == null) {
                        Object value = ObjectUtil.getFieldValue(proxyDataRow.getIn(), columnKey);
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
        return excelFile;
    }

    @Mapping(value = "/api/v${version:1}/formElementData")
    public Object formElementData() throws Exception {
        FormElementData formElementData = FormElementDataFactory.create(AgileParam.getInParam(JSONObject.class));
        if (formElementData == null) {
            return null;
        }
        return formElementData.data();
    }

    private void validateAndThrow(Table table, Class<?> group) throws AgileArgumentException {
        List<ValidateMsg> result = validate(validateConfigs(table), AgileParam.getInParam(), group);
        if (result.isEmpty()) {
            return;
        }
        throw new AgileArgumentException(result);
    }

    private List<ValidateMsg> validate(List<ValidateConfig> validateConfigs, Map<String, Object> params, Class<?> group) {
        return ValidateUtil.handleValidateData(params, validateConfigs.stream().filter(a -> {
            Class<?>[] columnGroups = a.getValidateGroups();
            return columnGroups == null || ArrayUtils.contains(columnGroups, group);
        }).collect(Collectors.toList()));
    }

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private ButtonType byUrl(HttpServletRequest request) throws NotFoundRequestMethodException {
        String path = request.getRequestURI();
        Table table = templateService.getTableByUrl(path);
        String urlSuffix = path.substring(table.getUrl().length());

        String mapping = null;
        ButtonType result = null;
        switch (RequestMethod.valueOf(request.getMethod().toUpperCase(Locale.ROOT))) {
            case DELETE:
                if (pathMatcher.match(deleteById, urlSuffix)) {
                    result = ButtonType.ROW_DELETE;
                    mapping = deleteById;
                } else if (pathMatcher.match(deleteByIds, urlSuffix)) {
                    result = ButtonType.DELETE;
                    mapping = deleteByIds;
                }
                break;
            case POST:
                if (pathMatcher.match(save, urlSuffix)) {
                    result = ButtonType.ADD;
                    mapping = save;
                } else if (pathMatcher.match(query, urlSuffix)) {
                    result = ButtonType.PAGE;
                    mapping = query;
                } else if (pathMatcher.match(page, urlSuffix)) {
                    result = ButtonType.PAGE;
                    mapping = page;
                } else if (pathMatcher.match(upload, urlSuffix)) {
                    result = ButtonType.UPLOAD;
                    mapping = upload;
                } else if (pathMatcher.match(download, urlSuffix)) {
                    result = ButtonType.DOWNLOAD;
                    mapping = download;
                } else if (pathMatcher.match(template, urlSuffix)) {
                    result = ButtonType.TEMPLATE;
                    mapping = template;
                } else if (pathMatcher.match(tree, urlSuffix)) {
                    result = ButtonType.TREE;
                    mapping = tree;
                }
                break;
            case PUT:
                if (pathMatcher.match(update, urlSuffix)) {
                    result = ButtonType.ROW_UPDATE;
                    mapping = update;
                }
                break;
            case GET:
                if (pathMatcher.match(queryById, urlSuffix)) {
                    result = ButtonType.ROW_DETAIL;
                    mapping = queryById;
                } else if (pathMatcher.match(download, urlSuffix)) {
                    result = ButtonType.DOWNLOAD;
                    mapping = download;
                } else if (pathMatcher.match(template, urlSuffix)) {
                    result = ButtonType.TEMPLATE;
                    mapping = template;
                } else if (pathMatcher.match(tree, urlSuffix)) {
                    result = ButtonType.TREE;
                    mapping = tree;
                }
                break;
            default:
        }

        if (result != null && mapping != null) {
            RequestWrapper requestWrapper = WebUtils.getNativeRequest(request, RequestWrapper.class);
            requestWrapper.extendInParam(Maps.newHashMap(pathMatcher.extractUriTemplateVariables(mapping, urlSuffix)));
            return result;
        }
        throw new NotFoundRequestMethodException();
    }
}

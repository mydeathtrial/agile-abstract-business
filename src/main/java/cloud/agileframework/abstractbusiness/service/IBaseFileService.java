package cloud.agileframework.abstractbusiness.service;

import cloud.agileframework.abstractbusiness.pojo.entity.IBaseEntity;
import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.annotation.Remark;
import cloud.agileframework.common.util.clazz.ClassUtil;
import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.file.ResponseFile;
import cloud.agileframework.common.util.file.poi.CellInfo;
import cloud.agileframework.common.util.file.poi.ExcelFile;
import cloud.agileframework.common.util.file.poi.POIUtil;
import cloud.agileframework.common.util.file.poi.SheetData;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.mvc.annotation.AgileInParam;
import cloud.agileframework.mvc.annotation.Mapping;
import cloud.agileframework.mvc.base.RETURN;
import cloud.agileframework.mvc.param.AgileParam;
import cloud.agileframework.spring.util.POIUtilOfMultipartFile;
import cloud.agileframework.validate.group.Insert;
import cloud.agileframework.validate.group.Query;
import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
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
    @Mapping(value = {"${agile.base-service.upload:/upload}"}, method = RequestMethod.POST)
    default RETURN upload(@AgileInParam("file") MultipartFile file) {
        Set<ClassUtil.Target<Remark>> remarks = ClassUtil.getAllFieldAnnotation(getInVoClass(), Remark.class);
        List<CellInfo> cellInfos = remarks.stream()
                .map(r -> CellInfo.builder()
                        .setKey(r.getMember().getName())
                        .setShowName(r.getAnnotation().value())
                        .build())
                .collect(Collectors.toList());

        List<I> data = POIUtilOfMultipartFile.readExcel(file, getInVoClass(), cellInfos);

        validate(data, Insert.class);

        final TypeReference<List<E>> toClass = new TypeReference<List<E>>() {
        };
        ParameterizedType parameterizedType = (ParameterizedType) toClass.getType();
        parameterizedType = TypeUtils.parameterizeWithOwner(parameterizedType.getOwnerType(),
                (Class<?>) parameterizedType.getRawType(),
                getEntityClass());
        toClass.replace(parameterizedType);

        saveData(ObjectUtil.to(data, toClass));
        return RETURN.SUCCESS;
    }

    /**
     * 列表查询
     *
     * @return 列表
     */
    @SneakyThrows
    @Mapping(value = {"${agile.base-service.download:/download}"}, method = {RequestMethod.POST, RequestMethod.GET})
    default ExcelFile download() {
        I inParam = AgileParam.getInParam(getInVoClass());
        validate(inParam, Query.class);
        String sql = listSql();
        List<?> list;
        if (sql != null) {
            list = list(getOutVoClass(), inParam, sql);
        } else {
            list = list(getEntityClass(), inParam);
        }

        List<O> result = toOutVo(list);

        Set<ClassUtil.Target<Remark>> remarks = ClassUtil.getAllFieldAnnotation(getInVoClass(), Remark.class);
        List<CellInfo> cellInfos = remarks.stream()
                .map(r -> CellInfo.builder()
                        .setKey(r.getMember().getName())
                        .setShowName(r.getAnnotation().value())
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
    default ResponseFile template() {
        String filePath = templatePath();
        if (filePath.startsWith("classpath:")) {
            String substring = filePath.substring(10);
            if (!substring.startsWith("/")) {
                substring = "/" + substring;
            }
            URL classpath = getClass().getResource(substring);
            if (classpath == null) {
                throw new NoSuchFileException(filePath);
            }
            filePath = classpath.getPath();
        }
        filePath = URLDecoder.decode(filePath, Charset.defaultCharset().name());
        File file = new File(filePath);
        if (!file.exists()) {
            throw new NoSuchFileException(filePath);
        }
        return new ResponseFile(file.getName(), file);
    }

    /**
     * 下载的文件名字
     */
    default String fileName(){
        return "下载文件";
    }

    /**
     * 模板文件的路径
     */
    default String templatePath() throws NoSuchFileException {
        throw new NoSuchFileException("未配置模板文件目录");
    }

    default POIUtil.VERSION version() {
        return POIUtil.VERSION.V2003;
    }

}

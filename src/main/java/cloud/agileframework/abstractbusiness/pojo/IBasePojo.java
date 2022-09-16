package cloud.agileframework.abstractbusiness.pojo;

import cloud.agileframework.common.util.clazz.TypeReference;
import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.mvc.exception.AgileArgumentException;
import cloud.agileframework.validate.ValidateMsg;
import cloud.agileframework.validate.ValidateUtil;

import java.io.Serializable;
import java.util.List;

public interface IBasePojo extends Serializable {
    default <T>T to(Class<T> tClass) {
        return ObjectUtil.to(this, new TypeReference<>(tClass));
    }

    /**
     * 参数验证
     * @param groups 场景
     * @throws AgileArgumentException 参数错误
     */
    default void validate(Class<?>... groups) throws AgileArgumentException{

        List<ValidateMsg> list = getValidate(groups);
        if (!list.isEmpty()) {
            throw new AgileArgumentException(list);
        }
    }
    
    default List<ValidateMsg> getValidate(Class<?>... groups){
        return ValidateUtil.validate(this, groups);
    }
}

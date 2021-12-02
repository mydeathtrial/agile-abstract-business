package cloud.agileframework.abstractbusiness.pojo;

import cloud.agileframework.mvc.exception.AbstractCustomException;

public class EntityExistsException extends AbstractCustomException {
    public EntityExistsException(String... params) {
        super(params);
    }
}

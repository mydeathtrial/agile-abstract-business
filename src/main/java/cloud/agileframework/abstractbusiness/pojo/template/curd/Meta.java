package cloud.agileframework.abstractbusiness.pojo.template.curd;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@EqualsAndHashCode
@Data
public class Meta implements Serializable {
    private String insert;
    private String update;
    private String delete;
    private String page;
    private String detail;
    private String tree;
}

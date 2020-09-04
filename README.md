# agile-abstract-business ： 抽象业务扩展
[![spring-data-jpa](https://img.shields.io/badge/Spring--data--jpa-LATEST-green)](https://img.shields.io/badge/Spring--data--jpa-LATEST-green)
[![maven](https://img.shields.io/badge/build-maven-green)](https://img.shields.io/badge/build-maven-green)
## 它有什么作用

* **抽象服务**
通过抽象服务，为实体类提供自动化增删改查、分页、参数判断能力，添加该组建后，仅需ORM实体，不需要额外代码，即可

* **旧Agile版本兼容**
在2.0.0之前的版本中，agile-mvc提供了过时的MainService类作为旧AgileService代理服务层的基类，由于此方式代码
入侵率很高，所以新版agile-mvc中利用@AgileService注解形式完全取代MainService基类。将该类移植至此模块，后续
高版本会删除。

-------
## 快速入门
开始你的第一个项目是非常容易的。

#### 步骤 1: 下载包
您可以从[最新稳定版本]下载包(https://github.com/mydeathtrial/agile-abstract-business/releases).
该包已上传至maven中央仓库，可在pom中直接声明引用

以版本agile-abstract-business-0.2.jar为例。
#### 步骤 2: 添加maven依赖
```xml
<!--声明中央仓库-->
<repositories>
    <repository>
        <id>cent</id>
        <url>https://repo1.maven.org/maven2/</url>
    </repository>
</repositories>
<!--声明依赖-->
<dependency>
    <groupId>cloud.agileframework</groupId>
    <artifactId>agile-abstract-business</artifactId>
    <version>0.2</version>
</dependency>
```
#### 步骤 3: 开箱即用
组件生效条件有两个必要条件
1. 使用agile-jpa组件托管持久层
2. 工程中能够扫描到对应的ORM实体
以自定义ORM实体`MyTableEntity.class`为例
+ 实体，实体中可以声明hibernate-validate验证注解，用于增删改查验证，
```
@Setter
@Builder
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "my_table")
@Remark("[系统管理]定时任务目标任务表")
public class MyTableEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Remark("主键")
    private Long sysBtTaskApiId;
    @Remark("定时任务标志")
    private Long sysTaskId;
    @Remark("目标方法主键")
    private Long sysApiId;
    @Remark("优先级")
    private Integer order;

    @Column(name = "sys_bt_task_api_id", nullable = false, length = 19)
    @Id
    public Long getSysBtTaskApiId() {
        return sysBtTaskApiId;
    }

    @Basic
    @Column(name = "sys_task_id", nullable = false, length = 19)
    public Long getSysTaskId() {
        return sysTaskId;
    }

    @Column(name = "sys_api_id", nullable = false, length = 19)
    @Basic
    public Long getSysApiId() {
        return sysApiId;
    }

    @Column(name = "`order`", length = 3)
    @Basic
    public Integer getOrder() {
        return order;
    }
}

```
API地址模板
相关参数说明：
+ model模糊对应实体名如MyTableEntity、my-table-entity等等
+ id主键，不区分类型，组件自动转换
+ page分页页号，从1计算
+ size分页页容量
+ sorts排序字段，数组格式，`-`开头字段意为倒叙
其中查询和增加修改都接收实体对象信息参数，不限传参形式，参数转换器会根据`model`对应的实体属性与传参进行模糊转换
成功转换后，经过hibernate-validate验证，进入抽象业务操作。后续会详细说明该部分使用技巧。
```
增:POST:/api/{model}
主键删:DELETE:/api/{model}/{id}
主键批量删:DELETE:/api/{model}
改:PUT:/api/{model}
查:GET:/api/{model}
主键查:GET:/api/{model}/{id}
分页:GET:/api/{model}/{page}/{size}
```
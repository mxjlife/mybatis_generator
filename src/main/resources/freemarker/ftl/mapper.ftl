package ${basicInfo.packagePath}.mapper;

import ${basicInfo.packagePath}.entity.${basicInfo.objectName}PO;
import com.hualala.app.distribution.manage.model.DcPriceRulePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * description: ${tableInfo.comment} 数据库操作接口
 * email ${basicInfo.email}
 * @author: ${basicInfo.author}
 * date ${basicInfo.createTime}
 * @version: ${basicInfo.version}
 */
@Mapper
public interface ${basicInfo.objectName}Mapper {

    /**
     * 添加数据
     */
    int insert(${basicInfo.objectName}PO ${basicInfo.paramName});

    /**
     * 使用主键 ${tableInfo.primary.property} 更新
     */
    int updateBy${tableInfo.primary.propertyUp}(${basicInfo.objectName}PO ${basicInfo.paramName});

    /**
     * 使用主键 ${tableInfo.primary.property} 查询
     */
    ${basicInfo.objectName}PO selectBy${tableInfo.primary.propertyUp}(@Param("${tableInfo.primary.property}") ${tableInfo.primary.javaType} ${tableInfo.primary.property});

    /**
     * 查询总条数
     */
    long getTotalCount();

    /**
     * 分页按条件查询信息
     * 参数说明：
     *   startTime  按照时间段过滤开始时间
     *   endTime  按照时间段过滤结束时间
     *   offset  按页查询的开始索引
     *   pageSize 页大小
     */
    List<${basicInfo.objectName}PO> selectPageByParams(@Param("params") Map<String, Object> params);

    /**
     * 按条件查询信息条数
     * 参数说明：
     *   startTime  按照时间段过滤开始时间
     *   endTime  按照时间段过滤结束时间
     *   offset  按页查询的开始索引
     *   pageSize 页大小
     */
    long selectCountByParams(@Param("params") Map<String, Object> params);

<#list tableInfo.indexs as index>
    /**
     * 使用索引 ${index.indexName} 查询数据
     */
<#if index.uniqueKey>
      ${basicInfo.objectName}PO selectBy${index.property}(<#list index.columns as c><#if c_index != 0>, </#if>@Param("${c.property}") ${c.javaType} ${c.property}</#list>);
<#else>
      List<${basicInfo.objectName}PO> selectBy${index.property}(<#list index.columns as c><#if c_index != 0>, </#if>@Param("${c.property}") ${c.javaType} ${c.property}</#list>);
</#if>

</#list>


}

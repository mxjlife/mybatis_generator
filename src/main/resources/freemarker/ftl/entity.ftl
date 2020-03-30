package ${basicInfo.packagePath}.entity;

import lombok.Data;
<#list tableInfo.packages as pkg>
    <#if pkg??>
import ${pkg};
    </#if>
</#list>

/**
 * description: ${tableInfo.comment}实体类
 * email ${basicInfo.email}
 * @author: ${basicInfo.author}
 * date ${basicInfo.createTime}
 * @version: ${basicInfo.version}
 */
@Data
public class ${basicInfo.objectName}PO {

<#list tableInfo.columns as ci>
    /**
     * ${ci.comment}
     */
    private ${ci.javaType} ${ci.property};

</#list>

}

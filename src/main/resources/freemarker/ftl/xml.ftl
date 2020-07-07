<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basicInfo.packagePath}.mapper.${basicInfo.objectName}Mapper">

    <resultMap id="BaseResultMap" type="${basicInfo.packagePath}.entity.${basicInfo.objectName}PO">
    <#list tableInfo.columns as ci>
    <#if ci.primaryKey>
        <id column="${ci.column}" property="${ci.property}" />
    </#if>
    </#list>
    <#list tableInfo.columns as ci>
    <#if !ci.primaryKey>
        <result column="${ci.column}" property="${ci.property}" />
    </#if>
    </#list>
    </resultMap>

    <sql id="Base_Column_List">
        <#list tableInfo.columns as c><#if c_index != 0>, </#if>`${c.column}`</#list>
    </sql>

    <!-- 添加数据  -->
    <insert id="insert" parameterType="${basicInfo.packagePath}.entity.${basicInfo.objectName}PO">
        INSERT INTO
        `${basicInfo.database}`.`${basicInfo.tableName}`
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list tableInfo.columns as ci>
            <#if !ci.primaryKey>
            <#if ci.javaType == 'String'>
            <if test="${ci.property} != null and ${ci.property} != '' "> `${ci.column}`, </if>
            <#else>
            <if test="${ci.property} != null "> `${ci.column}`, </if>
            </#if>
            </#if>
        </#list>
        </trim>
        VALUES
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list tableInfo.columns as ci>
            <#if !ci.primaryKey>
            <#if ci.javaType == 'String'>
            <if test="${ci.property} != null and ${ci.property} != '' "> ${r"#{"}${ci.property}${r"}"}, </if>
            <#else>
            <if test="${ci.property} != null ">  ${r"#{"}${ci.property}${r"}"}, </if>
            </#if>
            </#if>
        </#list>
        </trim>
    </insert>

    <!-- 使用主键 ${tableInfo.primary.property} 更新  -->
    <update id="updateBy${tableInfo.primary.propertyUp}" parameterType="${basicInfo.packagePath}.entity.${basicInfo.objectName}PO">
        UPDATE
        `${basicInfo.database}`.`${basicInfo.tableName}`
        <set>
            <#list tableInfo.columns as ci>
            <#if !ci.primaryKey>
            <if test="${ci.property} != null "> `${ci.column}` = ${r"#{"}${ci.property}${r"}"}, </if>
            </#if>
            </#list>
        </set>
        WHERE
            `${tableInfo.primary.column}` = ${r"#{"}${tableInfo.primary.property}${r"}"}
    </update>

    <!-- 使用主键 ${tableInfo.primary.property} 查询  -->
    <select id="selectBy${tableInfo.primary.propertyUp}" parameterType="${tableInfo.primary.javaType}" resultMap="BaseResultMap" >
        SELECT
        <include refid="Base_Column_List"/>
        FROM
        `${basicInfo.database}`.`${basicInfo.tableName}`
        WHERE
        `${tableInfo.primary.column}` = ${r"#{"}${tableInfo.primary.property}${r"}"}
    </select>

    <!-- 查询总条数  -->
    <select id="getTotalCount"  resultType="long" >
        SELECT
        count(*)
        FROM
        `${basicInfo.database}`.`${basicInfo.tableName}`
    </select>

    <!-- 分页按条件查询信息  -->
    <select id="selectPageByParams"  parameterType="java.util.Map" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM
        `${basicInfo.database}`.`${basicInfo.tableName}`
        <where>
            <#list tableInfo.columns as ci>
            <#if ci.javaType != 'Date'>
            <if test="params.${ci.property} != null "> AND `${ci.column}` = ${r"#{params."}${ci.property}${r"}"} </if>
            </#if>
            </#list>
            <#list tableInfo.columns as ci>
            <#if ci.javaType == 'Date'>
            <if test="params.startTime!=null">AND `${ci.column}` &gt;= ${r"#{params."}${ci.property}${r"}"} </if>
            <if test="params.endTime!=null">AND `${ci.column}` &lt; ${r"#{params."}${ci.property}${r"}"} </if>
            <if test="params.${ci.property} != null "> AND `${ci.column}` = ${r"#{params."}${ci.property}${r"}"} </if>
            <#break>
            </#if>
            </#list>
        </where>
        order by ${tableInfo.primary.column} DESC
        LIMIT ${r"#{params.offset}"}, ${r"#{params.pageSize}"}
    </select>

    <!-- 按条件查询信息条数  -->
    <select id="selectCountByParams"  parameterType="java.util.Map" resultType="long">
        SELECT
        count(*)
        FROM
        `${basicInfo.database}`.`${basicInfo.tableName}`
        <where>
            <#list tableInfo.columns as ci>
            <#if ci.javaType != 'Date'>
            <if test="params.${ci.property} != null "> AND `${ci.column}` = ${r"#{params."}${ci.property}${r"}"} </if>
            </#if>
            </#list>
            <#list tableInfo.columns as ci>
            <#if ci.javaType == 'Date'>
            <if test="params.startTime!=null">AND `${ci.column}` &gt;= ${r"#{params."}${ci.property}${r"}"} </if>
            <if test="params.endTime!=null">AND `${ci.column}` &lt; ${r"#{params."}${ci.property}${r"}"} </if>
            <#break>
            </#if>
            </#list>
        </where>
    </select>

<#list tableInfo.indexs as index>
    <!-- 使用索引 ${index.indexName} 查询数据  -->
    <select id="selectBy${index.property}" resultMap="BaseResultMap" >
        SELECT
        <include refid="Base_Column_List"/>
        FROM
        `${basicInfo.database}`.`${basicInfo.tableName}`
        WHERE
            <#list index.columns as c>
            `${c.column}` = ${r"#{"}${c.property}${r"}"}
            <#if c_index < (index.columns?size-1)>AND</#if>
            </#list>
    </select>

</#list>

</mapper>
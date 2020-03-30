# mybatis_generator
- 基于freemarker代码生成
- 按照手工写sql的格式重新写了xml生成的代码格式
- 基于数据库生成entity, mapper接口和xml文件
- 通过获取索引，生成基于所有索引的查询语句

# 仓库地址
- GitHub:https://github.com/mxjlife/mybatis_generator

## 数据表结构要求
- 必须有表注释，方便生成类注释
- 所有字段必须有注释，方便生成entity字段注释

## 生成方法入口
生成方法参考源码中的：test/java/com/mxjlife/tool/generator/MyGenerator.java

# 生成的实体类
``` java
package com.mxjlife.order.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * description: 订单主表实体类
 * email realmxj@163.com
 * @author: mxj
 * date 2020-03-29 23:22:36
 * @version: V1.0
 */
@Data
public class OrderMasterPO {

    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 订单ID
     */
    private String orderNo;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 创建时间
     */
    private Date createStamp;

    /**
     * 修改时间
     */
    private Date actionStamp;


}
```
# 生成的mapper接口
``` java
package com.mxjlife.order.mapper;

import com.mxjlife.order.entity.OrderMasterPO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * description: 订单主表 数据库操作接口
 * email realmxj@163.com
 * @author: mxj
 * date 2020-03-29 23:22:36
 * @version: V1.0
 */
@Mapper
public interface OrderMasterMapper {

    /**
     * 添加数据
     */
    int insert(OrderMasterPO orderMaster);

    /**
     * 使用主键 id 更新
     */
    int updateById(OrderMasterPO orderMaster);

    /**
     * 使用主键 id 查询
     */
    OrderMasterPO selectById(@Param("id") Long id);

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
    List<OrderMasterPO> selectPageByParams(@Param("params") Map<String, Object> params);

    /**
     * 按条件查询信息条数
     * 参数说明：
     *   startTime  按照时间段过滤开始时间
     *   endTime  按照时间段过滤结束时间
     *   offset  按页查询的开始索引
     *   pageSize 页大小
     */
    long selectCountByParams(@Param("params") Map<String, Object> params);

    /**
     * 使用索引 idx_orderNo 查询数据
     */
    List<OrderMasterPO> selectByOrderNo(@Param("orderNo") String orderNo);


}

```
# 生成的XML
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.mxjlife.order.mapper.OrderMasterMapper">

    <resultMap id="BaseResultMap" type="com.mxjlife.order.entity.OrderMasterPO">
          <id column="id" property="id" />
        <result column="shopId" property="shopId" />
        <result column="orderNo" property="orderNo" />
        <result column="amount" property="amount" />
        <result column="status" property="status" />
        <result column="remark" property="remark" />
        <result column="cityName" property="cityName" />
        <result column="createStamp" property="createStamp" />
        <result column="actionStamp" property="actionStamp" />
    </resultMap>

    <sql id="Base_Column_List">
        `id`, `shopId`, `orderNo`, `amount`, `status`, `remark`, `cityName`, `createStamp`, `actionStamp`
    </sql>

    <!-- 添加数据  -->
    <insert id="insert" parameterType="com.mxjlife.order.entity.OrderMasterPO">
        INSERT INTO
        `db_order`.`order_master`
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="shopId != null "> `shopId`, </if>
            <if test="orderNo != null and orderNo != '' "> `orderNo`, </if>
            <if test="amount != null "> `amount`, </if>
            <if test="status != null "> `status`, </if>
            <if test="remark != null and remark != '' "> `remark`, </if>
            <if test="cityName != null and cityName != '' "> `cityName`, </if>
            <if test="createStamp != null "> `createStamp`, </if>
            <if test="actionStamp != null "> `actionStamp`, </if>
        </trim>
        VALUES
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="shopId != null "> #{shopId}, </if>
            <if test="orderNo != null and orderNo != '' "> #{orderNo}, </if>
            <if test="amount != null ">  #{amount}, </if>
            <if test="status != null ">  #{status}, </if>
            <if test="remark != null and remark != '' "> #{remark}, </if>
            <if test="cityName != null and cityName != '' "> #{cityName}, </if>
            <if test="createStamp != null ">  #{createStamp}, </if>
            <if test="actionStamp != null ">  #{actionStamp}, </if>
        </trim>
    </insert>

    <!-- 使用主键 id 更新  -->
    <update id="updateById" parameterType="com.mxjlife.order.entity.OrderMasterPO">
        UPDATE
        `db_order`.`order_master`
        <set>
            <if test="shopId != null "> `shopId` = #{shopId}, </if>
            <if test="orderNo != null "> `orderNo` = #{orderNo}, </if>
            <if test="amount != null "> `amount` = #{amount}, </if>
            <if test="status != null "> `status` = #{status}, </if>
            <if test="remark != null "> `remark` = #{remark}, </if>
            <if test="cityName != null "> `cityName` = #{cityName}, </if>
            <if test="createStamp != null "> `createStamp` = #{createStamp}, </if>
            <if test="actionStamp != null "> `actionStamp` = #{actionStamp}, </if>
        </set>
        WHERE
            `id` = #{id}
    </update>

    <!-- 使用主键 id 查询  -->
    <select id="selectById" parameterType="Long" resultMap="BaseResultMap" >
        SELECT
        <include refid="Base_Column_List"/>
        FROM
        `db_order`.`order_master`
        WHERE
        `id` = #{id}
    </select>

    <!-- 查询总条数  -->
    <select id="getTotalCount"  resultType="long" >
        SELECT
        count(*)
        FROM
        `db_order`.`order_master`
    </select>

    <!-- 分页按条件查询信息  -->
    <select id="selectPageByParams"  parameterType="java.util.Map" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM
        `db_order`.`order_master`
        <where>
            <if test="params.id != null "> AND `id` = #{params.id} </if>
            <if test="params.shopId != null "> AND `shopId` = #{params.shopId} </if>
            <if test="params.orderNo != null "> AND `orderNo` = #{params.orderNo} </if>
            <if test="params.amount != null "> AND `amount` = #{params.amount} </if>
            <if test="params.status != null "> AND `status` = #{params.status} </if>
            <if test="params.remark != null "> AND `remark` = #{params.remark} </if>
            <if test="params.cityName != null "> AND `cityName` = #{params.cityName} </if>
            <if test="params.startTime!=null">AND `createStamp` &gt;= #{params.createStamp} </if>
            <if test="params.endTime!=null">AND `createStamp` &lt; #{params.createStamp} </if>
        </where>
        order by id DESC
        LIMIT #{params.offset}, #{params.pageSize}
    </select>

    <!-- 按条件查询信息条数  -->
    <select id="selectCountByParams"  parameterType="java.util.Map" resultType="long">
        SELECT
        count(*)
        FROM
        `db_order`.`order_master`
        <where>
            <if test="params.id != null "> AND `id` = #{params.id} </if>
            <if test="params.shopId != null "> AND `shopId` = #{params.shopId} </if>
            <if test="params.orderNo != null "> AND `orderNo` = #{params.orderNo} </if>
            <if test="params.amount != null "> AND `amount` = #{params.amount} </if>
            <if test="params.status != null "> AND `status` = #{params.status} </if>
            <if test="params.remark != null "> AND `remark` = #{params.remark} </if>
            <if test="params.cityName != null "> AND `cityName` = #{params.cityName} </if>
            <if test="params.startTime!=null">AND `createStamp` &gt;= #{params.createStamp} </if>
            <if test="params.endTime!=null">AND `createStamp` &lt; #{params.createStamp} </if>
        </where>
    </select>

    <!-- 使用索引 idx_orderNo 查询数据  -->
    <select id="selectByOrderNo" resultMap="BaseResultMap" >
        SELECT
        <include refid="Base_Column_List"/>
        FROM
        `db_order`.`order_master`
        WHERE
            `orderNo` = #{orderNo}
            
    </select>


</mapper>

```




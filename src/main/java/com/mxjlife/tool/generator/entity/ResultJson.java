package com.mxjlife.tool.generator.entity;

import lombok.Data;

/**
 * description: 返回结果
 * author mxj
 * email realmxj@163.com
 * date 2020/3/29 0:12
 */
@Data
public class ResultJson{

    private Integer code;

    private String message;

    private Object data;
}

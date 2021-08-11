package com.elco.platform.k8s.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author kay
 * @date 2021/8/3
 */
@Data
@ApiModel(value = "容器信息")
public class ConDescription implements Serializable {
    private static final long serialVersionUID = -5652483364028130356L;
    @ApiModelProperty(value = "编号")
    private int id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "空间")
    private String nameSpace;
    @ApiModelProperty(value = "所在节点")
    private String nodeName;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "重启次数")
    private String restartCount;
    @ApiModelProperty(value = "启动时间")
    private String startedAt;

}

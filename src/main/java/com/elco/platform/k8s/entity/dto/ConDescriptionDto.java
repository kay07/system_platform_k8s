package com.elco.platform.k8s.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@ApiModel("监控列表-入参")
@Data
public class ConDescriptionDto implements Serializable {
    private static final long serialVersionUID = 586755909551487012L;
    @ApiModelProperty(value = "页码")
    private int page;
    @ApiModelProperty(value = "pod名称")
    private String name;
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
}

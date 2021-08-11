package com.elco.platform.k8s.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@ApiModel("PageParam入参")
@Data
public class PageParam implements Serializable {

    private static final long serialVersionUID = -2426024252856235702L;
    @ApiModelProperty(value = "每页条数（10）")
    private int pageSize=10;
    @ApiModelProperty(value = "开始页（1）")
    private int pageNum=1;
}

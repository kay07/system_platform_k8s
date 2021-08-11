package com.elco.platform.k8s.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Env implements Serializable {

    private static final long serialVersionUID = -5779242222942637362L;
    @ApiModelProperty(value = "环境变量的key")
    private String name;
    @ApiModelProperty(value = "环境变量的value")
    private String value;
}

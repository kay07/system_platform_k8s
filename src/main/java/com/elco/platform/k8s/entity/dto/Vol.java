package com.elco.platform.k8s.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Vol implements Serializable {

    private static final long serialVersionUID = 7373992044214791057L;
    @ApiModelProperty(value = "挂载的名称")
    private String name;
    @ApiModelProperty(value = "挂载的路径（容器内）")
    private String mountPath;
    @ApiModelProperty(value = "挂载的路径（宿主机）")
    private String hostPath;
}

package com.elco.platform.k8s.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "创建服务-入参")
public class CreateDto implements Serializable {

    private static final long serialVersionUID = 1732650916381244742L;
    @ApiModelProperty(value = "服务名称")
    private String name;
    @ApiModelProperty(value = "服务端口(内部)")
    private Integer port;
    @ApiModelProperty(value = "服务端口(外部)")
    private Integer nodePort;
    @ApiModelProperty(value = "服务个数")
    private Integer replicas;
    @ApiModelProperty(value = "服务镜像地址")
    private String image;
    @ApiModelProperty(value = "cpu资源,单位m,1000m等于1核")
    private Integer cpuLimit;
    @ApiModelProperty(value = "内存资源,单位Mi")
    private Integer memoryLimit;
    @ApiModelProperty(value = "服务环境变量")
    private List<Env> env;
    @ApiModelProperty(value = "服务挂载路径")
    private List<Vol> volumes;
}

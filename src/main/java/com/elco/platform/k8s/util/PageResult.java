package com.elco.platform.k8s.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "分页响应")
public class PageResult<T> extends PageParam implements Serializable {
    private static final long serialVersionUID = 6162583694538266616L;
    @ApiModelProperty(value = "总页数")
    private int totalPage;
    @ApiModelProperty(value = "总条数")
    private long totalCount;
    @ApiModelProperty(value = "列表数据")
    private List<T> dataList;
}

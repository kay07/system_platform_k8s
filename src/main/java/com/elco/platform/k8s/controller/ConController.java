package com.elco.platform.k8s.controller;

import com.elco.platform.k8s.entity.ConDescription;
import com.elco.platform.k8s.entity.dto.ConDescriptionDto;
import com.elco.platform.k8s.service.ConService;
import com.elco.platform.k8s.util.CommonResult;
import com.elco.platform.k8s.util.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @author kay
 * @date 2021/8/3
 */
@Api(tags = "应用监控")
@RestController
@RequestMapping(value = "/alldep")
public class ConController {
    @Resource
    private ConService conService;
    @ApiOperation(value = "分页返回所有pod信息")
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public CommonResult<PageResult<ConDescription>> list(@RequestBody ConDescriptionDto dto){
        int page=dto.getPage();
        String name=dto.getName();
        String startTime=dto.getStartTime();
        String endTime =dto.getEndTime();
        PageResult<ConDescription> pageMsg=conService.list(page,name,startTime,endTime);
        return  CommonResult.success(pageMsg);
    }
}

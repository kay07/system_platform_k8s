package com.elco.platform.k8s.controller;

import com.elco.platform.k8s.entity.dto.CreateDto;
import com.elco.platform.k8s.service.Create;
import com.elco.platform.k8s.service.Delete;
import com.elco.platform.k8s.util.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "应用部署")
@RestController
@RequestMapping(value = "/svc")
public class CreateController {
    @Resource
    private Create create;

    @ApiOperation(value = "创建容器")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult<Boolean> create(@RequestBody CreateDto dto){
        if(dto.getName().equals("")||dto.getPort().equals("")||dto.getImage().equals("")||dto.getReplicas().equals("")){
            return CommonResult.failed("不能为空");
        }else {
          boolean svc=  create.createSvc(dto);
          boolean dep= create.createDep(dto);
          if (!svc||!dep){
              return CommonResult.failed("已存在，创建失败");
          }else {
              return CommonResult.success(true);
          }
        }
    }

}

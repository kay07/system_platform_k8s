package com.elco.platform.k8s.controller;

import com.elco.platform.k8s.entity.dto.CreateDto;
import com.elco.platform.k8s.service.DeployService;
import com.elco.platform.k8s.util.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "应用部署")
@RestController
@RequestMapping(value = "/svc")
public class DeployServiceController {
    @Resource
    private DeployService deployService;

    @ApiOperation(value = "创建容器")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult<Boolean> create(@RequestBody CreateDto dto){
        if(dto.getName().equals("")||dto.getPort().equals("")||dto.getImage().equals("")||dto.getReplicas().equals("")){
            return CommonResult.failed("不能为空");
        }else {
          boolean svc=  deployService.createSvc(dto);
          boolean dep= deployService.createDep(dto);
          if (!svc||!dep){
              return CommonResult.failed("已存在，创建失败");
          }else {
              return CommonResult.success(true);
          }
        }
    }

    @ApiOperation(value = "删除容器")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public CommonResult<Boolean> delete(@RequestParam String name){
        if (name.equals("")){
            return CommonResult.failed("不能为空");
        }else {
            boolean svc=  deployService.deleteSvc(name);
            boolean dep= deployService.deleteDep(name);
            if (!svc||!dep){
                return CommonResult.failed("不存在，删除失败");
            }else {
                return CommonResult.success(true);
            }
        }

    }

}

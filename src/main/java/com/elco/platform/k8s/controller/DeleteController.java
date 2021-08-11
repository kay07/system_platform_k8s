package com.elco.platform.k8s.controller;

import com.elco.platform.k8s.service.Delete;
import com.elco.platform.k8s.util.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@Api(tags = "应用部署")
@RestController
@RequestMapping(value = "/svc")
public class DeleteController {
    @Resource
    private Delete delete;

    @ApiOperation(value = "删除容器")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public CommonResult<Boolean> delete(@RequestParam String name){
        if (name.equals("")){
            return CommonResult.failed("不能为空");
        }else {
            boolean svc=  delete.deleteSvc(name);
            boolean dep= delete.deleteDep(name);
            if (!svc||!dep){
                return CommonResult.failed("不存在，删除失败");
            }else {
                return CommonResult.success(true);
            }
        }

    }
}

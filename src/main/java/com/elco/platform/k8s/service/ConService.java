package com.elco.platform.k8s.service;


import com.elco.platform.k8s.entity.ConDescription;
import com.elco.platform.k8s.util.PageResult;

/**
 * @author kay
 * @date 2021/8/3
 */
public interface ConService {
   PageResult<ConDescription> list(int page, String name, String startTime,String endTime);
}

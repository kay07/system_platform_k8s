package com.elco.platform.k8s.service;

import com.elco.platform.k8s.entity.dto.CreateDto;

public interface DeployService {
    boolean createSvc(CreateDto dto);
    boolean createDep(CreateDto dto);
    boolean deleteSvc(String name);
    boolean deleteDep(String name);
}

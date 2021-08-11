package com.elco.platform.k8s.service;

import com.elco.platform.k8s.entity.dto.CreateDto;

public interface Create {
    boolean createSvc(CreateDto dto);
    boolean createDep(CreateDto dto);
}

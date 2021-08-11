package com.elco.platform.k8s.service;

public interface Delete {
    boolean deleteSvc(String name);
    boolean deleteDep(String name);
}

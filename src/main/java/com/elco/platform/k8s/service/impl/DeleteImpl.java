package com.elco.platform.k8s.service.impl;

import com.elco.platform.k8s.config.ServerConfig;
import com.elco.platform.k8s.service.Delete;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeleteImpl implements Delete {
    @Resource
    private ServerConfig serverConfig;
    @Override
    public boolean deleteSvc(String name) {
        ApiClient client=serverConfig.getConnection();
        CoreV1Api apiInstance=new CoreV1Api(client);
        try {
            apiInstance.deleteNamespacedService(name,"default",null,null,null,null,null,null);
//            apiInstance.connectDeleteNamespacedServiceProxy(name,"default","");
            return true;
        } catch (ApiException e) {
        //    System.out.println(e.getCode());
        //    e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteDep(String name) {
        ApiClient client=serverConfig.getConnection();
        AppsV1Api apiInstance=new AppsV1Api(client);
        try {
            apiInstance.deleteNamespacedDeployment(name,"default",null,null,null,null,null,null);
            return true;
        } catch (ApiException e) {
            // e.printStackTrace();
            return false;
        }
    }

}

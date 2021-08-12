package com.elco.platform.k8s.service.impl;

import com.elco.platform.k8s.config.ServerConfig;
import com.elco.platform.k8s.entity.dto.CreateDto;
import com.elco.platform.k8s.service.DeployService;
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeployServiceImpl implements DeployService {
    @Resource
    private ServerConfig serverConfig;
    @Override
    public boolean createSvc(CreateDto dto) {

        ApiClient client = serverConfig.getConnection();
        CoreV1Api apiInstance = new CoreV1Api(client);
        V1Service body = new V1Service();
        body.setApiVersion("v1");
        body.setKind("Service");
        //设置metadata
        V1ObjectMeta objectMeta = new V1ObjectMeta();
        objectMeta.setName(dto.getName());
        Map<String, String> Labels = new HashMap<>();
        Labels.put("app", dto.getName());
        objectMeta.setLabels(Labels);
        body.setMetadata(objectMeta);
        //设置spec
        V1ServiceSpec serviceSpec = new V1ServiceSpec();
        serviceSpec.setType("NodePort");
        List<V1ServicePort> servicePorts = new ArrayList<>();
        V1ServicePort servicePort = new V1ServicePort();
        servicePort.setName(dto.getPort().toString());
        IntOrString intOrString = new IntOrString(dto.getPort());
        servicePort.setTargetPort(intOrString);
        servicePort.setPort(dto.getPort());
        servicePort.setNodePort(dto.getNodePort());
        servicePorts.add(servicePort);
        serviceSpec.setPorts(servicePorts);
        Map<String, String> selector = new HashMap<>();
        selector.put("app", dto.getName());
        serviceSpec.setSelector(selector);
        body.setSpec(serviceSpec);
        try {
            apiInstance.createNamespacedService("default", body, null, null, null);
            return true;
        } catch (ApiException e) {
            //409 已创建
            System.out.println(e.getCode());
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean createDep(CreateDto dto) {

        V1Deployment body = new V1Deployment();
        body.setApiVersion("apps/v1");
        body.kind("Deployment");
        //metadata
        V1ObjectMeta objectMeta = new V1ObjectMeta();
        objectMeta.setName(dto.getName());
        Map<String, String> Labels = new HashMap<>();
        Labels.put("app", dto.getName());
        objectMeta.setLabels(Labels);
        body.setMetadata(objectMeta);
        //spec
        V1DeploymentSpec deploymentSpec = new V1DeploymentSpec();
        //spec-replicas
        deploymentSpec.setReplicas(dto.getReplicas());
        //spec-selector
        Map<String, String> matchLabels = new HashMap<>();
        matchLabels.put("app", dto.getName());
        V1LabelSelector selector = new V1LabelSelector();
        selector.setMatchLabels(matchLabels);
        deploymentSpec.setSelector(selector);
        //spec-template
        V1PodTemplateSpec templateSpec = new V1PodTemplateSpec();
        //spec-template-metadata
        templateSpec.setMetadata(objectMeta);//直接使用上一步的metadata
        //spec-template-spec
        V1PodSpec podSpec = new V1PodSpec();
        //spec-template-spec-container
        List<V1Container> listContainers = new ArrayList<>();
        V1Container container = new V1Container();
        container.setName(dto.getName());
        container.setImage(dto.getImage());
        container.setImagePullPolicy("Always");
        //spec-template-spec-container-resources
        V1ResourceRequirements resources=new V1ResourceRequirements();
        Quantity cpu=new Quantity(dto.getCpuLimit().toString()+"m");
        Quantity memory=new Quantity(dto.getMemoryLimit().toString()+"Mi");
        //limits
        Map<String, Quantity> limits=new HashMap<>();

        limits.put("cpu",cpu);
        limits.put("memory",memory);
        resources.setLimits(limits);
        //resources
        Map<String, Quantity> request=new HashMap<>();
        request.put("cpu",cpu);
        request.put("memory",memory);
        resources.setRequests(request);
        container.setResources(resources);
        //env
        if (!dto.getEnv().isEmpty() && dto.getEnv() != null) {
            List<V1EnvVar> env = new ArrayList<>();
            for (int i = 0; i < dto.getEnv().size(); i++) {
                String n = dto.getEnv().get(i).getName();
                String v = dto.getEnv().get(i).getValue();
                V1EnvVar envVar = new V1EnvVar();
                envVar.setName(n);
                envVar.setValue(v);
                env.add(envVar);
            }
            container.setEnv(env);
        }

        if(!dto.getVolumes().isEmpty()&&dto.getEnv()!=null){
            //volumeMounts和volumes
            List<V1Volume> hostVolume=new ArrayList<>();
            List<V1VolumeMount> v1Volumes=new ArrayList<>();
            V1Volume v1Volume1=new V1Volume();
            for (int j=0;j<dto.getVolumes().size();j++){
                String n=dto.getVolumes().get(j).getName();
                String m=dto.getVolumes().get(j).getMountPath();
                String h=dto.getVolumes().get(j).getHostPath();
                V1VolumeMount v1Volume=new V1VolumeMount();
                v1Volume.setName(n);
                v1Volume.setMountPath(m);
                v1Volumes.add(v1Volume);
                v1Volume1.setName(n);
                V1HostPathVolumeSource hostPath =new V1HostPathVolumeSource();
                hostPath.setPath(h);
                v1Volume1.setHostPath(hostPath);
                hostVolume.add(v1Volume1);
            }
            container.setVolumeMounts(v1Volumes);
            //spec-template-spec-volumes
            podSpec.volumes(hostVolume);
        }
        listContainers.add(container);
        podSpec.containers(listContainers);
        templateSpec.setSpec(podSpec);
        deploymentSpec.setTemplate(templateSpec);
        body.setSpec(deploymentSpec);
        ApiClient client =serverConfig.getConnection();
        AppsV1Api apiInstance = new AppsV1Api(client);
        try {
            apiInstance.createNamespacedDeployment("default", body, null, null, null);
            return true;
        } catch (ApiException e) {
            e.printStackTrace();
            return false;
        }


    }
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

package com.elco.platform.k8s.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elco.platform.k8s.entity.ConDescription;
import com.elco.platform.k8s.service.ConService;
import com.elco.platform.k8s.util.PageParam;
import com.elco.platform.k8s.util.PageResult;
import com.xiaoleilu.hutool.http.HttpRequest;
import com.xiaoleilu.hutool.http.HttpUtil;
import jdk.nashorn.internal.scripts.JO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author kay
 * @date 2021/8/3
 */
@Service
public class ConServiceImpl implements ConService {

    @Value("${k8s.url}")
    private String addr;
    @Value("${k8s.token}")
    private String token;

    public PageResult<ConDescription> list(int page, String name, String startTime,String endTime) {
        //k8s的服务地址
        String url = addr + "api/v1/namespaces/default/pods";
        String msg = HttpRequest.get(url).header("Authorization", "Bearer " + token).execute().body();
        //msg是调用后返回的结果，需要解析msg
        JSONObject jsonObject = JSON.parseObject(msg);
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        int amount = jsonArray.toArray().length;
        List<ConDescription> list=new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            ConDescription conDescription=new ConDescription();
            //set id
            conDescription.setId(i + 1);
            JSONObject jO = (JSONObject) jsonArray.get(i);
            //set name
            JSONObject metadata = (JSONObject) jO.get("metadata");
            String name_i = metadata.get("name").toString();
            conDescription.setName(name_i);
            //set namespace
            String namespace = metadata.get("namespace").toString();
            conDescription.setNameSpace(namespace);
            //set nodename
            JSONObject spec=(JSONObject) jO.get("spec");
            String nodeName=spec.get("nodeName").toString();
            conDescription.setNodeName(nodeName);
            //set status
            String status=((JSONObject) jO.get("status")).get("phase").toString();
            conDescription.setStatus(status);
            //set restartCount
            JSONObject status_1=(JSONObject)jO.get("status");
            JSONArray jsonArray1=status_1.getJSONArray("containerStatuses");
            String restartCount=((JSONObject)jsonArray1.get(0)).get("restartCount").toString();
            conDescription.setRestartCount(restartCount);
            //set startTime
            String startedAt=((JSONObject)jO.get("status")).get("startTime").toString();
            conDescription.setStartedAt(startedAt);
            list.add(conDescription);
        }
        //对查询的list结果进行处理
        List<ConDescription> list1=new ArrayList<>();
        boolean bName=(name!=null)&&(!"".equals(name));
        boolean bTime=(startTime!=null)&&(endTime!=null)&&(!"".equals(startTime))&&(!"".equals(endTime));
        for(int i=0;i<list.size();i++){
            boolean eName=((ConDescription)(list.get(i))).getName().contains(name);
            boolean eTime = false;
            if (bTime){
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date d1=simpleDateFormat.parse(startTime);
                    Date d2=simpleDateFormat.parse(endTime);
                    Date d=simpleDateFormat.parse(((ConDescription)(list.get(i))).getStartedAt());
                    if ((d.compareTo(d1)!=-1)&&(d.compareTo(d2)!=1)){
                        //当前时间在所提供的时间段内
                        eTime=true;
                    }
                } catch (ParseException e) {
                 //    e.printStackTrace();
                }
            }
            //名称和时间都不为空时
            if (bName&&bTime){
                if(eName&&eTime){
                    list1.add(list.get(i));
                }
            }
            //名称不为空时间为空时
            else if (bName){
                if (eName){
                    list1.add(list.get(i));
                }
            }
            //名称为空时间不为空时
            else if (bTime){
                if (eTime){
                    list1.add(list.get(i));
                }
            }
            //均为空时
            else {
                    list1=list;
            }
        }
        //返回结果
        int totalPage=(list1.size()+9)/10;
        if (page>0&&page<=totalPage){
            PageResult<ConDescription> pageResult=new PageResult<>();
            pageResult.setDataList(list1);
            pageResult.setTotalPage(totalPage);
            pageResult.setTotalCount(list1.size());
            return pageResult;
        }else {
            return null;
        }
    }
}

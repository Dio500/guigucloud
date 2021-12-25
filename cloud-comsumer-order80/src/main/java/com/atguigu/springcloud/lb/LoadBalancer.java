package com.atguigu.springcloud.lb;

//import org.springframework.cloud.client.ServiceInstance;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @auther zzyy
 * @create 2020-02-19 20:31
 */
public interface LoadBalancer {
    //可用的服务实例
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}

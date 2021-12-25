package com.atguigu.springcloud.lb;

//import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther zzyy
 * @create 2020-02-19 20:33
 */

@Component
public class MyLB implements LoadBalancer {
    //原子整型
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement() {
        int current;//当前值
        int next;//修改值
        //自旋锁，while为true就继续循环
        do {
            current = this.atomicInteger.get();
            //Integer的最大值2147483647
            next = current >= 2147483647 ? 0 : current + 1;
        }
        while(!this.atomicInteger.compareAndSet(current,next));
        System.out.println("*****第几次访问，次数next: "+next);
        return next;
    }


    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        //负载均衡算法：rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}

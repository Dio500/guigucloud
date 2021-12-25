package com.atguigu.myrule;

import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon负载均衡自定义配置类（替换掉默认的轮询）
 *
 * 自定义配置类不能放在@componetScan所扫描的范围里（例：springboot主启动类同级包和子包）
 * 否则自定义配置类会被所有Ribbon客户端共享，达不到特殊定制的目的了
 * springboot主启动类上加@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration= MySelfRule.class)
 */
@Configuration
public class MySelfRule
{
    //负载均衡算法：rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标，每次服务启动后rest接口从1开始
    @Bean
    public IRule myRule() {
        //RoundRobinRule() 轮询（默认）
        //RetryRule() 先按照RoundRobinRule()，如果获取服务失败则在指定时间内进行试，获取可用服务
        //WeightedResponseTimeRule() RoundRobinRule()的扩展，响应速度越快的实例选择权重越大，越容易被选择
        //BestAvailableRule() 先过滤由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发最小的服务
        //AvailabilityFilteringRule() 先过滤故障实例，再选择并发较小的实例
        //ZoneAvoidanceRule() 默认规则，复合判断server所在区域的性能和server的可用选择服务器
        return new RandomRule();//定义为随机
    }
}

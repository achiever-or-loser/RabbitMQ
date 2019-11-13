package com.example.mqdemo;

import com.example.mqdemo.po.Book;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class MqdemoApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        // 对象被默认JAVA序列化发送，参数：Exchange，routingKey，消息
        System.out.println("start");
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("exchange.direct",
                    "iceoooodin.news", "csc"+i);
        }
        System.out.println("end");

    }

    @Test
    public void receive() {
        // 接受数据
        Object o = rabbitTemplate.receiveAndConvert("iceoooodin.news");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    @Test
    public void contextLoads1() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "第一个数据");
        map.put("data", Arrays.asList("helloworld", 123, true));
        // 对象被默认JAVA序列化发送
        rabbitTemplate.convertAndSend("exchange.direct", "iceoooodin.news", map);
    }

    @Test
    public void contextLoads12() {
        Map<String, Object> map = (Map<String, Object>) rabbitTemplate.receiveAndConvert("iceoooodin.news");
        for (Map.Entry entry : map.entrySet()) {
            System.out.println("K:" + entry.getKey() + "; V=" + entry.getValue());
        }
    }

    @Test
    public void contextLoads2() {
        // 对象被默认JAVA序列化发送
        rabbitTemplate.convertAndSend("exchange.direct", "iceoooodin.news", new Book("金瓶M", "em.."));
    }

    @Test
    public void sendMst() {
        rabbitTemplate.convertAndSend("exchange.direct", "", new Book("红楼", "草 "));
    }


    @Autowired
    AmqpAdmin amqpAdmin;//这个amqpadmin是用来管理QP的，可以创建、删除等操作；

    @Test
    public void creatExchange() {
        // 创建Exchange
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
        System.out.println("创建exchange完成");
        // 创建Queue
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));
        System.out.println("创建Queue完成");
        // 绑定
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE, "amqpadmin.exchange", "amqp.haha", null));
    }
}

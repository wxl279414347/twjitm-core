package com.twjitm.kafka;import com.twjitm.TestSpring;import com.twjitm.core.common.kafka.KafkaTaskType;import com.twjitm.core.common.kafka.NettyKafkaProducerListener;import com.twjitm.core.spring.SpringServiceManager;import javax.annotation.Resource;/** * @author twjitm - [Created on 2018-09-05 12:25] * @company https://github.com/twjitm/ * @jdk java version "1.8.0_77" */public class TestKafka {    public static void main(String[] args) {        TestSpring.initSpring();        test();    }    public static void test() {        WordChatTask task = new WordChatTask(KafkaTaskType.WORLD_CHAT);        task.setValue("世界，你好");        NettyKafkaProducerListener nettyKafkaProducerListener = SpringServiceManager.getSpringLoadService().getNettyKafkaProducerListener();        nettyKafkaProducerListener.put(task);    }}
/**
 * 
 */
package org.dragonli.tools.mq.rocketmq;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @author mac
 *
 */
public class RocketMQTool {

	protected static final Random random = new Random();
	
	public static MetaQEventClient createClient(MessageListenerConcurrently listerner,String consumerGroup
			,int maxThread,int minThread,String adds,String subscribeGroup,String SubscribeKey) 
	{
		String id = System.currentTimeMillis() + "" + random.nextInt(1000000);
		MetaQEventClient metaQEventClient = new MetaQEventClient();
		metaQEventClient.setConsumerGroup(consumerGroup);
		metaQEventClient.setConsumeThreadMax(maxThread + "");
		metaQEventClient.setConsumeThreadMin(minThread + "");
		metaQEventClient.setNamesrvAddr(adds);
		metaQEventClient.setInstanceName(consumerGroup+id);
		metaQEventClient.setMessageListener(listerner);
		Map<String, String> map = new HashMap<>();
		map.put(subscribeGroup, SubscribeKey);
		metaQEventClient.setSubscribeMap(map);
		try {
			metaQEventClient.init();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		return metaQEventClient;
	}
	
	public static MessageProducer createProducer(String adds,String groupName,int topicQueueNums) {
		DefaultMQProducer producer = new DefaultMQProducer(groupName);
		producer.setNamesrvAddr(adds);
		producer.setDefaultTopicQueueNums(topicQueueNums);
		try {
			producer.start();
		} catch (MQClientException e) {}
		return new MessageProducer(producer);
	}
	
}

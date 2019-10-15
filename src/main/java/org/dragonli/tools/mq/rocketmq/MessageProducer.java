package org.dragonli.tools.mq.rocketmq;

import javax.annotation.PreDestroy;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Warpper for MQProducer to send message to specific queue.
 *
 * @author liaoxuefeng
 *
 */
public class MessageProducer//<T> 
{

	final Logger logger = LoggerFactory.getLogger(getClass());
	final MQProducer producer;

	public MessageProducer(MQProducer producer) {
		this.producer = producer;
	}

	
	public void sendStringMessage(String groupName,String key, String msg) throws Exception {
		doSend(groupName,key, msg.getBytes("UTF-8"), true);
	}

	void doSend(String groupName,String key, byte[] data, boolean delay) throws InterruptedException {
		Message msg = new Message(groupName,key, data);
		if (delay) {
			// about 5 seconds later:
			msg.setDelayTimeLevel(2);
		}
		try {
			@SuppressWarnings("unused")
			SendResult sr = producer.send(msg);
//			logger.info("Send message result: {}", sr);
		} catch (MQClientException | MQBrokerException | RemotingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Shutdown the producer.
	 */
	@PreDestroy
	public synchronized void close() {
		logger.info("shutdown message producer...");
		this.producer.shutdown();
	}
}

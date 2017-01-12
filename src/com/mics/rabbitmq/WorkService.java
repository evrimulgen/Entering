package com.mics.rabbitmq;

import java.io.UnsupportedEncodingException;

import com.mics.conf.BaseConf;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class WorkService {
	private final String EXCHANGE_NAME = "";
	public final String QUEUE_NAME ="";
	private final String ROUTINGKEY = "";	  
	public boolean durable = true;
	public boolean autoDelete = false;
	private BaseConf baseConf = BaseConf.getInstance();

	public void start() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(baseConf.getRM_username());
		factory.setPassword(baseConf.getRM_password());
		factory.setHost(baseConf.getRM_host());
		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

	    channel.exchangeDeclare(EXCHANGE_NAME, "fanout", durable, autoDelete, null);
	    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
	    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTINGKEY);
		channel.basicQos(1);

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) {
				try {
					doWork(body);
					channel.basicAck(envelope.getDeliveryTag(), false);
				} catch (Exception e) {
					//log exception
					
				}
			}
		};
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}

	private static void doWork(byte[] body) throws InterruptedException, UnsupportedEncodingException {
		String task = new String(body, "UTF-8");
		for (char ch : task.toCharArray()) {
			if (ch == '.') {
				Thread.sleep(1000);
			}
		}
	}
}
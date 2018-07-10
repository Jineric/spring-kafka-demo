package com.jineric.springkafkademo;

import com.jineric.springkafkademo.consumer.Receiver;
import com.jineric.springkafkademo.model.Car;
import com.jineric.springkafkademo.producer.Sender;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringKafkaDemoApplicationTest {
	@Autowired
	private Sender sender;

	@Autowired
	private Receiver receiver;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, "json.t");

	@Before
	public void setUp() throws Exception {
		// wait until the partitions are assigned
		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer,
					embeddedKafka.getPartitionsPerTopic());
		}
	}

	@Test
	public void testReceive() throws Exception {
		Car car = new Car("Passat", "Volkswagen", "ABC-123");
		sender.send(car);

		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		assertThat(receiver.getLatch().getCount()).isEqualTo(0);
	}
}

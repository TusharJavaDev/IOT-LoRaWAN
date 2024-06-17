package com.iot.lorawan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import com.iot.mqtt.config.Mqtt;
import com.iot.mqtt.config.MqttPublishModel;
import com.iot.mqtt.config.MqttSubscribeModel;

@Component
public class LoRaWANService {

	@Autowired
	private SimpMessagingTemplate template;

	public ResponseEntity subscribeChannel(@RequestParam(value = "topic") String topic, String uid)
			throws InterruptedException, org.eclipse.paho.client.mqttv3.MqttException {
		List<MqttSubscribeModel> messages = new ArrayList<>();
		CountDownLatch countDownLatch = new CountDownLatch(10);
		Mqtt.getInstance().subscribeWithResponse(topic, (s, mqttMessage) -> {
			MqttSubscribeModel mqttSubscribeModel = new MqttSubscribeModel();
			mqttSubscribeModel.setId(mqttMessage.getId());
			mqttSubscribeModel.setMessage(new String(mqttMessage.getPayload()));
			mqttSubscribeModel.setQos(mqttMessage.getQos());
			messages.add(mqttSubscribeModel);
			countDownLatch.countDown();
		});
		countDownLatch.await(10, TimeUnit.MILLISECONDS);
		template.convertAndSendToUser(uid, "/" + topic, messages);

		return new ResponseEntity(HttpStatus.OK);
	}

	public ResponseEntity publish(MqttPublishModel messagePublishModel) throws MqttPersistenceException, MqttException {
		MqttMessage mqttMessage = new MqttMessage(messagePublishModel.getMessage().getBytes());
		mqttMessage.setQos(messagePublishModel.getQos());
		mqttMessage.setRetained(messagePublishModel.getRetained());
		Mqtt.getInstance().publish(messagePublishModel.getTopic(), mqttMessage);
		return new ResponseEntity(HttpStatus.CREATED);
	}
}

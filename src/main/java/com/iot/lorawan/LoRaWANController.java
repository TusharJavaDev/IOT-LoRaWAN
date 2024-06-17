package com.iot.lorawan;

import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iot.config.RequestInterceptor;
import com.iot.mqtt.config.MqttPublishModel;

@RestController
@RequestMapping(value = "/api")
public class LoRaWANController {
	@Autowired
	LoRaWANService lorawanService;

	@GetMapping("/subscribe")
	public ResponseEntity subscribe(@RequestParam(name = "topic") String topic,
			@RequestInterceptor("value") Map<String, String> tokenResponse) throws InterruptedException, MqttException {
		return lorawanService.subscribeChannel(topic, tokenResponse.get("uid"));
	}

	@PostMapping("/publish")
	public ResponseEntity publishMessage(@RequestBody MqttPublishModel messagePublishModel)
			throws org.eclipse.paho.client.mqttv3.MqttException {
		return lorawanService.publish(messagePublishModel);
	}

}

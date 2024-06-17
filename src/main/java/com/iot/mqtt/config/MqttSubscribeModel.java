package com.iot.mqtt.config;

import lombok.Data;

public @Data class MqttSubscribeModel {

	private String message;
	private Integer qos;
	private Integer id;

}

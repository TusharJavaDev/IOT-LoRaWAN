package com.iot.mqtt.config;
import lombok.Data;
/*
 * Simple class to put publish request
 */
public @Data  class MqttPublishModel {

	private String topic;

	private String message;

	private Boolean retained;

	private Integer qos;

	
}
package com.iot.mqtt.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/*
 * MQTT configuration Singleton class created to get instance of MQTT broker.
 * The org.eclipse.paho.client.mqttv3 has been used as a broker.
 */
@PropertySource("classpath:values.properties")
public class Mqtt {

	@Value("${mqtt.publisher.id}")
	private static String MQTT_PUBLISHER_ID;
	@Value("${mqtt.publisher.address}")
	private static String MQTT_SERVER_ADDRES;
	@Value("${mqtt.username}")
	private static String MQTT_USERNAME;
	@Value("${mqtt.password}")
	private static String MQTT_PASSWORD;

	private static IMqttClient instance;

	public static IMqttClient getInstance() {
		try {
			if (instance == null) {
				instance = new MqttClient(MQTT_SERVER_ADDRES, MQTT_PUBLISHER_ID);
				MqttConnectOptions options = new MqttConnectOptions();
				options.setAutomaticReconnect(true);
				options.setCleanSession(true);
				options.setConnectionTimeout(10);
				options.setPassword(MQTT_PASSWORD.toCharArray());
				options.setUserName(MQTT_USERNAME);
				if (!instance.isConnected()) {
					instance.connect(options);
				}
			}
		} catch (MqttException e) {
			e.printStackTrace();
		}

		return instance;
	}

	private Mqtt() {

	}
}
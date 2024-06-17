package com.iot.websocket.config;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.iot.security.JwtService;

class CustomHandshakeHandler extends DefaultHandshakeHandler {
	@Autowired
	JwtService jwtService;

	/*
	 * This class attatches the user uid with every connection with the client
	 * through socket
	 */
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		if (request.getHeaders().get("Authorization") != null) {
			String authHeader = request.getHeaders().get("Authorization").get(0);
			String uid = jwtService.extractAllClaims(authHeader).get("uid").toString();
			return new StompPrincipal(uid);

		} else {
			return null;
		}

	}
}
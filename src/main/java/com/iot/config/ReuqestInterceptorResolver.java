package com.iot.config;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Repository
public class ReuqestInterceptorResolver implements HandlerMethodArgumentResolver {

	public Map<String, String> resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		try {
			jakarta.servlet.http.HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes()).getRequest();

			String res = "Invalid Request";

			String[] parts = servletRequest.getHeader("Authorization").split("\\.");
			Base64 base64Url = new Base64(true);

//			System.out.println(" JWT Body ");
			String body = new String(base64Url.decode(parts[1]));
			JSONObject payload = new JSONObject(body);

			Map<String, String> map = new HashedMap<>();
			if (!org.json.JSONObject.NULL.equals(payload.opt("uid"))) {
				map.put("uid", payload.getString("uid"));
			}

			return map;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(RequestInterceptor.class) != null;
	}
}

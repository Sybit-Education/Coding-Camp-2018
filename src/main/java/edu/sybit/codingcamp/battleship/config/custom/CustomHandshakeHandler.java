/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.config.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String userId = "";
        String cookie = request.getHeaders().get("cookie").get(0);
        if (cookie != null) {
            userId = getUserIdByRegex(cookie);
        }

        return new StompPrincipal(userId.equals("") ? UUID.randomUUID().toString() : userId);
    }

    private String getUserIdByRegex(String cookie) {
        String result = "";
        String patternString = ".*(?:userId=)(.*)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(cookie);
        if (matcher.find()) {
            try {
                result = matcher.group(1);
            } catch (IllegalStateException e) {
                LOGGER.info("No UserId in Cookie found!");
            }

        }
        return result;
    }
}

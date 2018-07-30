/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.service;

import edu.sybit.codingcamp.battleship.objects.Player;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingService.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessageToUser(String destination, Player user, Message message) {
        LOGGER.debug("--> sendMessageToUser destionation=" + destination + ", user=" + user);
        //TODO Auf dem messaging Template die Send to User Funktion aufrufen
        LOGGER.debug("<-- sendMessageToUser");
    }

}

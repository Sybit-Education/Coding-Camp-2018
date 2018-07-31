/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.service;

import edu.sybit.codingcamp.battleship.objects.jsonObjects.Message;
import edu.sybit.codingcamp.battleship.objects.Player;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class MessagingServiceTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private MessagingService messagingService;

    @Before
    public void init() {
        initMocks(this);
    }

    @Test
    public void sendMessageToUserTest() {
        String destination = "destination";
        Player user = new Player();
        Message message = new Message();
        messagingService.sendMessageToUser(destination, user, message);
        verify(simpMessagingTemplate).convertAndSendToUser(any(),eq(destination), any());
    }
}

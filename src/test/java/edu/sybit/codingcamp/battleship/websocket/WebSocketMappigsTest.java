/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.websocket;

import edu.sybit.codingcamp.battleship.objects.Match;
import edu.sybit.codingcamp.battleship.objects.Player;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.Message;
import edu.sybit.codingcamp.battleship.service.MatchService;
import edu.sybit.codingcamp.battleship.service.MessagingService;
import edu.sybit.codingcamp.battleship.service.PlayerService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class WebSocketMappigsTest {

    @Mock
    private MessagingService messagingServiceMock;

    @Mock
    private MatchService matchServiceMock;

    @Mock
    private PlayerService mockedPlayerService;

    @Mock
    private Match matchMock;

    @InjectMocks
    private WebSocketMappings webSocketMappings;

    @Before
    public void init() {
        initMocks(this);
    }


    @Ignore
    @Test
    public void gamefieldTest() throws Exception {
        Player sentFrom = new Player();
        sentFrom.setPlayerId("Uername");

        when(matchServiceMock.getMatchById(any())).thenReturn(matchMock);

        Message incomingMessage = new Message("messageType", "messageContent");
        incomingMessage.setSendFrom(sentFrom);

        webSocketMappings.gamefield(incomingMessage);

        verify(mockedPlayerService).addGamefieldToPlayer(sentFrom, "messageContent");
    }
}

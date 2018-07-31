/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.objects.jsonObjects;

import edu.sybit.codingcamp.battleship.objects.Player;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;

public class MessageTest {
    private final String messageType = "MessageType";
    private final String messageContent = "MessageContent";
    private Message message;

    @Before
    public void init(){
        this.message = new Message();
    }

    @Test
    public void constructorTest() {
        this.message = new Message(messageType, messageContent);
        assertThat(message.getMessageType(), is(messageType));
        assertThat(message.getMessageContent(), is(messageContent));
    }
    
    @Test
    public void getterSetterTest() {
        String matchId = "matchId";
        Player sendTo = new Player("sendToUser");
        Player sendFrom = new Player("sendFromUser");
        String newContent = "newContent";

        assertThat(message.getSendFrom(), is(nullValue()));
        assertThat(message.getSendTo(), is(nullValue()));
        assertThat(message.getMatchId(), is(nullValue()));

        message.setMatchId(matchId);
        message.setSendFrom(sendFrom);
        message.setSendTo(sendTo);
        message.setMessageContent(newContent);

        assertThat(message.getSendFrom(), is(sendFrom));
        assertThat(message.getSendTo(), is(sendTo));
        assertThat(message.getMatchId(), is(matchId));
        assertThat(message.getMessageContent(), is(newContent));
    }
}

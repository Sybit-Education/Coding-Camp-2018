/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.objects.jsonObjects;

import edu.sybit.codingcamp.battleship.objects.Player;

import java.io.Serializable;


public class Message implements Serializable{
    private String messageType;

    private Player sendFrom;

    private Player sendTo;

    private String messageContent;

    private String matchId;

    public Message() {
    }

    public Message(String messageType, String messageContent) {
        this.messageType = messageType;
        this.messageContent = messageContent;
    }

    public String getMessageType() {
        return messageType;
    }

    public Player getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(Player sendFrom) {
        this.sendFrom = sendFrom;
    }

    public Player getSendTo() {
        return sendTo;
    }

    public void setSendTo(Player sendTo) {
        this.sendTo = sendTo;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
}

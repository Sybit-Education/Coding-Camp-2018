/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.service;

import edu.sybit.codingcamp.battleship.objects.Match;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.Message;
import java.util.TimerTask;
import org.springframework.stereotype.Component;

@Component
public class GameOverTask extends TimerTask {
    
    private Match currentMatch;
    
    private MessagingService messagingService;
        
    public void setMatch(Match currentMatch){
        this.currentMatch = currentMatch;
    }
    
    public void setMessagingService(MessagingService messagingService) {
        this.messagingService = messagingService;
    }    
    
    @Override
    public void run(){
        messagingService.sendMessageToUser("/match", currentMatch.getPlayer1(), new Message("gameOver", "End"));
        messagingService.sendMessageToUser("/match", currentMatch.getPlayer2(), new Message("gameOver", "End"));
    }
}

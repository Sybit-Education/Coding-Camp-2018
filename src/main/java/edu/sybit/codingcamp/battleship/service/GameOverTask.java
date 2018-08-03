/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sybit.codingcamp.battleship.service;

import edu.sybit.codingcamp.battleship.objects.Match;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.Message;
import edu.sybit.codingcamp.battleship.service.MatchService;
import edu.sybit.codingcamp.battleship.service.MessagingService;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GameOverTask extends TimerTask
{        
    private Match currentMatch;
    
    private MessagingService messagingService;
        
    public void setMatch(Match currentMatch){
        this.currentMatch = currentMatch;
    }
    
    public void setMessagingService(MessagingService messagingService) {
        this.messagingService = messagingService;
    }    
    public void run(){
         messagingService.sendMessageToUser("/match", currentMatch.getPlayer1(), new Message("gameOver", "End"));
         messagingService.sendMessageToUser("/match", currentMatch.getPlayer2(), new Message("gameOver", "End"));
    }

}

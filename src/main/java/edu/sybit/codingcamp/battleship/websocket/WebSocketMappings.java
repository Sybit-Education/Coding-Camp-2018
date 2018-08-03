/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.websocket;

import edu.sybit.codingcamp.battleship.exception.MatchNotFoundException;
import edu.sybit.codingcamp.battleship.exception.PlayerException;
import edu.sybit.codingcamp.battleship.objects.Match;
import edu.sybit.codingcamp.battleship.objects.Player;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.Box;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.GameField;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.Message;
import edu.sybit.codingcamp.battleship.service.JsonConverter;
import edu.sybit.codingcamp.battleship.service.MatchService;
import edu.sybit.codingcamp.battleship.service.MessagingService;
import edu.sybit.codingcamp.battleship.service.PlayerService;
import edu.sybit.codingcamp.battleship.service.GameOverTask;
import java.util.Timer;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketMappings {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketMappings.class);

    @Autowired
    private MatchService matchService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MessagingService messagingService;

    @MessageMapping("/match/gamefield")
    public void gamefield(Message message) {
        LOGGER.debug("--> gamefield");
        
        Player player = message.getSendFrom();
        
        Match match;
        try {
            match = matchService.getMatchById(message.getMatchId());
        } catch (MatchNotFoundException ex) {
            LOGGER.debug("no Match found -> create new one.");
            match = matchService.createNewMatch(message.getMatchId(), player);
        }
        
        if(!(player.equals(match.getPlayer1())) && (match.getPlayer2() == null)) {
            //set second player
            matchService.addOpponentPlayer(match, player);
            
        } 
            
        playerService.addGamefieldToPlayer(player, message.getMessageContent());

        Message responseMessage = new Message("saveResponse", "{\"saveState\":true}");
        responseMessage.setSendTo(player);
        messagingService.sendMessageToUser("/match", player, responseMessage);

        LOGGER.debug("<-- gamefield");
    }
    
    @MessageMapping("/match/gamfielddata")
    public void getGamFieldData(Message gamefieldMessage) {
        LOGGER.debug("--> getGamefieldData");
        Match currentMatch = null;
        try {
            currentMatch = matchService.getMatchById(gamefieldMessage.getMatchId());
        } catch (MatchNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }

        Player player1 = currentMatch.getPlayer1();
        Player player2 = currentMatch.getPlayer2();

        GameField gameFieldForPlayer1 = matchService.getGameFieldForPlayer(currentMatch, player1);

        if(player2  != null){
            GameField gameFieldForPlayer2 = matchService.getGameFieldForPlayer(currentMatch, player2);
            GameField gameFieldForPlayer1Pruned = matchService.pruneGameField(gameFieldForPlayer1);
            GameField gameFieldForPlayer2Pruned = matchService.pruneGameField(gameFieldForPlayer2);
            Message messageForPlayer1 = matchService.buildGameFieldDataMessage(player1, gameFieldForPlayer1, gameFieldForPlayer2Pruned, true);
            Message messageForPlayer2 = matchService.buildGameFieldDataMessage(player2, gameFieldForPlayer2, gameFieldForPlayer1Pruned, true);
            messagingService.sendMessageToUser("/match", player1, messageForPlayer1);
            messagingService.sendMessageToUser("/match", player2, messageForPlayer2);
            matchService.setCurrentPlayer(currentMatch,1);
        }else{
            Message messageForPlayer1 = matchService.buildGameFieldDataMessage(player1, gameFieldForPlayer1, new GameField(), true);
            messagingService.sendMessageToUser("/match", player1, messageForPlayer1);
        }

        LOGGER.debug("<-- getGamefieldData");
    }

    @MessageMapping("/match/shot")
    public void shot(Message shot) {
        LOGGER.debug("--> shoot: on " + shot.getMessageContent());
        String currentPlayerId = shot.getSendFrom().getPlayerId();

        try {
            Match currentMatch = matchService.getMatchById(shot.getMatchId());
            Timer timer= new Timer();
            GameOverTask gameOverTask = new GameOverTask();
            gameOverTask.setMatch(currentMatch);
            gameOverTask.setMessagingService(messagingService);
            timer.schedule(gameOverTask,60000);
            Box box = JsonConverter.convertStringToBox(shot.getMessageContent());
            Player winnerPlayer = matchService.performShot(currentPlayerId,currentMatch, box);
            if(winnerPlayer != null){
                //Es hat jemand Gewonnen
                messagingService.sendMessageToUser("/match", currentMatch.getPlayer1(), new Message("gameOver", "End"));
                messagingService.sendMessageToUser("/match", currentMatch.getPlayer2(), new Message("gameOver", "End"));
            }
        } catch (MatchNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.debug("<-- shoot");
    }
}
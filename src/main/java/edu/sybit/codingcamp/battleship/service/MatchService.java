/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */
package edu.sybit.codingcamp.battleship.service;

import edu.sybit.codingcamp.battleship.config.custom.CustomMessageHeaderAccessor;
import edu.sybit.codingcamp.battleship.exception.GeneralException;
import edu.sybit.codingcamp.battleship.exception.MatchNotFoundException;
import edu.sybit.codingcamp.battleship.exception.PlayerException;
import edu.sybit.codingcamp.battleship.objects.Match;
import edu.sybit.codingcamp.battleship.objects.Player;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.Box;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.BoxStatus;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.GameField;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.Message;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.Ship;
import edu.sybit.codingcamp.battleship.repository.MatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * This Service provides functionality to manage the Matches.
 *
 * @author ssr
 */
@Service
public class MatchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private CustomMessageHeaderAccessor customMessageHeaderAccessor;

    /**
     * Liefert für den Spieler und das Match das Gamefield Object zurück
     *
     * @param match
     * @param player
     * @return Gamefield
     */
    public GameField getGameFieldForPlayer(Match match, Player player) {
        String gameField;
        //TODO Teste Welcher Spieler des Match mit dem Spieler übereinstimmt
        //TODO String konvertieren
        return null;
    }

    public Match createNewMatch(String id, Player player) {
        LOGGER.debug("--> createNewMatch");
        Match match = null;
        try {
            match = new Match(id);
            addNewPlayerToMatch(match,player);
        } catch (MatchNotFoundException | PlayerException ex) {
            LOGGER.error("Match konnte nicht gefunden werden. Exception:" + ex.getMessage());
        } 
        LOGGER.debug("<-- createNewMatch match=");
        return match;
    }

    /**
     * Get the winner of the match.
     *
     * @param matchId
     * @return Null if no winner.
     * @throws edu.sybit.codingcamp.battleship.exception.MatchNotFoundException
     */
    public Player isMatchWon(final String matchId) throws MatchNotFoundException {
        LOGGER.debug("--> isMatchWon: matchId=" + matchId);
        //TODO Herausfinden ob ein Spieler gewonnen hat und den gewinner zurückgeben
        LOGGER.debug("<-- isMatchWon: player=");
        return null;
    }

    /**
     * Count number of boxes in gamefield having given status.
     *
     * @param player
     * @param status
     * @return
     */
    protected int countOfStatus(final Player player, final String status) {
        int hits = 0;
        //TODO Gamefield anhand des Spielers finden
        //TODO Die Hits auf dem Gamfield zählen
        return hits;
    }

    public void addNewPlayerToMatch(Match match, Player player) throws MatchNotFoundException, PlayerException {
        LOGGER.debug("--> addNewPlayerToMatch match=" + match + " playerName=" + player.toString());
        playerService.update(player);
        match.addPlayer(player);
        this.updateMatch(match);
        LOGGER.debug("<-- addNewPlayerToMatch match=" + match + " playerName=" + player.toString());
      
    }
   
    /**
     * Get a match by given id.
     *
     * @param matchId
     * @return
     * @throws MatchNotFoundException
     */
    public Match getMatchById(String matchId) throws MatchNotFoundException {
        LOGGER.debug("--> getMatchById");
        Match match = null;
        //TODO Das Match anhand der Id finden
        //TODO Wenn kein Match gefunden wurde Exception
        //TODO Den aktuellen Spieler setzen
        return match;
    }

    public void verifyMatchSubscription(SessionSubscribeEvent event) {
        LOGGER.debug("--> verifyMatchSubscription");

        Principal user = event.getUser();
        String playerName = user.getName();

        StompHeaderAccessor accessor
            = CustomMessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
        final List<String> nativeHeader = accessor.getNativeHeader("matchId");
        if (nativeHeader == null || nativeHeader.isEmpty()) {
            throw new GeneralException("Missing matchID in Header!");
        }

        LOGGER.debug("<-- verifyMatchSubscription");
    }

    /**
     * Get the current player of match.
     *
     * @param match
     * @return
     */
    public Player getCurrentPlayer(Match match) {
        LOGGER.debug("--> getCurrentPlayer: match=" + match);
        Player currentPlayer = null;
        //TODO Aus dem Match den aktuellen Spieler finden
        //TODO Wenn kein Match gefunden wurde Exception
        LOGGER.debug("<-- getCurrentPlayer");
        return currentPlayer;
    }

    /**
     * set the current Player.
     *
     * @param match
     * @param currentPlayer
     */
    public void setCurrentPlayer(Match match, Integer currentPlayer) {
        LOGGER.debug("--> setCurrentPlayer");
        //TODO Exception falls der Spieler nicht 1 oder 2 ist
        //TODO Den aktuellen Spieer Setzen
        //TODO Das Match dem aktuellen Spieler senden
        //TODO Das Match abspeichern
        LOGGER.debug("--> setCurrentPlayer: " + currentPlayer);
    }

    private void sendCurrentPlayerMessage(Match match) {
        Player player1 = match.getPlayer1();
        Player player2 = match.getPlayer2();

        String playerId = "";

        //TODO Den aktuellen Spiel herausfinden
        switch (match.getCurrentPlayer()) {
            case 1: {
                playerId = player1.getPlayerId();
                break;
            }
            case 2: {
                playerId = player2.getPlayerId();
                break;
            }
            default: {
                LOGGER.error("Current Player not found!");
            }
        }

        //TODO enstprechend diesen Spielern eine Message senden
        Message messageForPlayer1 = new Message("matchInfo", "{\"currentPlayer\": \"" + playerId + "\"}");
        messageForPlayer1.setSendTo(player1);

        Message messageForPlayer2 = new Message("matchInfo", "{\"currentPlayer\": \"" + playerId + "\"}");
        messageForPlayer2.setSendTo(player2);

        messagingService.sendMessageToUser("/match", player1, messageForPlayer1);
        messagingService.sendMessageToUser("/match", player2, messageForPlayer2);
    }

    public void addOpponentPlayer(Match match, Player player) {
        //save and update
        if (match.getPlayer2() == null) {
            playerService.update(player);
            match.setPlayer2(player);
            updateMatch(match);
        }
    }

    /**
     * Get the opponment player of the Match.
     *
     * @param match
     * @return
     */
    protected Player getOpponentPlayer(Match match) {
        LOGGER.debug("--> getOpponentPlayer: match=" + match);
        Player opponentPlayer = null;
        //TODO von dem Match den opponent Player zurückgeben, falls kein Spieler gefunden Wird exeption
        LOGGER.debug("<-- getOpponentPlayer");
        return opponentPlayer;
    }


    /**
     * current Player has shot.
     *
     * @param match
     * @param boxShot
     */
    public void performShot(String currentPlayerId, Match match, Box boxShot) {
        LOGGER.debug("--> performShot: match=" + match + ", box=" + boxShot);

       Player current = match.getPlayerById(currentPlayerId);
       Player opponent = match.getOpponent(current);
       
       GameField currentGamefield = JsonConverter.convertStringToGamefield(current.getGamefield());
       GameField opponentGamefield = JsonConverter.convertStringToGamefield(opponent.getGamefield());
       
       Box fieldBox = opponentGamefield.getBox(boxShot.getId());
       if(fieldBox.getContent().getId() == null){
           fieldBox.setStatus(BoxStatus.FIELD_SHOT);
       }
       else{
       fieldBox.setStatus(BoxStatus.FIELD_HIT);
           isShipSunk(getFieldsOfShip(opponentGamefield, fieldBox));
           
       } 

        //TODO Spieler wechseln
        //TODO Den Schuss zähler erhöhen
        
        playerService.addGamefieldToPlayer(opponent, JsonConverter.convertGamefieldToJsonString(opponentGamefield));
        
        GameField gameFieldForCurrentPlayerPruned= pruneGameField(currentGamefield);
        GameField gameFieldForOpponentPlayerPruned= pruneGameField(opponentGamefield);
        
        Message messageForCurrentPlayer = buildGameFieldDataMessage(current, currentGamefield, gameFieldForCurrentPlayerPruned, false);
        Message messageForOpponentPlayer = buildGameFieldDataMessage(opponent, opponentGamefield, gameFieldForOpponentPlayerPruned, false);
        
        messagingService.sendMessageToUser("/match", current, messageForCurrentPlayer);
        messagingService.sendMessageToUser("/match", opponent, messageForOpponentPlayer);
        LOGGER.debug("--> performShot");
    }

    /**
     * Switch the current Player
     *
     * @param opponentPlayer
     * @param match
     */
    private void switchPlayer(Player opponentPlayer, Match match) {
        //TODO switch Player
    }


    private List<Box> getFieldsOfShip(GameField gameField, Box box) {
        String startBox = getStartBoxOfShip(box.getContent());

        char xValue = startBox.charAt(0);
        String boxYString = startBox.substring(1, startBox.length());
        int yValue = Integer.parseInt(boxYString);

        List<Box> boxesOfShip = new ArrayList<>();
        boxesOfShip.add(gameField.getBox(startBox));
        if (box.getContent() == null) {
            throw new IllegalArgumentException();
        }

        Ship hittenShip = box.getContent();
        
        if(hittenShip.getRotation() == 0){
            if(hittenShip.getShipType().equals("Submarine")){
                for(int i = 1; i<= 1; i++){
                    String stringX = String.valueOf(xValue);
                    String stringY = String.valueOf(yValue + i);
                    boxesOfShip.add(gameField.getBox(stringX + stringY));
                }
            }
            else if(hittenShip.getShipType().equals("Cruiser")){
                for(int i = 1; i<= 2; i++){
                    String stringX = String.valueOf(xValue);
                    String stringY = String.valueOf(yValue + i);
                    boxesOfShip.add(gameField.getBox(stringX + stringY));
                }
            }
            else if(hittenShip.getShipType().equals("Battleship")){
                for(int i = 1; i<= 3; i++){
                    String stringX = String.valueOf(xValue);
                    String stringY = String.valueOf(yValue + i);
                    boxesOfShip.add(gameField.getBox(stringX + stringY));
                }
            }
            else if(hittenShip.getShipType().equals("Carrier")){
                for(int i = 1; i<= 4; i++){
                    String stringX = String.valueOf(xValue);
                    String stringY = String.valueOf(yValue + i);
                    boxesOfShip.add(gameField.getBox(stringX + stringY));
                }
            }
            else {
            throw new IllegalArgumentException("Unbekanntes Schiff");   
            }
        
        }else if(hittenShip.getRotation() == 270){
            if(hittenShip.getShipType().equals("Submarine")){
                for(int i = 1; i<= 1; i++){
                    char characterX = (char)(xValue + i);
                    String stringY = String.valueOf(yValue);
                    boxesOfShip.add(gameField.getBox(String.valueOf(characterX) + stringY));
                }
            }
            else if(hittenShip.getShipType().equals("Cruiser")){
                for(int i = 1; i<= 2; i++){
                    char characterX = (char)(xValue + i);
                    String stringY = String.valueOf(yValue);
                    boxesOfShip.add(gameField.getBox(String.valueOf(characterX) + stringY));
                }
            }
            else if(hittenShip.getShipType().equals("Battleship")){
                for(int i = 1; i<= 3; i++){
                    char characterX = (char)(xValue + i);
                    String stringY = String.valueOf(yValue);
                    boxesOfShip.add(gameField.getBox(String.valueOf(characterX) + stringY));
                }
            }
            else if(hittenShip.getShipType().equals("Carrier")){
                for(int i = 1; i<= 4; i++){
                    char characterX = (char)(xValue + i);
                    String stringY = String.valueOf(yValue);
                    boxesOfShip.add(gameField.getBox(String.valueOf(characterX) + stringY));
                }
            }
            else {
            throw new IllegalArgumentException("Unbekanntes Schiff");   
            }
        }

        return boxesOfShip;
    }

    //iterates above all fields and checks weather every status is x (so ship is sunk) or not
    private boolean isShipSunk(List<Box> boxesOfShip) {
        for(int i = 0; i < boxesOfShip.size(); i++ ){
            if(!boxesOfShip.get(i).getStatus().equals(BoxStatus.FIELD_HIT)&& !boxesOfShip.get(i).equals(BoxStatus.FIELD_SUNK)){
               return false;
            }
        }
        setAllBoxesAsSunk(boxesOfShip);
        return true;
    }

    private String getStartBoxOfShip(Ship ship) {
        //TODO gibt für ein Schiff die Startbox zurück
        return "";
    }

    //replaces status in every box with v (sunk)
    private void setAllBoxesAsSunk(List<Box> boxes) {
        for(int i = 0; i< boxes.size(); i++){
            boxes.get(i).setStatus(BoxStatus.FIELD_SUNK);
        }
        //TODO Den Box status getroffen durch gesunken ersetzen
        LOGGER.debug("SCHIFF GESUNKEN !!!!!!");
    }

    private void increaseShotCounter(Match match) {
        //TODO Den counter erhöhen
    }

    /**
     * Update match in database.
     *
     * @param matchById
     * @return
     */
    private Match updateMatch(Match matchById) {
        return matchRepository.saveAndFlush(matchById);
    }

    /**
     * Number of used Boxes of all ships
     *
     * @return
     */
    private int getNumberOfBoxesOfAllShips() {
        int count = 30;
        return count;
    }

    public GameField pruneGameField(GameField gameField) {
        GameField prunedGameField = new GameField();
        List<Box> prunedBoxes = new ArrayList<>();
        for (Box box : gameField.getGameField()) {
            try {
                Box boxToPrune = (Box) box.clone();
                boxToPrune.setContent(null);
                prunedBoxes.add(boxToPrune);
            } catch (CloneNotSupportedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        prunedGameField.setGameField(prunedBoxes);
        return prunedGameField;
    }

    public Message buildGameFieldDataMessage(Player player, GameField ownGameField, GameField opponentGameField, boolean init) {
        String jsonStringForGamefieldData = mergeGamefields(ownGameField, opponentGameField);
        Message message;
        if (init) {
            message = new Message("gamefieldDataInit", jsonStringForGamefieldData);
        } else {
            message = new Message("gamefieldData", jsonStringForGamefieldData);

        }
        message.setSendTo(player);
        return message;
    }

    private String mergeGamefields(GameField ownGameField, GameField opponentGameField) {
        String ownGameFieldString = JsonConverter.convertGamefieldToJsonString(ownGameField);
        String opponentGameFieldString = JsonConverter.convertGamefieldToJsonString(opponentGameField);
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"ownGameField\"");
        sb.append(":");
        sb.append(ownGameFieldString);
        sb.append(",");
        sb.append("\"opponentGameField\"");
        sb.append(":");
        sb.append(opponentGameFieldString);
        sb.append("}");
        return sb.toString();
    }
}

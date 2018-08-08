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
import static edu.sybit.codingcamp.battleship.objects.jsonObjects.BoxStatus.FIELD_SUNK;
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
import java.util.Optional;
import java.util.Timer;
import java.util.logging.Level;

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
        if(match.getPlayer1().equals(player)){
            String player1 = player.getGamefield();
            JsonConverter.convertStringToGamefield(player1);
            GameField player1GF = JsonConverter.convertStringToGamefield(player1);
            System.out.println("Spieler 1 Gefunden");
            return player1GF;
        }else if(match.getPlayer2().equals(player)){
            String player2 = player.getGamefield();
            GameField player2GF = JsonConverter.convertStringToGamefield(player2);
            System.out.println("Spieler 2 Gefunden");
            return player2GF;
        }else{
            System.out.println("Error Spieler Konnte nicht verifiziert werden");
            return null;
        }
    }
    
    public Timer resetTimer(Timer timer,Match currentMatch) {
        timer.cancel();
        timer.purge();
        
        return createNewTimer(currentMatch);
    }
    
    public Timer createNewTimer(Match currentMatch){
        Timer timer= new Timer();
        GameOverTask gameOverTask = new GameOverTask();
        gameOverTask.setMatch(currentMatch);
        gameOverTask.setMessagingService(messagingService);
        timer.schedule(gameOverTask,60000);
        return timer;
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
        Match match = getMatchById(matchId);
        Player player1 = match.getPlayer1();
        Player player2 = match.getPlayer2();
        int sunkBoatsP1 = countOfStatus(player1, FIELD_SUNK);
          int sunkBoatsP2 = countOfStatus(player2, FIELD_SUNK);
        if(sunkBoatsP1 == 30){
            return player2;
        }else if(sunkBoatsP2 == 30){
            return player1;
        } else {
        return null;
    }
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
        String playerGFString = player.getGamefield();
        GameField playerGF = JsonConverter.convertStringToGamefield(playerGFString);
        for (Box box : playerGF.getGameField()) {
            if(box.getStatus().equals(status)){
                hits++;
            }
        }      
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
        Optional<Match> optionalMatch = matchRepository.findById(matchId);
        if(optionalMatch.isPresent()){
            match =  optionalMatch.get();
                    
            
        }else{
            throw new MatchNotFoundException("Match nicht gefunden");
        }
        
        if(match.getPlayer1()!=null&& match.getPlayer2()!=null){
            match.setCurrentPlayer(2);
        }else if((match.getPlayer1()==null||match.getPlayer2()==null)&&(match.getPlayer1()!=null ||match.getPlayer2()==null)){
            match.setCurrentPlayer(1);
        }

        
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
        Player currentPlayer;

        if (match.getCurrentPlayer() == 1) {
            currentPlayer = match.getPlayer1();
        } else if (match.getCurrentPlayer() == 2) {
            currentPlayer = match.getPlayer2();
        } else if (match.getCurrentPlayer() == 0) {
            currentPlayer = match.getPlayer1();
        } else {
            throw new GeneralException("match has no defined currentPlayer!");
        }

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

        if(currentPlayer < 1 || currentPlayer > 2) {
            throw new GeneralException("currentPlayer must be '1' or '2'!");
        }
        match.setCurrentPlayer(currentPlayer);
        sendCurrentPlayerMessage(match);
        matchRepository.saveAndFlush(match);
        LOGGER.debug("--> setCurrentPlayer: " + currentPlayer);
    }

     private void sendCurrentPlayerMessage(Match match){
        Player player1 = match.getPlayer1();
        Player player2 = match.getPlayer2();

        String playerId = "";
        String playerName = "";

        switch(match.getCurrentPlayer()){
            case 1:{
                playerId = player1.getPlayerId();
                playerName = player1.getPlayerName();
                break;
            }
            case 2: {
                playerId = player2.getPlayerId();
                playerName = player2.getPlayerName();
                break;
            }
            default:{
                LOGGER.error("Current Player not found!");
            }
        }

        Message messageForPlayer1 = new Message("matchInfo", "{\"currentPlayer\": \""+playerId+"\", \"currentPlayerName\": \""+playerName+"\"}");
        messageForPlayer1.setSendTo(player1);

        Message messageForPlayer2 = new Message("matchInfo", "{\"currentPlayer\": \""+playerId+"\", \"currentPlayerName\": \""+playerName+"\"}");
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
        Player opponentPlayer;

        if (match.getCurrentPlayer() == 2) {
            opponentPlayer = match.getPlayer1();
        } else if (match.getCurrentPlayer() == 1) {
            opponentPlayer = match.getPlayer2();
        } else {
            throw new GeneralException("match has no defined opponentPlayer!");
        }

        LOGGER.debug("<-- getOpponentPlayer");
        return opponentPlayer;
    }


    /**
     * current Player has shot.
     *
     * @param currentPlayerId
     * @param match
     * @param boxShot
     * @param showShips
     * @return winnerPlayer
     */
    public Player performShot(String currentPlayerId, Match match, Box boxShot, boolean showShips) {
        LOGGER.debug("--> performShot: match=" + match + ", box=" + boxShot);

        Player currentPlayer = match.getPlayerById(currentPlayerId);
        Player opponentPlayer =  match.getOpponent(currentPlayer);

        GameField opponentGameField = JsonConverter.convertStringToGamefield(opponentPlayer.getGamefield());
        GameField currentGameField = JsonConverter.convertStringToGamefield(currentPlayer.getGamefield());

        Box fieldBox = opponentGameField.getBox(boxShot.getId());
        fieldBox.setSound(true);
        System.err.println("Setze sound auf true: " + fieldBox);
        System.err.println(boxShot);
        if (fieldBox.getContent().getId() != null) {

            LOGGER.info("Treffer! -> " + fieldBox.getContent());
            fieldBox.setStatus(BoxStatus.FIELD_HIT);
            updateOpponentPlayerAfterShot(opponentPlayer, opponentGameField);
            sendUpdateMessage(currentPlayer,opponentPlayer,currentGameField,opponentGameField, showShips);

            if(isShipSunk(getFieldsOfShip(opponentGameField, fieldBox))){
                updateOpponentPlayerAfterShot(opponentPlayer, opponentGameField);
                sendUpdateMessage(currentPlayer,opponentPlayer,currentGameField,opponentGameField, showShips);
            }

        } else {
            fieldBox.setStatus(BoxStatus.FIELD_SHOT);
            LOGGER.info("daneben :/");

            updateOpponentPlayerAfterShot(opponentPlayer, opponentGameField);
            sendUpdateMessage(currentPlayer,opponentPlayer,currentGameField,opponentGameField, showShips);
        }


        //finally increase shot counter:
        increaseShotCounter(match);      
         
        String matchID = match.getMatchId();
        Player winnerPlayer = new Player();
        try {
            winnerPlayer = isMatchWon(matchID);
        } catch (MatchNotFoundException ex) {
            LOGGER.error("Error: " + ex.getMessage());
        }
        
        switchPlayer(opponentPlayer, match);

        LOGGER.debug("--> performShot");
        fieldBox.setSound(false);
        System.err.println("Setze sound auf false: " + fieldBox);
        return winnerPlayer;
        
    }

    private void updateOpponentPlayerAfterShot(Player opponentPlayer, GameField opponentGameField){
        playerService.addGamefieldToPlayer(opponentPlayer, JsonConverter.convertGamefieldToJsonString(opponentGameField));
        playerService.update(opponentPlayer);
    }

    private void sendUpdateMessage (Player currentPlayer, Player opponentPlayer, GameField currentGameField, GameField opponentGameField, boolean showShips){

        GameField gameFieldForCurrentPlayerPruned = pruneGameField(currentGameField);
        GameField gameFieldForOpponentPlayerPruned;
        
        if(showShips){
            System.out.println("Cheat >show ships<");
            gameFieldForOpponentPlayerPruned = opponentGameField;
        } else {
            gameFieldForOpponentPlayerPruned = pruneGameField(opponentGameField);
        }
        Message messageForCurrentPlayer = buildGameFieldDataMessage(currentPlayer, currentGameField, gameFieldForOpponentPlayerPruned, false);
        Message messageForOpponentPlayer = buildGameFieldDataMessage(opponentPlayer, opponentGameField, gameFieldForCurrentPlayerPruned, false);

        messagingService.sendMessageToUser("/match", currentPlayer, messageForCurrentPlayer);
        messagingService.sendMessageToUser("/match", opponentPlayer, messageForOpponentPlayer);
    }


    /**
     * Switch the current Player
     *
     * @param opponentPlayer
     * @param match
     */
    private void switchPlayer(Player opponentPlayer, Match match) {
        if(opponentPlayer.getPlayerId().equals(match.getPlayer1().getPlayerId())){
            setCurrentPlayer(match, 1);
        } else if (opponentPlayer.getPlayerId().equals(match.getPlayer2().getPlayerId())){
            setCurrentPlayer(match, 2);
        } else {
            LOGGER.error("Player not found");
        }
    }


     private List<Box> getFieldsOfShip(GameField gameField, Box box){
        String startBox = getStartBoxOfShip(box.getContent());

        char xValue = startBox.charAt(0);
        String boxYString = startBox.substring(1, startBox.length());
        int yValue = Integer.parseInt(boxYString);

        List<Box> boxesOfShip = new ArrayList<>();
        boxesOfShip.add(gameField.getBox(startBox));
        if(box.getContent() == null){
            throw new IllegalArgumentException();
        }

        Ship hittenShip = box.getContent();
        if(hittenShip.getRotation() == 0){
            if(hittenShip.getShipType().equals("Submarine")){
                for(int i = 1; i <= 1; i++){
                    String stringX = String.valueOf(xValue);
                    String stringY = String.valueOf(yValue+i);
                    boxesOfShip.add(gameField.getBox(stringX+stringY));
                }
            } else if(hittenShip.getShipType().equals("Cruiser")){
                for(int i = 1; i <= 2; i++){
                    String stringX = String.valueOf(xValue);
                    String stringY = String.valueOf(yValue+i);
                    boxesOfShip.add(gameField.getBox(stringX+stringY));
                }
            } else if(hittenShip.getShipType().equals("Battleship")){
                for(int i = 1; i <= 3; i++){
                    String stringX = String.valueOf(xValue);
                    String stringY = String.valueOf(yValue+i);
                    boxesOfShip.add(gameField.getBox(stringX+stringY));
                }
            } else if(hittenShip.getShipType().equals("Carrier")){
                for(int i = 1; i <= 4; i++){
                    String stringX = String.valueOf(xValue);
                    String stringY = String.valueOf(yValue+i);
                    boxesOfShip.add(gameField.getBox(stringX+stringY));
                }
            } else {
                throw new IllegalArgumentException("unknown ship type");
            }
        } else if (hittenShip.getRotation() == 270){
            if(hittenShip.getShipType().equals("Submarine")){
                for(int i = 1; i <= 1; i++){
                    char character = (char) (xValue+i);
                    String stringY = String.valueOf(yValue);
                    boxesOfShip.add(gameField.getBox(String.valueOf(character)+stringY));
                }
            } else if(hittenShip.getShipType().equals("Cruiser")){
                for(int i = 1; i <= 2; i++){
                    char character = (char) (xValue+i);
                    String stringY = String.valueOf(yValue);
                    boxesOfShip.add(gameField.getBox(String.valueOf(character)+stringY));
                }
            } else if(hittenShip.getShipType().equals("Battleship")){
                for(int i = 1; i <= 3; i++){
                    char character = (char) (xValue+i);
                    String stringY = String.valueOf(yValue);
                    boxesOfShip.add(gameField.getBox(String.valueOf(character)+stringY));
                }
            } else if(hittenShip.getShipType().equals("Carrier")){
                for(int i = 1; i <= 4; i++){
                    char character = (char) (xValue+i);
                    String stringY = String.valueOf(yValue);
                    boxesOfShip.add(gameField.getBox(String.valueOf(character)+stringY));
                }
            } else {
                throw new IllegalArgumentException("unknown ship type");
            }
        }

        return boxesOfShip;
    }

    private boolean isShipSunk(List<Box> boxesOfShip){
        for(Box box : boxesOfShip){
            if(!box.getStatus().equals(BoxStatus.FIELD_HIT) && !box.getStatus().equals(BoxStatus.FIELD_SUNK)){
                return false;
            }
        }
        
        setAllBoxesAsSunk(boxesOfShip);

        return true;
    }
    
    private String getStartBoxOfShip(Ship ship){

        int xChar = (((ship.getPosX()-160)/40)+1)+64;
        String boxIdX = Character.toString((char) xChar);
        String boxIdY = String.valueOf(ship.getPosY()/40+1);

        return boxIdX+boxIdY;
    }

    private void setAllBoxesAsSunk(List<Box> boxes){
        for (Box box : boxes) {
            box.setStatus(BoxStatus.FIELD_SUNK);
        }
        LOGGER.debug("SCHIFF GESUNKEN !!!!!!");
    }


     void increaseShotCounter(Match match) {

        if (match.getShotCount() == null) {
            match.setShotCount(1);
        } else {
            match.setShotCount(match.getShotCount() + 1);
        }
        updateMatch(match);
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

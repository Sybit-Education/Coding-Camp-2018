/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.objects;

import edu.sybit.codingcamp.battleship.exception.GeneralException;
import edu.sybit.codingcamp.battleship.exception.PlayerException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author spp
 */
@Entity
@Table(name = "Match")
public class Match implements Serializable, Cloneable {

    /**
     * GUID of Match.
     */
    @Id
    @Column(name = "match_id")
    private String matchId;
    @Column(name = "shot_count")
    private Integer shotCount;
    @OneToOne(targetEntity = Player.class, fetch = FetchType.EAGER)
    private Player player1;
    @OneToOne(targetEntity = Player.class, fetch = FetchType.EAGER)
    private Player player2;
    @Column(name = "current_player")
    private Integer currentPlayer;
    @Column(name = "winner_player")
    private String winnerPlayer;
    @Column(name = "last_mod_Date")
    private Date lastModificationDate;

    public Match() {
    }

    public Match(String uuid) {
        this.matchId = uuid;
    }

    public void addPlayer(Player user) throws PlayerException {
        if (getPlayer1() == null && getPlayer2() == null) {
            setPlayer1(user);
        } else if (getPlayer1() == null) {
            setPlayer1(user);
        } else if (getPlayer2() == null) {
            setPlayer2(user);
        } else {
            throw new PlayerException("Already 2 Players connected!");
        }
    }

    public Player getOpponent(Player player) {
        if (player.getPlayerId().equals(player1.getPlayerId())) {
            return player2;
        }
        return player1;
    }

    public Player getPlayerById(String playerId){
        if (playerId.equals(player1.getPlayerId())) {
            return player1;
        }else if(playerId.equals(player2.getPlayerId())){
            return player2;
        }
        throw new GeneralException("Unknown PlayerID!");
    }

    public String getMatchId() {
        return matchId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Integer getShotCount() {
        return shotCount;
    }

    public void setShotCount(Integer shotCount) {
        this.shotCount = shotCount;
    }


    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Integer getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * currentPlayer 1 = Spieler 1
     * currentPlayer 2 = Spieler 2
     *
     * @param currentPlayer
     */
    public void setCurrentPlayer(Integer currentPlayer) {
        if (currentPlayer < 0 || currentPlayer > 2) {
            throw new GeneralException("currentPlayer must be '1' or '2'");
        }
        this.currentPlayer = currentPlayer;
    }

    public String getWinnerPlayer() {
        return winnerPlayer;
    }

    public void setWinnerPlayer(String winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    @Override
    public String toString() {
        return "Match{" + "matchId=" + matchId + ", currentPlayer=" + currentPlayer + ", winnerPlayer=" + winnerPlayer + ", lastModificationDate=" + lastModificationDate + '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}

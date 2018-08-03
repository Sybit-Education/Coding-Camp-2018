/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Player")
public class Player implements Serializable {
    @Id
    @Column(name = "player_id")
    private String playerId;
    @Column(name = "player_name")
    private String playerName;
    @Column(name = "game_field", length = Integer.MAX_VALUE)
    private String gamefield;

    @OneToOne(targetEntity = Match.class)
    private Match match;

    public Player() {
    }

    public Player(String playerId) {
        this.playerId = playerId;
        this.gamefield = "";
    }

    public String getGamefield() {
        return gamefield;
    }

    public void setGamefield(String gamefield) {
        this.gamefield = gamefield;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return "Player{playerId=" + playerId + "}";
    }
}

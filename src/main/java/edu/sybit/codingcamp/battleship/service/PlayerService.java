/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.service;

import edu.sybit.codingcamp.battleship.objects.Player;
import edu.sybit.codingcamp.battleship.repository.PlayerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to manage operations on Player.
 * @author ssr
 */
@Service
public class PlayerService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);
    
    @Autowired
    private PlayerRepository playerRepository;

    public void addGamefieldToPlayer(Player player, String gamefield) {
        LOGGER.debug("--> addGamefieldToPlayer");
        player.setGamefield(gamefield);
        update(player);
        LOGGER.debug("<-- addGamefieldToPlayer");
    }
    
    /**
     * Get Player by given id.
     * 
     * @param id
     * @return 
     */
    public Player getPlayer(String id) {
        LOGGER.debug("--> getPlayer id=" + id);
        //TODO Mithilfe der Id den Spieler finden
        LOGGER.debug("<-- getPlayer player=");
        return null;
    }

    /**
     * Update given player in database.
     * 
     * @param player 
     */
    void update(Player player) {
        LOGGER.debug("--> update");
        //TODO Das Spieler Objekt updaten
        LOGGER.debug("--> update");
    }
}

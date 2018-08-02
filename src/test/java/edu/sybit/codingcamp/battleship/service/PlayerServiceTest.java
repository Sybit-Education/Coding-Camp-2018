/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.service;

import edu.sybit.codingcamp.battleship.objects.Player;
import edu.sybit.codingcamp.battleship.repository.PlayerRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;


public class PlayerServiceTest {

    @Mock
    private Player mockedPlayer;

    @Mock
    private PlayerRepository mockedPlayerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addGamefieldToPlayerTest(){
        String gamefield = "someGamefield";
        playerService.addGamefieldToPlayer(mockedPlayer, gamefield);

        verify(mockedPlayer).setGamefield(gamefield);
        verify(mockedPlayerRepository).saveAndFlush(mockedPlayer);
    }
}

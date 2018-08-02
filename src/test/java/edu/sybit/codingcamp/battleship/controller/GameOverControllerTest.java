/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sybit.codingcamp.battleship.controller;

import edu.sybit.codingcamp.battleship.objects.Match;
import edu.sybit.codingcamp.battleship.objects.Player;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.GameField;
import edu.sybit.codingcamp.battleship.service.JsonConverter;
import edu.sybit.codingcamp.battleship.service.MatchService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author Schulungsnb
 */
public class GameOverControllerTest {
        
    private MockMvc mockMvc;
    
    @Mock
    private MatchService mockedMatchService;

    @InjectMocks
    private GameOverController gameOverController;

    @Before
    public void init() {
        initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(gameOverController)
            .build();
    }

    @Test
    public void gameOver() throws Exception{
        String matchID = "test";
        String playerID = "Player1";
        Player player1 = new Player(playerID);
        player1.setPlayerId(playerID);
        when(mockedMatchService.isMatchWon(matchID)).thenReturn(player1);
        MockHttpServletRequestBuilder request = get("/playermatch/"+matchID+"/over");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(model().attribute("winner", playerID));
    }
    
}

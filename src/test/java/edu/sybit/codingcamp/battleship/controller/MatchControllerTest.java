/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.controller;

import edu.sybit.codingcamp.battleship.objects.Match;
import edu.sybit.codingcamp.battleship.objects.Player;
import edu.sybit.codingcamp.battleship.service.MatchService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MatchControllerTest {
    private MockMvc mockMvc;

    @Mock
    private MatchService matchServiceMock;

    @InjectMocks
    private MatchController matchController;

    @Before
    public void init() {
       initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(matchController)
            .build();
    }

    @Test
    public void matchTest() throws Exception {
        String matchId = UUID.randomUUID().toString();
        MockHttpServletRequestBuilder request = get("/match/" + matchId);
        MvcResult mvcResult = mockMvc.perform(request)
            .andExpect(status().isOk())
            .andReturn();

        assertThat(mvcResult.getResponse().getCookie("matchId").getValue(), is(matchId));
    }

    @Test
    public void newMatchTest() throws Exception {
        MockHttpServletRequestBuilder request = get("/match/newmatch");
        MvcResult mvcResult = mockMvc.perform(request)
            .andExpect(status().isOk())
            .andReturn();
        
        String id = mvcResult.getResponse().getContentAsString();
                
        Match newMatch = new Match(id);
        Player player = new Player();
        when(matchServiceMock.createNewMatch(id, player)).thenReturn(newMatch);

        assertThat(id, is(newMatch.getMatchId()));
    }
}

/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.service;

import edu.sybit.codingcamp.battleship.exception.MatchNotFoundException;
import edu.sybit.codingcamp.battleship.objects.Match;
import edu.sybit.codingcamp.battleship.objects.Player;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.Ship;
import edu.sybit.codingcamp.battleship.repository.MatchRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class MatchServiceTest {


    @Mock
    private Match mockedMatch;

    @Mock
    private MatchRepository mockedMatchRepository;

    @Mock
    private PlayerService mockedPlayerService;

    @InjectMocks
    private MatchService matchService;

    @Before
    public void init() {
        initMocks(this);
    }

    private List<Match> generateSampleMatches() {
        List<Match> matchList = new ArrayList<>();
        Match match1 = new Match("match1Id");
        Match match2 = new Match("match2Id");
        matchList.add(match1);
        matchList.add(match2);
        return matchList;
    }

    private List<Match> generateMockMatches() {
        List<Match> matchList = new ArrayList<>();
        matchList.add(mockedMatch);
        return matchList;
    }

    @Test
    public void addNewPlayerToMatchTest() throws Exception {
        List<Match> matchList = generateSampleMatches();
        String sampleMatchId = matchList.get(0).getMatchId();
        String playerId = "playerId";
        when(mockedMatch.getMatchId()).thenReturn(sampleMatchId);
        when(mockedMatchRepository.findById(sampleMatchId)).thenReturn(Optional.of(matchList.get(0)));
        matchService.addNewPlayerToMatch(new Match(sampleMatchId), new Player(playerId));
        verify(mockedMatchRepository).saveAndFlush(any(Match.class));
    }

    @Test(expected = Exception.class)
    public void addNewPlayerToMatch_throwsException() throws Exception {
        String mockedMatchId = "matchId";
        String playerId = "playerId";
        when(mockedMatch.getMatchId()).thenReturn(mockedMatchId);
        doThrow(new Exception()).when(mockedMatch).addPlayer(any(Player.class));
        matchService.addNewPlayerToMatch(new Match(mockedMatchId), new Player(playerId));
    }

    @Test
    public void getMatchByIdTest_validMatch() throws Exception {
        List<Match> matchList = generateSampleMatches();
        String sampleMatchId = matchList.get(0).getMatchId();
        when(mockedMatchRepository.findById(sampleMatchId)).thenReturn(Optional.of(matchList.get(0)));
        Match matchById = matchService.getMatchById(sampleMatchId);
        assertThat(matchById, is(matchList.get(0)));
    }

    @Test(expected = MatchNotFoundException.class)
    public void getMatchByIdTest_throwsException() throws Exception {
        matchService.getMatchById("some invalid Id");
    }

    /*
    @Test
    public void performShotTest(){



        Match match = new Match();
        Message message = matchService.buildGameFieldDataMessage(new Player(), new GameField(), new GameField());
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setGamefield(message.getMessageContent());
        match.setPlayer1(player1);
        match.setPlayer2(player2);

        Box boxA1 = new Box();
        Box boxB1 = new Box();
        boxA1.setContent(getSubmarineA1());
        boxB1.setContent(getSubmarineA1());



        matchService.performShot(match, boxA1);

    }
    */

    //posX/Y = fieldNr * 40 (e.g A1 = x:0 y:0 , A1 = x:0, y:1*40, C9 = x:2*40, y=8*40)
    public Ship getSubmarineA1(){
        Ship ship = new Ship();
        ship.setRotation(0);
        ship.setPosX(0);
        ship.setPosY(0);
        ship.setShipType("Submarine");

        return ship;
    }

}

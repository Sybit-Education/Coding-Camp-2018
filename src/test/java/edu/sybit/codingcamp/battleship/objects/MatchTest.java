/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.objects;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;

public class MatchTest {

    private Match match;

    private Player sampleUser1;
    private Player sampleUser2;

    @Before
    public void init(){
        this.match = new Match();
        this.sampleUser1 = new Player("p1");
        this.sampleUser2 = new Player("p2");
    }

    @Ignore
    @Test
    public void constructorTest() {
        assertThat(match, is(notNullValue()));
    }

    @Ignore
    @Test
    public void getterSetterTest() {
        assertThat(match.getPlayer1(),is(nullValue()));
        assertThat(match.getPlayer2(),is(nullValue()));

        match.setPlayer1(sampleUser1);
        match.setPlayer2(sampleUser1);
        match.setCurrentPlayer(1);
        Date lastModDate = new Date();
        match.setLastModificationDate(lastModDate);

        assertThat(match.getPlayer1(),is(notNullValue()));
        assertThat(match.getPlayer2(),is(notNullValue()));
        assertThat(match.getCurrentPlayer(),is(1));
        assertThat(match.getLastModificationDate(),is(lastModDate));
    }

    @Ignore
    @Test
    public void addPlayerTest_bothNull() throws Exception {
        match.addPlayer(sampleUser1);
        assertThat(match.getPlayer1(), is(sampleUser1));
        assertThat(match.getPlayer2(), is(nullValue()));
    }

    @Ignore
    @Test
    public void addPlayerTest_OnlyPlayer2Null() throws Exception {
        match.addPlayer(sampleUser1);
        match.addPlayer(sampleUser2);
        assertThat(match.getPlayer1(), is(sampleUser1));
        assertThat(match.getPlayer2(), is(sampleUser2));
    }

    @Ignore
    @Test(expected = Exception.class)
    public void addPlayerTest_BothSet() throws Exception {
        match.addPlayer(sampleUser1);
        match.addPlayer(sampleUser2);
        match.addPlayer(new Player("someOtherUser"));
    }

    @Ignore
    @Test
    public void getOpponent_player1() throws Exception {
        match.addPlayer(sampleUser1);
        match.addPlayer(sampleUser2);
        Player opponent = match.getOpponent(sampleUser1);
        assertThat(opponent, is(sampleUser2));
    }

    @Ignore
    @Test
    public void getOpponent_player2() throws Exception {
        match.addPlayer(sampleUser1);
        match.addPlayer(sampleUser2);
        Player opponent = match.getOpponent(sampleUser2);
        assertThat(opponent, is(sampleUser1));
    }
}

/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.objects;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;

public class PlayerTest {

    private final String username = "Username";
    private Player user;

    @Before
    public void init(){
        this.user = new Player();
    }

    @Test
    public void constructorTest() {
        this.user = new Player(username);
        assertThat(user.getPlayerId(), is(username));
    }

    @Test
    public void getterSetterTest() {
        String newName = "newUsername";
        String gamefield = "GamfieldString";
        user.setPlayerId(newName);
        user.setGamefield(gamefield);
        assertThat(user.getPlayerId(), is(newName));
        assertThat(user.getGamefield(), is(gamefield));
    }
}

/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.objects.jsonObjects;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;

public class GameFieldTest {

    private GameField gameField;

    @Before
    public void init(){
        this.gameField = new GameField();
    }

    @Ignore
    @Test
    public void getterSetterTest(){
        List<Box> boxes = new ArrayList<>();
        boxes.add(new Box());
        gameField.setGameField(boxes);

        assertThat(gameField.getGameField(), is(boxes));
    }
}

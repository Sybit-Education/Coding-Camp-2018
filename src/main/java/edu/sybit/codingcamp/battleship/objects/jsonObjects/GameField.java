/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.objects.jsonObjects;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 */
public class GameField implements Serializable {

    private List<Box> gameField;

    public List<Box> getGameField() {
        return gameField;
    }

    public void setGameField(List<Box> gameField) {
        this.gameField = gameField;
    }

    /**
     * Get the box by given name eg. "A1".
     *
     * @param name
     * @return
     */
    public Box getBox(String name) {
        for (Box box : gameField) {
            if (box.getId().equals(name)) {
                return box;
            }
        }
        return null;
    }
}

/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */
package edu.sybit.codingcamp.battleship.objects.jsonObjects;

import java.io.Serializable;

/**
 * Representation of the Ship.
 *
 * @author ssr
 */
public class Ship implements Serializable {

    private String id;

    //Submarine, Cruiser, Battleship or Carrier
    private String shipType;

    private Integer rotation;

    private Integer posX;

    private Integer posY;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    @Override
    public String toString() {
        return "Ship{" + "id=" + id + ", shipType=" + shipType + ", rotation=" + rotation + ", posX=" + posX + ",posY=" + posY + '}';
    }
}

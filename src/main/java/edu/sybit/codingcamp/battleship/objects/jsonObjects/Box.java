/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.objects.jsonObjects;

import java.io.Serializable;

public class Box implements Serializable, Cloneable {
    private String id;
    private Ship content;
    private String status;
    private Integer posX;
    private Integer posY;
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ship getContent() {
        return content;
    }

    public void setContent(Ship content) {
        this.content = content;
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
        return "Box{" + "id=" + id + ", content=" + content + ", status=" + status + ", posX=" + posX + ", posY=" + posY +"}";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

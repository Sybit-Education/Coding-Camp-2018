/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */
package edu.sybit.codingcamp.battleship.objects.jsonObjects;

/**
 *
 * @author ssr
 */
public class BoxStatus {
    
    //marker of Box if ship was hit.
    public static final String FIELD_HIT = "x";
    //marker of box if ship is killed completely.
    public static final String FIELD_SUNK = "v";
    //marker where shot but nothing targeted.
    public static final String FIELD_SHOT = "o";
}

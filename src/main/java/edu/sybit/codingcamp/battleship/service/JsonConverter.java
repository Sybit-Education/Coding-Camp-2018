/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.service;

import com.google.gson.Gson;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.Box;
import edu.sybit.codingcamp.battleship.objects.jsonObjects.GameField;
import org.springframework.stereotype.Service;

/**
 * Converter to convert between JSON and Java objects.
 * 
 */
@Service
public class JsonConverter {

    /**
     * Convert JSON String to Java object GameField.
     * @param gamefieldString
     * @return 
     */
    public static GameField convertStringToGamefield(String gamefieldString) {
        Gson gson = new Gson();
        return gson.fromJson(gamefieldString, GameField.class);
    }

    /**
     * Convert given object GameField to JSON String.
     * 
     * @param gamefield
     * @return 
     */
    public static String convertGamefieldToJsonString(GameField gamefield) {
        Gson gson = new Gson();
        return gson.toJson(gamefield);
    }

    /**
     * Convert JSON String to Java object Box.
     * 
     * @param boxString
     * @return 
     */
    public static Box convertStringToBox(String boxString) {
        Gson gson = new Gson();
        return gson.fromJson(boxString, Box.class);
    }
}

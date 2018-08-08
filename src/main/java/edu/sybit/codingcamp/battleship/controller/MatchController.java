/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.controller;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MatchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchController.class);

    @RequestMapping(value = "match/{matchId}")
    public String match(@PathVariable String matchId, HttpServletResponse response) {
        LOGGER.debug("--> match: matchId=" + matchId);
        response.addCookie(new Cookie("matchId", matchId));
        LOGGER.debug("<-- match");
        return "match";
    }

    @GetMapping("playermatch/{matchId}")
    public String playermatch(@PathVariable String matchId, HttpServletResponse response){
        LOGGER.debug("--> playermatch MatchId: " + matchId);

        response.addCookie(new Cookie("matchId", matchId));
        
        LOGGER.debug("<-- playermatch MatchId: " + matchId);
        return "playerMatch";
    }

    @GetMapping("match/newmatch")
    @ResponseBody
    public String newMatch() {
        LOGGER.debug("--> new Match");
        
        String id = UUID.randomUUID().toString();
        
        LOGGER.debug("<-- new Match Id: " + id);
        return id;
    }
}
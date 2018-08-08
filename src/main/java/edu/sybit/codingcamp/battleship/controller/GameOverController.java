/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.controller;

import edu.sybit.codingcamp.battleship.exception.MatchNotFoundException;
import edu.sybit.codingcamp.battleship.objects.Match;
import edu.sybit.codingcamp.battleship.objects.Player;
import edu.sybit.codingcamp.battleship.service.MatchService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller wenn das Spiel beendet wurde: Wer hat gewonnen?
 * @author ssr
 */
@Controller
public class GameOverController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameOverController.class);
    
    @Autowired
    protected MatchService matchService;
    
    /**
     * Show game over page with winner.
     * @param matchId
     * @param response
     * @param model
     * @return 
     */
    @RequestMapping(value = "/playermatch/{matchId}/over")
    public ModelAndView gameOver(@PathVariable String matchId, HttpServletResponse response, ModelMap model) {
        LOGGER.debug("--> gameOver  matchId: " + matchId);
        
        response.addCookie(new Cookie("matchId", matchId));
        
        try {
            Player winner = new Player();
            Match match= matchService.getMatchById(matchId);
            Player player1 = match.getPlayer1();
            Player player2 = match.getPlayer2();
            if(match.getWinnerPlayer().equals(player2.getPlayerId())){
               match.setWinnerPlayer(player2.getPlayerId());
            } else if(match.getWinnerPlayer().equals(player1.getPlayerId())){
               match.setWinnerPlayer(player1.getPlayerId());
            } else {
                winner = matchService.isMatchWon(matchId); 
            }
            LOGGER.debug("<<<Gewinner ist "+winner+" >>>");
            if(winner != null) {
                String winnerName = winner.getPlayerName();
                model.addAttribute("winner", winnerName);
                Player looser = match.getOpponent(winner);
                String looserName = looser.getPlayerName();
                model.addAttribute("looser", looserName);
            } else {
                Player currentPlayer = matchService.getCurrentPlayer(match);
                model.addAttribute("winner", currentPlayer.getPlayerName());
                Player looser = match.getOpponent(currentPlayer);
                String looserName = looser.getPlayerName();
                model.addAttribute("looser", looserName);
            }
            
            model.addAttribute("shotsCount", match.getShotCount());
        
        } catch (MatchNotFoundException ex) {
            LOGGER.error(ex.getMessage(), ex);
            //on error go to startpage.
            return new ModelAndView("redirect:/", model);
        }
              
        LOGGER.debug("<-- gameOver");
        return new ModelAndView("game-over", model);
    }
}

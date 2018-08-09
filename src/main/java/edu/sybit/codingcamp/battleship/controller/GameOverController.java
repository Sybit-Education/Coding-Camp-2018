/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.controller;

import edu.sybit.codingcamp.battleship.exception.MatchNotFoundException;
import edu.sybit.codingcamp.battleship.objects.Match;
import edu.sybit.codingcamp.battleship.objects.Player;
import edu.sybit.codingcamp.battleship.service.MatchService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
     * @param request
     * @param response
     * @param model
     * @return 
     */
    @RequestMapping(value = "/playermatch/{matchId}/over")
    public ModelAndView gameOver(@PathVariable String matchId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        LOGGER.debug("--> gameOver  matchId: " + matchId);
        
        response.addCookie(new Cookie("matchId", matchId));
        
        try {
            Player winner = matchService.isMatchWon(matchId);
            
            Match match= matchService.getMatchById(matchId);
            
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
            return new ModelAndView("redirect:/", request.getContextPath(), model);
        }
              
        LOGGER.debug("<-- gameOver");
        return new ModelAndView("game-over", model);
    }
}

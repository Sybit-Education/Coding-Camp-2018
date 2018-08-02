/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sybit.codingcamp.battleship.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Schulungsnb
 */
@Controller
public class creditsController{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(creditsController.class);
    
    @RequestMapping(value = "/credits")
    public String credits() {
        LOGGER.debug("--> credits");
        
        //dieser controller zeigt einfach die Datei index.jsp an. Deshalb "return index".
        
        LOGGER.debug("<-- credits");
        return "credits";
    }
}
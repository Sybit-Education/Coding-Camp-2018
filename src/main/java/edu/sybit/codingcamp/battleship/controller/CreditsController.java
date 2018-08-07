/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
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
public class CreditsController{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditsController.class);
    
    @RequestMapping(value = "/credits")
    public String credits() {
        LOGGER.debug("--> credits");
               
        return "credits";
    }
}
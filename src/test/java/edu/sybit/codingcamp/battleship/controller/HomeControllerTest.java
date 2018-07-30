/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */

package edu.sybit.codingcamp.battleship.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class HomeControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private HomeController homeController;

    @Before
    public void init() {
        initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(homeController)
            .build();
    }

    @Test
    public void indexTest() throws Exception {
        MockHttpServletRequestBuilder request = get("/");
        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
    }
}

package com.test.ecomdemo.controller;

import com.test.ecomdemo.repository.WatchRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WatchRepository watchRepository;

    @Test
    void checkoutSuccess() throws Exception {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                .content("[\"001\",\"002\",\"001\",\"004\",\"003\"]")
                .headers(headers))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.is(360.0)));
    }

    @Test
    void checkoutFailureWithNotFound() throws Exception {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .content("[\"001\",\"002\",\"001\",\"004\",\"006\"]")
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void checkoutFailureWithBadRequest() throws Exception {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .content("[\"hello\"]")
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                        .content("")
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
}

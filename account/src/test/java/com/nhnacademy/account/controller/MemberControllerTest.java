package com.nhnacademy.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.account.entity.Member;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void testCreateMember()throws Exception{
        ObjectMapper mapper=new ObjectMapper();
        Member member=new Member(1L,"test","test@mail.com","1234","testUser");
        mockMvc.perform(post("/accounts/signup")
                        .content(mapper.writeValueAsString(member))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.seq",equalTo(1)));

    }

    @Test
    @Order(2)
    void testGetMembers()throws Exception{
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].seq",equalTo(1)));
    }

    @Test
    @Order(3)
    void testCheckExist()throws Exception{
        mockMvc.perform(get("/accounts/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id",equalTo("test")))
                .andExpect(jsonPath("$[0].password",equalTo("1234")));
    }

    @Test
    @Order(4)
    void testGetEmail()throws Exception{


    }

    @Test
    @Order(5)
    void testGetMember()throws Exception{
        mockMvc.perform(get("/accounts/{seq}",1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",equalTo("test")));
    }



    @Test
    @Order(6)
    void deleteMember()throws Exception{
        this.mockMvc.perform(delete("/accounts/{seq}",1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result",equalTo("ok")));
    }
}
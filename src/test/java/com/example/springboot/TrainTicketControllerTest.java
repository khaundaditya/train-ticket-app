package com.example.springboot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.*;

public class TrainTicketControllerTest {

	@Autowired
    private ObjectMapper objectMapper;
	
    @Mock
    private List<TrainTicket> tickets;

    @InjectMocks
    private TrainTicketController controller;

    private MockMvc mockMvc;
    
    @BeforeEach
    public void setup() {
    	MockitoAnnotations.openMocks(this);
    	mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testPurchaseTicket() throws Exception {
        TrainTicket ticket = new TrainTicket("London", "France", "John", "Doe", "john.doe@example.com", 20.0);

        when(tickets.size()).thenReturn(1);
        when(tickets.add(any(TrainTicket.class))).thenReturn(true);

        mockMvc.perform(post("/api/tickets/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ticket)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.from").value("London"))
                .andExpect(jsonPath("$.to").value("France"))
                .andExpect(jsonPath("$.userFirstName").value("John"))
                .andExpect(jsonPath("$.userLastName").value("Doe"))
                .andExpect(jsonPath("$.userEmail").value("john.doe@example.com"))
                .andExpect(jsonPath("$.price").value(20.0));
    }

    @Test
    public void testGetReceipt() throws Exception {
        TrainTicket ticket = new TrainTicket("London", "France", "John", "Doe", "john.doe@example.com", 20.0);
        when(tickets.stream()).thenReturn(Stream.of(ticket));

        mockMvc.perform(get("/api/tickets/receipt/{userEmail}", "john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userFirstName").value("John"))
                .andExpect(jsonPath("$.userLastName").value("Doe"))
                .andExpect(jsonPath("$.userEmail").value("john.doe@example.com"))
                .andExpect(jsonPath("$.price").value(20.0));
    }

    @Test
    public void testGetUsersBySection() throws Exception {
        // Ticket creation and purchase operations
        TrainTicket ticket1 = new TrainTicket("London", "France", "John", "Doe", "john.doe@example.com", 20.0);
        TrainTicket ticket2 = new TrainTicket("Rome", "Portugal", "Jane", "Smith", "jane.smith@example.com", 45.0);
        List<TrainTicket> testTickets = Arrays.asList(ticket1, ticket2);

        // Mock the behavior of the tickets list
        when(tickets.stream()).thenReturn(testTickets.stream());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tickets/users/A"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seat").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].user").value("Jane Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].seat").value("2"));
       
    }

    

    @Test
    public void testRemoveUser() throws Exception {
        when(tickets.removeIf(any())).thenReturn(true);

        mockMvc.perform(delete("/api/tickets/remove/{userEmail}", "john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("User removed successfully."));
    }

    @Test
    public void testModifySeat() throws Exception {
        TrainTicket ticket = new TrainTicket("London", "France", "John", "Doe", "john.doe@example.com", 20.0);
        when(tickets.stream()).thenReturn(Stream.of(ticket));

        mockMvc.perform(put("/api/tickets/modify-seat/{userEmail}/{newSeat}", "john.doe@example.com", 10))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seat").value(10));
    }

    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

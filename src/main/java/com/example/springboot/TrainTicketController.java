package com.example.springboot;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class TrainTicketController {


    // API to submit a purchase for a ticket
    private List<TrainTicket> tickets = new ArrayList<>();
    
    public List<TrainTicket> getTickets() {
        return tickets;
    }
    public void setTickets(List<TrainTicket> tickets) {
        this.tickets = tickets;
    }

    // API to submit a purchase for a ticket
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody @Valid TrainTicket ticket, BindingResult bindingResult) {
        // Validate the ticket fields
        if (bindingResult.hasErrors()) {
            // Log validation errors
            bindingResult.getAllErrors().forEach(error -> System.out.println("Validation Error: " + error.getDefaultMessage()));

            // If validation fails, return the validation errors
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // Assuming a simple allocation logic for sections A and B
        ticket.setSection(tickets.size() % 2 == 0 ? "A" : "B");
        ticket.setSeat(tickets.size() + 1);

        tickets.add(ticket);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }


    // API to view the details of the receipt for a user
    @GetMapping("/receipt/{userEmail}")
    public TrainTicket getReceipt(@PathVariable String userEmail) {
        return tickets.stream()
                .filter(ticket -> ticket.getUserEmail().equals(userEmail))
                .findFirst()
                .orElse(null);
    }

    // API to view users and seats allocated by section
    @GetMapping("/users/{section}")
    public List<Map<String, String>> getUsersBySection(@PathVariable String section) {
        return tickets.stream()
                .filter(ticket -> ticket.getSection().equals(section))
                .map(ticket -> Map.of("user", ticket.getUserFirstName() + " " + ticket.getUserLastName(), "seat", String.valueOf(ticket.getSeat())))
                .collect(Collectors.toList());
    }

    // API to remove a user from the train
    @DeleteMapping("/remove/{userEmail}")
    public String removeUser(@PathVariable String userEmail) {
        tickets.removeIf(ticket -> ticket.getUserEmail().equals(userEmail));
        return "User removed successfully.";
    }

    // API to modify a user's seat
    @PutMapping("/modify-seat/{userEmail}/{newSeat}")
    public TrainTicket modifySeat(@PathVariable String userEmail, @PathVariable int newSeat) {
        TrainTicket ticket = tickets.stream()
                .filter(t -> t.getUserEmail().equals(userEmail))
                .findFirst()
                .orElse(null);

        if (ticket != null) {
            ticket.setSeat(newSeat);
        }

        return ticket;
    }
}

package main.service;

import main.entity.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketService {

    public void print(Ticket ticket) {
        System.out.println(ticket.toString());
    }

    public void shared(String phone) {
        System.out.println("Shared by this phone " + phone);
    }

    public void shared(String phone, String email) {
        System.out.println("Shared by this phone " + phone + " and email " + email);
    }

    public List<Ticket> readTicketsFromFile(String filename) {
        List<Ticket> tickets = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    tickets.add(new Ticket(parts[0].trim(), parts[1].trim(), parts[2].trim(), Double.parseDouble(parts[3].trim())));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public void validateTickets(List<Ticket> tickets) {
        int totalTickets = tickets.size();
        int validTickets = 0;
        Map<String, Integer> violationCounts = new HashMap<>();
        violationCounts.put("startDate", 0);
        violationCounts.put("price", 0);
        violationCounts.put("ticketType", 0);

        Date today = new Date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Ticket ticket : tickets) {
            boolean isValid = true;
            List<String> violations = new ArrayList<>();

            if (!List.of("DAY", "WEEK", "MONTH", "YEAR").contains(ticket.getTicketType())) {
                isValid = false;
                violations.add("ticketType");
                violationCounts.put("ticketType", violationCounts.get("ticketType") + 1);
            }

            if (ticket.getStartDate() != null && !ticket.getStartDate().isEmpty()) {
                try {
                    LocalDate startDate = LocalDate.parse(ticket.getStartDate(), formatter);
                    if (startDate.isAfter(LocalDate.now())) {
                        isValid = false;
                        violations.add("startDate (in future)");
                        violationCounts.put("startDate", violationCounts.get("startDate") + 1);
                    }
                } catch (DateTimeParseException e) {
                    isValid = false;
                    violations.add("startDate (invalid format)");
                    violationCounts.put("startDate", violationCounts.get("startDate") + 1);
                }
            } else {
                isValid = false;
                violations.add("startDate (missing)");
                violationCounts.put("startDate", violationCounts.get("startDate") + 1);
            }

            if (ticket.getPrice() <= 0) {
                isValid = false;
                violations.add("price");
                violationCounts.put("price", violationCounts.get("price") + 1);
            }

            if (ticket.getPrice() % 2 != 0) {
                isValid = false;
                violations.add("price (not even)");
                violationCounts.put("price", violationCounts.get("price") + 1);
            }

            if (!isValid) {
                System.out.println("Invalid ticket: " + ticket);
                System.out.println("Violations: " + violations);
            } else {
                validTickets++;
            }
        }

        String mostCommonViolation = violationCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Valid Tickets: " + validTickets);
        System.out.println("Most Common Violation: " + mostCommonViolation);
    }
}

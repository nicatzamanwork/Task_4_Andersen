package main.service;

import main.entity.Ticket;
import main.enums.TicketType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class TicketService {

    private static final String START_DATE = "startDate";
    private static final String PRICE = "price";
    private static final String TICKET_TYPE = "ticketType";

    public static boolean isValidType(TicketType type) {
        try {
            TicketType.valueOf(type.name());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

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
                TicketType ticketType = TicketType.valueOf(parts[1].trim().toUpperCase());
                if (parts.length == 4) {
                    tickets.add(new Ticket(parts[0].trim(), ticketType, parts[2].trim(), Double.parseDouble(parts[3].trim())));
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
        Map<String, Integer> violationCounts = initializeViolationCounts();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Ticket ticket : tickets) {
            boolean isValid = validateTicket(ticket, violationCounts, formatter);

            if (isValid) {
                validTickets++;
            }
        }

        printValidationSummary(tickets.size(), validTickets, violationCounts);
    }

    private Map<String, Integer> initializeViolationCounts() {
        Map<String, Integer> violationCounts = new HashMap<>();
        violationCounts.put(START_DATE, 0);
        violationCounts.put(PRICE, 0);
        violationCounts.put(TICKET_TYPE, 0);
        return violationCounts;
    }

    private boolean validateTicket(Ticket ticket, Map<String, Integer> violationCounts, DateTimeFormatter formatter) {
        boolean isValid = true;
        List<String> violations = new ArrayList<>();

        if (!isValidType(ticket.getTicketType())) {
            isValid = false;
            violations.add(TICKET_TYPE);
            incrementViolationCount(violationCounts, TICKET_TYPE);
        }

        if (!validateStartDate(ticket.getStartDate(), formatter, violations, violationCounts)) {
            isValid = false;
        }

        if (!validatePrice(ticket.getPrice(), violations, violationCounts)) {
            isValid = false;
        }

        if (!isValid) {
            System.out.println("Invalid ticket: " + ticket);
            System.out.println("Violations: " + violations);
        }

        return isValid;
    }

    private boolean validateStartDate(String startDate, DateTimeFormatter formatter, List<String> violations, Map<String, Integer> violationCounts) {
        if (startDate != null && !startDate.isEmpty()) {
            try {
                LocalDate parsedDate = LocalDate.parse(startDate, formatter);
                if (parsedDate.isAfter(LocalDate.now())) {
                    violations.add(START_DATE + " (in future)");
                    incrementViolationCount(violationCounts, START_DATE);
                    return false;
                }
            } catch (DateTimeParseException e) {
                violations.add(START_DATE + " (invalid format)");
                incrementViolationCount(violationCounts, START_DATE);
                return false;
            }
        } else {
            violations.add(START_DATE + " (missing)");
            incrementViolationCount(violationCounts, START_DATE);
            return false;
        }
        return true;
    }

    private boolean validatePrice(double price, List<String> violations, Map<String, Integer> violationCounts) {
        boolean isValid = true;
        if (price <= 0) {
            violations.add(PRICE);
            incrementViolationCount(violationCounts, PRICE);
            isValid = false;
        }

        if (price % 2 != 0) {
            violations.add(PRICE + " (not even)");
            incrementViolationCount(violationCounts, PRICE);
            isValid = false;
        }

        return isValid;
    }

    private void incrementViolationCount(Map<String, Integer> violationCounts, String violationType) {
        violationCounts.put(violationType, violationCounts.get(violationType) + 1);
    }

    private void printValidationSummary(int totalTickets, int validTickets, Map<String, Integer> violationCounts) {
        String mostCommonViolation = violationCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Valid Tickets: " + validTickets);
        System.out.println("Most Common Violation: " + mostCommonViolation);
    }
}

package main;

import main.entity.Ticket;
import main.entity.User;
import main.service.AdminService;
import main.service.ClientService;
import main.service.TicketService;
import main.service.abstracts.AbstractUser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        User client = new User("Nicat","CLIENT");
        User admin = new User("Nicat","ADMIN");

        AbstractUser clientService = new ClientService();
        ((ClientService) clientService).getTicket(client);
        clientService.getRole(client);

        AbstractUser adminService = new AdminService();
        ((AdminService) adminService).checkTicket(admin);
        adminService.getRole(admin);

        TicketService ticketService = new TicketService();
        ticketService.shared("5352345234");

        ticketService.shared("52535","adfsgf");

        System.out.println("\n--- Validating Tickets ---");
        List<Ticket> tickets = ticketService.readTicketsFromFile("tickets.txt");
        ticketService.validateTickets(tickets);
    }
}

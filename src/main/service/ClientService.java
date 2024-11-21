package main.service;

import main.entity.Ticket;
import main.entity.User;
import main.service.abstracts.AbstractUser;

public class ClientService extends AbstractUser {

    @Override
    public void getRole(User user) {
        System.out.println("Your Role is CLIENT");
    }

    public void getTicket(User user) {
        TicketService ticketService = new TicketService();

        Ticket fullTicket = new Ticket(123, "MainHall", 101, System.currentTimeMillis(),
                true, 'B', 5.0, 30.0);

        Ticket limitedTicket = new Ticket("SideHall", 102, System.currentTimeMillis());
        Ticket emptyTicket = new Ticket("EmptyHall", 0, System.currentTimeMillis());

        System.out.println("Full Ticket Details:");
        ticketService.print(fullTicket);

        System.out.println("\nLimited Ticket Details:");
        ticketService.print(limitedTicket);

        System.out.println("\nEmpty Ticket Details:");
        ticketService.print(emptyTicket);
    }
}

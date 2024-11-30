package main.service;

import main.entity.Ticket;
import main.entity.User;
import main.service.abstracts.AbstractUser;

public class AdminService extends AbstractUser {

    public void checkTicket(User user) {
        if (user.getUserRole().equals("ADMIN")){
            TicketService ticketService = new TicketService();

            Ticket fullTicket = new Ticket(123, "MainHall", 101, System.currentTimeMillis(),
                    true, 'B', 5.0, 30.0);

            Ticket limitedTicket = new Ticket("SideHall", 102,System.currentTimeMillis());
            Ticket emptyTicket = new Ticket("EmptyHall", 0, System.currentTimeMillis());

            System.out.println("Full main.entity.Ticket Details:");
            ticketService.print(fullTicket);

            System.out.println("\nLimited main.entity.Ticket Details:");
            ticketService.print(limitedTicket);

            System.out.println("\nEmpty main.entity.Ticket Details:");
            ticketService.print(emptyTicket);
        }  else{
            System.out.println("You do not have the required role");
        }
    }

    @Override
    public void getRole(User user) {
        System.out.println("Your Role is ADMIN");
    }
}

package main.service;

import main.entity.Ticket;

public class TicketService {

    public void print(Ticket ticket) {
        System.out.println( ticket.toString());
    }

    public void shared(String phone){
       System.out.println("Shared by this phone " + phone);
    }
    public void shared(String phone, String email){
        System.out.println("Shared by this phone " + phone + " and email " + email);
    }
}

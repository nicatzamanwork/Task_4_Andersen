package main.entity;

import main.entity.abstracts.AbstractTicket;
import main.enums.TicketType;
import main.util.NullableFieldValidator;
import main.util.NullableWarning;

import java.util.Objects;

import static main.enums.TicketType.MONTH;

public class Ticket extends AbstractTicket {

  @NullableWarning
  private String concertHall;
  private int eventCode;
  private long time;
  private boolean isPromo;
  private char stadiumSector;
  private double maxBackpackWeight;
  private double price;
  private long createdAt;
  private String startDate;
  private TicketType ticketType;

  public Ticket(int ticketID, String hall, int code, long eventTime, boolean promoStatus,
                char sector, double backpackWeight, double ticketPrice) {
    setId(ticketID);
    this.concertHall = hall;
    this.eventCode = code;
    this.time = eventTime;
    this.isPromo = promoStatus;
    this.stadiumSector = sector;
    this.maxBackpackWeight = backpackWeight;
    this.price = ticketPrice;
    this.createdAt = System.currentTimeMillis();
    NullableFieldValidator.validate(this);
  }

  public Ticket(String hall, TicketType ticketType, String startDate, double ticketPrice) {
    this.concertHall = hall;
    this.ticketType = ticketType;
    this.startDate = startDate;
    this.price = ticketPrice;
    this.createdAt = System.currentTimeMillis();
    NullableFieldValidator.validate(this);
  }

  public Ticket(String hall, int code, long eventTime) {
    this.concertHall = hall;
    this.eventCode = code;
    this.time = eventTime;
    this.createdAt = System.currentTimeMillis();
    this.isPromo = false;
    this.stadiumSector = 'A';
    this.maxBackpackWeight = 0.0;
    this.price = 0.0;
    this.ticketType = MONTH;
    this.startDate = "";
    NullableFieldValidator.validate(this); // Call the utility class
  }

  public String getStartDate() {
    return startDate;
  }

  public TicketType getTicketType() {
    return ticketType;
  }

  public double getPrice() {
    return price;
  }

  public void setTime(long time) {
    if (time <= 0) {
      throw new IllegalArgumentException("Time must be positive");
    }
    this.time = time;
  }

  public void setStadiumSector(char stadiumSector) {
    if (!Character.isLetter(stadiumSector)) {
      throw new IllegalArgumentException("Stadium sector must be a letter");
    }
    this.stadiumSector = stadiumSector;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ticket ticket = (Ticket) o;
    return eventCode == ticket.eventCode &&
            time == ticket.time &&
            isPromo == ticket.isPromo &&
            stadiumSector == ticket.stadiumSector &&
            Double.compare(maxBackpackWeight, ticket.maxBackpackWeight) == 0 &&
            Double.compare(price, ticket.price) == 0 &&
            createdAt == ticket.createdAt &&
            Objects.equals(concertHall, ticket.concertHall) &&
            Objects.equals(startDate, ticket.startDate) &&
            Objects.equals(ticketType, ticket.ticketType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(concertHall, eventCode, time, isPromo, stadiumSector, maxBackpackWeight,
            price, createdAt, startDate, ticketType);
  }

  @Override
  public String toString() {
    return "Ticket{" +
            "concertHall='" + concertHall + '\'' +
            ", eventCode=" + eventCode +
            ", time=" + time +
            ", isPromo=" + isPromo +
            ", stadiumSector=" + stadiumSector +
            ", maxBackpackWeight=" + maxBackpackWeight +
            ", price=" + price +
            ", startDate='" + startDate + '\'' +
            ", ticketType='" + ticketType + '\'' +
            ", createdAt=" + createdAt +
            '}';
  }
}

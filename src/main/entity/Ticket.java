package main.entity;

import main.entity.abstracts.AbstractTicket;
import main.util.NullableWarning;

import java.lang.reflect.Field;
import java.util.Objects;

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



  public Ticket(int ticketID, String hall, int code, long eventTime, boolean promoStatus,
                char sector, double backpackWeight, double ticketPrice) {
    setId(ticketID);
    concertHall = hall;
    eventCode = code;
    time = eventTime;
    isPromo = promoStatus;
    stadiumSector = sector;
    maxBackpackWeight = backpackWeight;
    price = ticketPrice;
    createdAt = System.currentTimeMillis();
    checkNullableFields();
  }

  public Ticket(String hall, int code, long eventTime) {
    concertHall = hall;
    eventCode = code;
    time = eventTime;
    createdAt = System.currentTimeMillis();
    isPromo = false;
    stadiumSector = 'A';
    maxBackpackWeight = 0.0;
    price = 0.0;
    checkNullableFields();
  }


  private void checkNullableFields() {
    for (Field field : this.getClass().getDeclaredFields()) {
      if (field.isAnnotationPresent(NullableWarning.class)) {
        try {
          field.setAccessible(true);
          Object value = field.get(this);
          if (value == null) {
            throw new RuntimeException("VARIABLE IS NULL");
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void setTime(long time) {
    this.time = time;
  }

  public void setStadiumSector(char stadiumSector) {
    this.stadiumSector = stadiumSector;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ticket ticket = (Ticket) o;
    return eventCode == ticket.eventCode && time == ticket.time && isPromo == ticket.isPromo && stadiumSector == ticket.stadiumSector && Double.compare(maxBackpackWeight, ticket.maxBackpackWeight) == 0 && Double.compare(price, ticket.price) == 0 && createdAt == ticket.createdAt && Objects.equals(concertHall, ticket.concertHall);
  }

  @Override
  public int hashCode() {
    return Objects.hash(concertHall, eventCode, time, isPromo, stadiumSector, maxBackpackWeight, price, createdAt);
  }

  @Override
  public String toString() {
    return "main.entity.Ticket{" +
            "concertHall='" + concertHall + '\'' +
            ", eventCode=" + eventCode +
            ", time=" + time +
            ", isPromo=" + isPromo +
            ", stadiumSector=" + stadiumSector +
            ", maxBackpackWeight=" + maxBackpackWeight +
            ", price=" + price +
            ", createdAt=" + createdAt +
            '}';
  }
}

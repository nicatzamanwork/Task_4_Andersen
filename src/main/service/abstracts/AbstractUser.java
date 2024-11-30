package main.service.abstracts;

import main.entity.User;
//sdfsdf
public abstract class AbstractUser {

   protected Role role;

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public abstract void getRole(User user);

   public enum Role {
      CLIENT, ADMIN, GUEST
   }
}

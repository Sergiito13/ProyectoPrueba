package somosdeweb.edu.aut05_04myikea.Models;


import jakarta.persistence.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public Roles() {
    }

    public Roles(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles = (Roles) o;
        return Objects.equals(id, roles.id) && Objects.equals(role, roles.role) && Objects.equals(users, roles.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, users);
    }
}

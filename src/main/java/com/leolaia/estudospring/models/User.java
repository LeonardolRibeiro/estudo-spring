package com.leolaia.estudospring.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true)
    private String userName;

    //Essa campo e coluna não são obrigatórios do spring security
    @Column(name = "full_name", unique = true)
    private String fullName;
    @Column(name = "password")
    private String password;
    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;
    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;
    @Column(name = "enabled")
    private Boolean enabled;

    //FetchType.EAGER -> Assim que carregar user carrega tbm suas permissoes
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission",
            joinColumns = {@JoinColumn (name = "id_user")},
            inverseJoinColumns = {@JoinColumn (name = "id_permission")})
    private List<Permission> permissions;

    public User() {
    }

    //Precisamos deste metodo porque estamos implementando de UserDetails e elas não estão no formato que queremos
    public List<String> getRoles(){

        List<String> roles = new ArrayList<>();
        for (Permission permission : permissions) {
            roles.add(permission.getDescription());
        }
        return roles;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(fullName, user.fullName) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(isAccountNonExpired(), user.isAccountNonExpired()) && Objects.equals(isAccountNonLocked(), user.isAccountNonLocked()) && Objects.equals(isCredentialsNonExpired(), user.isCredentialsNonExpired()) && Objects.equals(isEnabled(), user.isEnabled()) && Objects.equals(permissions, user.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, fullName, getPassword(), isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), isEnabled(), permissions);
    }
}

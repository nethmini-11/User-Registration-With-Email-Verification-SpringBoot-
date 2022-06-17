/**
 * @author - Chamath_Wijayarathna
 * Date :6/15/2022
 */

package com.example.demo.entity;

import com.example.demo.enumpackage.AppUserRoleEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails { // Store data in AppUser table after User add Registration Data
    /// resetPasswordToken Column create to Reset Password


    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @Id
    /*  GenerationType.SEQUENCE is my preferred way to generate primary key values and uses a
        database sequence to generate unique values */
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private Long id;
    private String firstName;
    private String lastName;
    @Column(name = "email", length = 50, unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRoleEnum appUserRoleEnum;
    private Boolean locked = false;
    private Boolean enabled = false;

    private String resetPasswordToken;

    public AppUser(String firstName, String lastName, String email, String password, AppUserRoleEnum appUserRoleEnum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.appUserRoleEnum = appUserRoleEnum;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRoleEnum.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

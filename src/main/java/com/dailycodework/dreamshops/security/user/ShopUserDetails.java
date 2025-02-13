package com.dailycodework.dreamshops.security.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dailycodework.dreamshops.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

// Se pone NoArgsContructor porque en Spring, requieren un constructor sin argumentos para poder instanciar objetos
// a través de mecanismos de reflexión o proxies dinámicos.
//Spring Security internamente puede necesitar instanciar objetos de UserDetails. Cuando se trabaja con autenticación 
// basada en JWT o bases de datos, es común que Spring deserialice usuarios desde la sesión o la base de datos, y en algunos casos, 
// frameworks de serialización como Jackson requieren un constructor sin argumentos para poder reconstruir la instancia correctamente.

public class ShopUserDetails implements UserDetails{

    private Long id;
    private String email;
    private String password;

    //Autorización Otorgada
    private Collection<GrantedAuthority> authorities;

    public static ShopUserDetails buildUserDetails(User user) {
        //Obtenemos los roles del usuario
        //A cada role le otorgamos un simpleGrantedAuthority
        List<GrantedAuthority> authorities = user.getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
        //Creamos una instancia de la clase, es decir, tiene los atributos id, email, password y authorities
        // Esta instancia es creada por el constructor gracias a las anotaciones @AllArgsConstructor
        // Y le asignamos los valores del Usuario y los authorities de cada role que obtuvimos
        return new ShopUserDetails(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

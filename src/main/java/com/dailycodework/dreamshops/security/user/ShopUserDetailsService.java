package com.dailycodework.dreamshops.security.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class ShopUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    // Servicio que en caso que el email este registrado en la BD, crea una instancia de UserDetails
    // Es decir, obtiene los roles y da autorizaciones llamando a buildUserDetails
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email))
            .orElseThrow(() -> new UsernameNotFoundException("Usiario no encontrado"));
        return ShopUserDetails.buildUserDetails(user);
    }

}

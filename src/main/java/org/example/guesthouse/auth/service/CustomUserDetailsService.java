package org.example.guesthouse.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.guesthouse.user.infrastructure.User;
import org.example.guesthouse.user.infrastructure.UserDataRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Component("userDetailService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDataRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username){
        return userRepository.findOneWithAuthoritiesByUsername(username)
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    // user 객체 생성 메소드
    private org.springframework.security.core.userdetails.User createUser(String username, User user){
        if(!user.isActivated()){
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}
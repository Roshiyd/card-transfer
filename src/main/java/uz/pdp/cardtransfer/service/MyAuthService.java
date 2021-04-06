package uz.pdp.cardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MyAuthService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        List<User> userList=new ArrayList<>(Arrays.asList(
                new User("User1",passwordEncoder.encode("password1"),new ArrayList<>()),
                new User("User2",passwordEncoder.encode("password2"),new ArrayList<>()),
                new User("User3",passwordEncoder.encode("password3"),new ArrayList<>()),
                new User("User4",passwordEncoder.encode("password4"),new ArrayList<>())));

        for (User user : userList) {
            if (user.getUsername().equals(s)){
                return user;
            }
        }
        throw new UsernameNotFoundException("Not Found");

    }
}

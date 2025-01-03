package com.freelance.bookCar.security;



import com.freelance.bookCar.models.user.User;
import com.freelance.bookCar.respository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OurUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return(User) userRepository.findByEmail(username)
                             .orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + username));
    }
}

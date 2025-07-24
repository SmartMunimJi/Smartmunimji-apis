package com.smartmunimji.services;

import com.smartmunimji.daos.SellerDao;
import com.smartmunimji.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class SellerDetailsService implements UserDetailsService {

    @Autowired
    private SellerDao sellerDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Seller> optionalSeller = sellerDao.findBySellersemail(email);

        if (optionalSeller.isEmpty()) {
            throw new UsernameNotFoundException("Seller not found with email: " + email);
        }

        Seller seller = optionalSeller.get();

        return new org.springframework.security.core.userdetails.User(
                seller.getSellersemail(),
                seller.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_SELLER"))
        );
    }
}

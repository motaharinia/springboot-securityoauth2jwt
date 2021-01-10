package com.motaharinia.authorizationserver.security.business.service;


import com.motaharinia.authorizationserver.security.persistence.orm.securityuser.SecurityUser;
import com.motaharinia.authorizationserver.security.persistence.orm.securityuser.SecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SecurityUserServiceImpl implements UserDetailsService {

    private SecurityUserRepository securityUserRepository;

    @Autowired
    public SecurityUserServiceImpl(SecurityUserRepository securityUserRepository) {
        this.securityUserRepository=securityUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("----------------------------- username:"+username);
        SecurityUser securityUser=securityUserRepository.findByUsername(username);
        if(securityUser==null){
            throw new BadCredentialsException("Bad credentials");
        }


//        Set<SecurityPermission> securityPermissionSet = new HashSet<>();
//        securityPermissionSet.add(new SecurityPermission("perm1"));
//        securityPermissionSet.add(new SecurityPermission("perm2"));
//        Set<SecurityRole> securityRoleSet = new HashSet<>();
//        securityRoleSet.add(new SecurityRole("role1", securityPermissionSet));
//        SecurityUser securityUser = new SecurityUser("09124376251", "123456", "Mostafa", "Motaharinia", securityRoleSet);


        new AccountStatusUserDetailsChecker().check(securityUser);
        return securityUser;
    }
}

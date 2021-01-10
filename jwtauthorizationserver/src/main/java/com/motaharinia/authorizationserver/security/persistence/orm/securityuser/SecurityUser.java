package com.motaharinia.authorizationserver.security.persistence.orm.securityuser;

import com.motaharinia.authorizationserver.security.persistence.orm.role.SecurityRole;
import com.motaharinia.msjpautility.entity.GenericEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * @author: https://github.com/motaharinia<br>
 * Description:<br>
 * کلاس انتیتی کاربر امنیت
 */
@Entity
@Table(name = "security_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})})
@Getter
@Setter
@NoArgsConstructor
public class SecurityUser  extends GenericEntity implements Serializable,UserDetails{
    /**
     * شناسه
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * کلمه کاربری
     */
    @Column(name = "username")
    private String username;
    /**
     * رمز عبور
     */
    @Column(name = "password")
    private String password;
    /**
     * نام
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * نام خانوادگی
     */
    @Column(name = "last_name")
    private String lastName;
    /**
     *پست الکترونیکی
     */
    @Column(name = "email")
    private String email;
    /**
     * حساب کاربری منقضی شده است؟
     */
    @Column(name = "account_expired")
    private Boolean accountExpired = false;

    /**
     * حساب کاربری قفل شده است؟
     */
    @Column(name = "account_locked")
    private Boolean accountLocked = false;

    /**
     * اطلاعات لاگین منقضی شده است؟
     */
    @Column(name = "credential_expired")
    private Boolean credentialExpired = false;


    /**
     * کاربر فعال است؟
     */
    @Column(name = "enabled")
    private Boolean enabled = true;


    /**
     * لیست دسترسی ها
     */
    @JoinTable(name = "security_user_jt_security_role", joinColumns = {
            @JoinColumn(name = "security_user_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "security_role_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<SecurityRole> roleSet = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        roleSet.stream().forEach(role -> {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(role.getTitle()));
            role.getPermissionSet().stream().forEach(permission -> grantedAuthoritySet.add(new SimpleGrantedAuthority(permission.getTitle())));
        });
        return grantedAuthoritySet;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    public SecurityUser(String username, String password, String firstName, String lastName,Set<SecurityRole> roleSet) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleSet = roleSet;
    }
}

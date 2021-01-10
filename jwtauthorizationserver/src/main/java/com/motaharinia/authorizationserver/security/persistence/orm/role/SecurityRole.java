package com.motaharinia.authorizationserver.security.persistence.orm.role;

import com.motaharinia.authorizationserver.security.persistence.orm.permission.SecurityPermission;
import com.motaharinia.msjpautility.entity.GenericEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * @author: https://github.com/motaharinia<br>
 * Description:<br>
 * کلاس انتیتی نقش امنیت
 */
@Entity
@Table(name = "security_role")
@Getter
@Setter
@NoArgsConstructor
public class SecurityRole extends GenericEntity implements Serializable {
    /**
     * شناسه
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * عنوان نقش
     */
    @Column(name = "title")
    private String title;


    /**
     * لیست دسترسی ها
     */
    @JoinTable(name = "security_role_jt_security_permission", joinColumns = {
            @JoinColumn(name = "security_role_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "security_permission_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<SecurityPermission> permissionSet = new HashSet<>();


    public SecurityRole(String title, Set<SecurityPermission> permissionSet) {
        this.title = title;
        this.permissionSet = permissionSet;
    }
}

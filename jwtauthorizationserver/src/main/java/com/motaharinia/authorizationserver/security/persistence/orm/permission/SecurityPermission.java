package com.motaharinia.authorizationserver.security.persistence.orm.permission;

import com.motaharinia.msjpautility.entity.GenericEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author: https://github.com/motaharinia<br>
 * Description:<br>
 * کلاس انتیتی دسترسی امنیت
 */
@Entity
@Table(name = "security_permission")
@Getter
@Setter
@NoArgsConstructor
public class SecurityPermission extends GenericEntity implements Serializable {
    /**
     * شناسه
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * دسترسی
     */
    @Column(name = "title")
    private String title;

    public SecurityPermission(String title) {
        this.title = title;
    }
}

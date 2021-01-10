package com.motaharinia.authorizationserver.security.persistence.orm.securityuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;


/**
 * @author: https://github.com/motaharinia<br>
 * Description:<br>
 * کلاس ریپازیتوری کاربر امنیت
 */

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long>, JpaSpecificationExecutorWithProjection<SecurityUser> {
    SecurityUser findByUsername(String username);
}

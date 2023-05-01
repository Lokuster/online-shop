package com.lokuster.userservice.repository;

import com.lokuster.userservice.model.Role;
import com.lokuster.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("""
            DELETE FROM User u
            WHERE u.id = :id
            """)
    int delete(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("""
            UPDATE User u
            SET u.username = :username, u.email = :email, u.password = :password, u.roles = :roles
            WHERE u.id = :id
            """)
    User updateUser(@Param("username") String username,
                    @Param("email") String email,
                    @Param("password") String password,
                    @Param("roles") Set<Role> roles,
                    @Param("id") Long id);
}

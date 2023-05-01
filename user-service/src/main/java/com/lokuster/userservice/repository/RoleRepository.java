package com.lokuster.userservice.repository;

import com.lokuster.userservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("""
            SELECT r
            FROM Role r
            WHERE r.name = :name
            """)
    Role findByName(@Param("name") String name);
}

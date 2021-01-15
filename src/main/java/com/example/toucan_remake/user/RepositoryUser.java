package com.example.toucan_remake.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SuppressWarnings("ALL")
@Repository
public interface RepositoryUser extends JpaRepository<EntityUser, UUID> {

    EntityUser findByEmail(String username);
    EntityUser findByUuid(UUID uuid);
    EntityUser save(EntityUser entityUser);

    @Transactional
    void deleteByEmail(String username);

    @Transactional
    @Modifying
    @Query("UPDATE EntityUser u SET u.password = :password WHERE u.email = :email")
    void changePassword(@Param("email") String username,
                        @Param("password") String newHashedPassword);

    boolean existsByEmail(String email);

    /*@Transactional
    @Modifying
    @Query("UPDATE EntityUser u SET u.lockStatus = :status WHERE u.username = :username")
    void setLockStatus(@Param("username") String username,
                       @Param("status") boolean status);*/
}

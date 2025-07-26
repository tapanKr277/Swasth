package com.org.swasth_id_backend.repo;

import com.org.swasth_id_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {

    Optional<Role> findByRole(String role);
}

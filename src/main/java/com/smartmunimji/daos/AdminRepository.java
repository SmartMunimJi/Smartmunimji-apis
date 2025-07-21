package com.smartmunimji.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartmunimji.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Admin findByEmail(String email);
}

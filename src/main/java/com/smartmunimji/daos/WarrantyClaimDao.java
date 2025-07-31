package com.smartmunimji.daos;

import com.smartmunimji.entities.WarrantyClaim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarrantyClaimDao extends JpaRepository<WarrantyClaim, Integer> {
}

package com.examenT2.LP2_T2_Jose_Hermoza.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.examenT2.LP2_T2_Jose_Hermoza.model.OrdenCompraHermozaJose;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompraHermozaJose, Integer> {
	List<OrdenCompraHermozaJose> findAllByOrderByNroOrdenDesc();
}

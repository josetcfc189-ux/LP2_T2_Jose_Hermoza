package com.examenT2.LP2_T2_Jose_Hermoza.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.examenT2.LP2_T2_Jose_Hermoza.model.RubroHermozaJose;

public interface RubroRepository extends JpaRepository<RubroHermozaJose, Integer> {
	List<RubroHermozaJose> findAllByOrderByIdRubroDesc();
}

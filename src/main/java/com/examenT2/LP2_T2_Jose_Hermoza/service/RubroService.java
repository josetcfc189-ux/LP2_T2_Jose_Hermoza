package com.examenT2.LP2_T2_Jose_Hermoza.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.examenT2.LP2_T2_Jose_Hermoza.model.RubroHermozaJose;
import com.examenT2.LP2_T2_Jose_Hermoza.repository.RubroRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RubroService {
	private final RubroRepository rubroRepository;

	public List<RubroHermozaJose> getAll() {
		return rubroRepository.findAllByOrderByIdRubroDesc();
	}

	public RubroHermozaJose getOne(Integer id) {
		return rubroRepository.findById(id).orElse(null);
	}
}

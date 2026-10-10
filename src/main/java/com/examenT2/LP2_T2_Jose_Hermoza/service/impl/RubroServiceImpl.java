package com.examenT2.LP2_T2_Jose_Hermoza.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.examenT2.LP2_T2_Jose_Hermoza.model.RubroHermozaJose;
import com.examenT2.LP2_T2_Jose_Hermoza.repository.RubroRepository;
import com.examenT2.LP2_T2_Jose_Hermoza.service.RubroService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RubroServiceImpl implements RubroService {
	private final RubroRepository rubroRepository;

	@Override
	public List<RubroHermozaJose> getAll() {
		return rubroRepository.findAllByOrderByIdRubroDesc();
	}

	@Override
	public RubroHermozaJose getOne(Integer id) {
		return rubroRepository.findById(id).orElse(null);
	}
}

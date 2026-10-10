package com.examenT2.LP2_T2_Jose_Hermoza.service;

import java.util.List;
import com.examenT2.LP2_T2_Jose_Hermoza.model.RubroHermozaJose;

public interface RubroService {
	List<RubroHermozaJose> getAll();
	RubroHermozaJose getOne(Integer id);
}

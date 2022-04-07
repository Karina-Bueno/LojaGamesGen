package org.generation.lojagames.repository;

import java.util.List;

import org.generation.lojagames.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
	
	public List<Genero> findAllByNomeContainingIgnoreCase (String nome);
	
}

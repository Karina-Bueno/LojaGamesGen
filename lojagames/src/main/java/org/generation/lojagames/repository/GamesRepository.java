package org.generation.lojagames.repository;

import java.util.List;

import org.generation.lojagames.model.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  //indica que é uma classe de repositório 
public interface GamesRepository extends JpaRepository<Games, Long>{  //<qual o tipo de entidade que estamos trabalhando, qual o tipo de id>
	
	public List<Games> findAllByTituloContainingIgnoreCase (String titulo);  //consulta pelo titulo da postagem, retorna uma lista

}

package org.generation.lojagames.controller;

import java.util.List;

import org.generation.lojagames.model.Games;
import org.generation.lojagames.repository.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController   //indica que a classe é um controller
@RequestMapping("/games")   //indica por qual RI essa classe será acessada, no caso /postagem
@CrossOrigin("*")   //indica que essa classe vai aceitar requisão de qq origem
public class GamesController {
	
	@Autowired   //responsavel pela instanciação de interface
	private GamesRepository repository;

	@GetMapping   //sempre que vier uma requisição externa através de /games, se o metodo for get vai desparar esse metodo
	public ResponseEntity<List<Games>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Games> getById(@PathVariable long id) {
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Games>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping 
	public ResponseEntity<Games> postGames(@RequestBody Games games) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(games));
	}
	
	@PutMapping
	public ResponseEntity<Games> put(@RequestBody Games games) {
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(games));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}
}

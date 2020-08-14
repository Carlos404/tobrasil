package com.tobrasil.aluno.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.tobrasil.aluno.entity.Aluno;
import com.tobrasil.aluno.repository.AlunoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@RequestMapping(path = "/aluno")
@Api(value="APÍ REST GERENCIAMENTO ALUNOS")
@CrossOrigin(origins = "*")
public class AlunoController {

	@Autowired
	private AlunoRepository repository;

	@GetMapping
	@ApiOperation(value="Retorna lista de Alunos")
	public Iterable<Aluno> list() {
		return repository.findAll();
	}
	
	@PostMapping("/inserir")
	@ApiOperation(value="Insere aluno")
	public String insere(@RequestBody Aluno aluno) {

		repository.save(aluno);

		return "redirect:/aluno";

	}

	@GetMapping("/{id}")
	@ResponseBody
	@ApiOperation(value="Retorna Aluno por ID")
	public ResponseEntity<?> listaAlunoId(@PathVariable("id") long id) {
		VerificarAlunoExiste(id);
		Aluno aluno = repository.findById(id);
		return new ResponseEntity<>(aluno, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	@ApiOperation(value="Remove aluno por ID")
	public ResponseEntity<?> delete(@PathVariable long id) {
		VerificarAlunoExiste(id);
		repository.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping("/editar/{id}")
	@ApiOperation(value="Atualiza produtos")
	public Aluno update(@RequestBody Aluno aluno) {
		return repository.save(aluno);
	}

	private void VerificarAlunoExiste(long id) {

		if (repository.findById(id) == null) {
			throw new ResourceAccessException("Aluno não encontrado para id " + id);
		}

	}

}
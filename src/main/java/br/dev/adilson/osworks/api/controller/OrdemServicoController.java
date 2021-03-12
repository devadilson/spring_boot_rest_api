package br.dev.adilson.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.dev.adilson.osworks.api.model.OrdemServicoInput;
import br.dev.adilson.osworks.api.model.OrdemServicoModel;
import br.dev.adilson.osworks.domain.model.OrdemServico;
import br.dev.adilson.osworks.domain.repository.OrdemServicoRepository;
import br.dev.adilson.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRepository OrdemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordemServico = toEntify(ordemServicoInput);
		
		return toModel(gestaoOrdemServico.criar(ordemServico));		
	}
	
	@GetMapping
	public List<OrdemServicoModel> listar() {
		return toColletionModel(OrdemServicoRepository.findAll());		
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId){
		Optional<OrdemServico> ordemServico = OrdemServicoRepository.findById(ordemServicoId);
		
		if(ordemServico.isPresent()) {
			OrdemServicoModel ordemServicoModel = toModel(ordemServico.get());
			return ResponseEntity.ok(ordemServicoModel);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoModel.class);
	}
	
	private List<OrdemServicoModel> toColletionModel(List<OrdemServico> ordensServico){
		return ordensServico.stream()
				.map(OrdemServico -> toModel(OrdemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntify(OrdemServicoInput OrdemServicoInput) {
		return modelMapper.map(OrdemServicoInput, OrdemServico.class);
	}
	
}

package br.dev.adilson.osworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.adilson.osworks.api.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
package com.proyecto.egginder.repositorios;

import com.proyecto.egginder.entidades.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepositorio extends JpaRepository<Match, String>{

}

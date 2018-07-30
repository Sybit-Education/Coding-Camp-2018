package edu.sybit.codingcamp.battleship.repository;

import edu.sybit.codingcamp.battleship.objects.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, String> {

}

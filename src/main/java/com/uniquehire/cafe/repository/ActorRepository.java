package com.uniquehire.cafe.repository;

import com.uniquehire.cafe.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}


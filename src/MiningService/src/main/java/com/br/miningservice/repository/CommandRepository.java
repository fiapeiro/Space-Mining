package com.br.miningservice.repository;

import com.br.miningservice.model.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, String> {
}

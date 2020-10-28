package com.mooop.board.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mooop.board.entity.MSBHistory;

public interface HistoryRespository extends JpaRepository<MSBHistory , Long>{

}

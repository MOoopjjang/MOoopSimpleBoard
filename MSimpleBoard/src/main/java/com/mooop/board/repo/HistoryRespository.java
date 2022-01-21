package com.mooop.board.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mooop.board.entity.MSBHistory;
import org.springframework.data.repository.query.Param;

public interface HistoryRespository extends JpaRepository<MSBHistory , Long>{

    MSBHistory findByConfirmToken(@Param("confirmToken") String confirmToken);
}

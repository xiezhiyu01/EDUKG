package com.java.wangxingqi.dao;

import com.java.wangxingqi.entity.WrongProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WrongProblemMapper extends JpaRepository<WrongProblem, Long>, JpaSpecificationExecutor<WrongProblem> {
}

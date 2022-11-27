package com.java.wangxingqi.dao;

import com.java.wangxingqi.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryMapper extends JpaRepository<History, Long>, JpaSpecificationExecutor<History> {
}

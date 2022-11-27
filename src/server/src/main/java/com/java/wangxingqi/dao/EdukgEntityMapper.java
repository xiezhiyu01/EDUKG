package com.java.wangxingqi.dao;

import com.java.wangxingqi.entity.EdukgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EdukgEntityMapper extends JpaRepository<EdukgEntity, Long>, JpaSpecificationExecutor<EdukgEntity> {
    Optional<EdukgEntity> findEdukgEntityByUri(String uri);
}

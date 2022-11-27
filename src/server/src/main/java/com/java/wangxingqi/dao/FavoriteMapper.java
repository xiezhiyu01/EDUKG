package com.java.wangxingqi.dao;

import com.java.wangxingqi.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteMapper extends JpaRepository<Favorite, Long>, JpaSpecificationExecutor<Favorite> {
}

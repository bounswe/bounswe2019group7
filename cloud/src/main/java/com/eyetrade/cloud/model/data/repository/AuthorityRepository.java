package com.eyetrade.cloud.model.data.repository;

import com.eyetrade.cloud.model.data.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository  extends JpaRepository<Authority,String> {
}

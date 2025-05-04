package com.zl.mjga.repository;

import org.jooq.Configuration;
import org.jooq.generated.mjga.tables.daos.UserPositionMapDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserPositionMapRepository extends UserPositionMapDao {
  @Autowired
  public UserPositionMapRepository(Configuration configuration) {
    super(configuration);
  }
}

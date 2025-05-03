package com.zl.mjga.repository;

import org.jooq.Configuration;
import org.jooq.generated.mjga.tables.daos.UserDepartmentMapDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDepartmentMapRepository extends UserDepartmentMapDao {
  @Autowired
  public UserDepartmentMapRepository(Configuration configuration) {
    super(configuration);
  }
}

package org.edupoll.repository;

import org.edupoll.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepoitory extends JpaRepository<User, Long>{

}

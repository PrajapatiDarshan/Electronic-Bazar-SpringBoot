package com.eb1.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eb1.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByMobile(String mobile);
}

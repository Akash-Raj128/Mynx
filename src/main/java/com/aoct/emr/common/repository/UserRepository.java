package com.aoct.emr.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aoct.emr.common.entity.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
	public Optional<UserModel> findByEmail(String email);

	
	@Modifying
	@Query("UPDATE UserModel u SET u.password = :password WHERE u.email = :email")
	void changePassword(@Param("email") String email, @Param("password") String password);


}

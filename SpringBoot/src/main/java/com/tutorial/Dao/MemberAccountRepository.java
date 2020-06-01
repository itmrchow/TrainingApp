package com.tutorial.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.Model.MemberAccountModel;

public interface MemberAccountRepository extends JpaRepository<MemberAccountModel, Long> {

}

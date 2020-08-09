package com.doctor.assistant.userserver.springdata.repository;

import com.doctor.assistant.userserver.springdata.entity.AccountbookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountbookRepository extends JpaRepository<AccountbookEntity, String>,CrudRepository<AccountbookEntity, String> {

    AccountbookEntity findFirstByAccountbookCodeOrAccountbookName(String accountbookCode, String accountbookName);

    AccountbookEntity findFirstByAccountbookCodeAndAccountbookName(String accountbookCode, String accountbookName);

    List<AccountbookEntity> findAll();
}

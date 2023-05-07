package com.km.efactory.workshop.security.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long>{
    @Query(value = " select t from Token t inner join Employee e on t.employee.id = e.id where e.id = :id and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokenByEmployee(Long id);

    Optional<Token> findByToken(String token);
}

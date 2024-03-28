package com.DogFoot.adpotAnimal.member.repository;

import com.DogFoot.adpotAnimal.member.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long id);

    Optional<Users> findByUserId(String userId);

    boolean existsByUserId(String userId);
}

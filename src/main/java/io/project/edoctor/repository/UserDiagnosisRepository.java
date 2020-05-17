package io.project.edoctor.repository;

import io.project.edoctor.model.entity.UserDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDiagnosisRepository extends JpaRepository<UserDiagnosis, Integer> {
}

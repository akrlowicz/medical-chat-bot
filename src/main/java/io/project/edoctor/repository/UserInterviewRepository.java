package io.project.edoctor.repository;

import io.project.edoctor.model.entity.UserInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterviewRepository extends JpaRepository<UserInterview, Integer> {
        }

package io.project.edoctor.repository;

import io.project.edoctor.model.entity.User;
import io.project.edoctor.model.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {

    UserData findByUserData(User user);
}

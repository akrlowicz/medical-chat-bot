package io.project.edoctor.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class UserInterview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany
    private Set<UserDiagnosis> diagnoses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<UserDiagnosis> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<UserDiagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }
}

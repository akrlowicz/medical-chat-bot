package io.project.edoctor.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class UserInterview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_login_id", referencedColumnName = "id")
    private User user;

    @OneToMany(
            mappedBy = "userInterview",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<UserDiagnosis> userDiagnoses;

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
        user.getInterviews().add(this);
    }

    public List<UserDiagnosis> getUserDiagnoses() {
        return userDiagnoses;
    }

    public void setUserDiagnoses(List<UserDiagnosis> userDiagnoses) {
        this.userDiagnoses = userDiagnoses;
        for(UserDiagnosis userDiagnosis: userDiagnoses)
            userDiagnosis.setInterview(this);
    }
}

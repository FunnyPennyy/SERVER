
package penny.penny_backend.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student{
    @Id
    @GeneratedValue(stragetegy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JoinColumn(name = "school_id", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = true)
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = true)
    private Job job;

    @Column(nullable = true)
    private int creditRating;


    @java.lang.Override
    public java.lang.String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", classroom=" + classroom +
                '}';
    }


}
package penny.penny_back.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Data -  @toString + @getter + @setter + @RequiredArgsConstructor + @EqualsAndHashCode 생성
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classroom")
public class Classroom {

    @Id
    @GeneratedValue(stragetegy = GenerationType.IDENTITY) //자동생성
    private Long id;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false)
    private int classnum;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, unique = true)
    private int uuid;
    // 형식 협의 필요
    //private String uniqueCode = UUID.randomUUID().toString();



    @java.lang.Override
    public java.lang.String toString() {
        return "Classroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
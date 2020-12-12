package pvs.app.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Project {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long projectId;

    @NotNull
    private Long memberId;

    @NotNull
    private String name;

    @NotNull
    private String avatarURL = "https://avatars3.githubusercontent.com/u/17744001?u=038d9e068c4205d94c670d7d89fb921ec5b29782&v=4";

    @ManyToMany(cascade = { CascadeType.ALL },fetch=FetchType.EAGER)
    @JoinTable(
            name = "project_repository",
            joinColumns = { @JoinColumn(name = "project_id") },
            inverseJoinColumns = { @JoinColumn(name = "repository_id") }
    )
    private Set<Repository> repositorySet = new HashSet<>();


}

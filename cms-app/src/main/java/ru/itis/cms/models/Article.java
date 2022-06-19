package ru.itis.cms.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    public String slug;

    private String title;

    @Column(columnDefinition = "text")
    private String text;

    @OneToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
    private UserAuthority userRole;
}

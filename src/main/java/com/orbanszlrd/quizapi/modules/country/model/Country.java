package com.orbanszlrd.quizapi.modules.country.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Size(min = 2, max = 2)
    @Column(unique = true, nullable = false, length = 2)
    private String alpha2Code;

    @Size(min = 3, max = 3)
    @Column(unique = true, nullable = false, length = 3)
    private String alpha3Code;

    @Column(length = 100)
    private String capital;

    @Column(nullable = false, length = 100)
    private String subregion;

    @Column(nullable = false, length = 100)
    private String region;

    private Long population;

    private Float area;

    @Column(length = 100)
    private String flag;

    @CreationTimestamp
    @Column(nullable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updateDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return name.equals(country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}

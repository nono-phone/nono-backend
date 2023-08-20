package com.vn.aptech.smartphone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity{
    @NaturalId(mutable = true)
    private String name;
    @NaturalId(mutable = true)
    private int idParentCate;

    @JsonIgnore
    @OneToMany(mappedBy = "categories")
    private List<Product> product;
}

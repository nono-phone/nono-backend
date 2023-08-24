package com.vn.aptech.smartphone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "category_name")
    @NaturalId(mutable = true)
    private String name;
    @NaturalId(mutable = true)
    @JsonProperty(value = "parent_id")
    private Long idParentCate;

    @JsonIgnore
    @OneToMany(mappedBy = "categories")
    private List<Product> product;
}

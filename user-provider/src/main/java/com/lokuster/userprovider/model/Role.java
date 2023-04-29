package com.lokuster.userprovider.model;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    @NotNull
    @NotBlank
    private String name;

}

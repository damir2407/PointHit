package com.example.weblabb4.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RoleEntity {

    @Id
    private Integer id;
    private String name;
}

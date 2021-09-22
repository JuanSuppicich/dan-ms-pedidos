package com.durandsuppicich.danmspedidos.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "construction", schema = "ms_users")
public class Construction implements Serializable {

    @Id
    @Column(name = "construction_id")
    private Integer id;

    @Column(unique = true)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Construction{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}

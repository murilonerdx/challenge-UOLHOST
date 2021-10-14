package com.murilonerdx.uolhost.model;

import javax.persistence.*;

@Entity
@Table(name="tb_hero")
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codinome;
    private boolean obtain;
    private String nameGroup;

    public Hero(String codinome) {
        this.codinome = codinome;
    }

    public Hero(){}


    public boolean isObtain() {
        return !obtain;
    }

    public void setObtain(boolean obtain) {
        this.obtain = obtain;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodinome() {
        return codinome;
    }

    public void setCodinome(String codinome) {
        this.codinome = codinome;
    }
}

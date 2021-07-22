package com.example.demo.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Token {
    private String acces;
    private String idarticolo;

    @Basic
    @Column(name = "acces", nullable = true, length = 2000)
    public String getAcces() {
        return acces;
    }

    public void setAcces(String acces) {
        this.acces = acces;
    }

    @Id
    @Column(name = "idarticolo", nullable = true, length = -1)
    public String getIdarticolo() {
        return idarticolo;
    }

    public void setIdarticolo(String idarticolo) {
        this.idarticolo = idarticolo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(acces, token.acces) &&
                Objects.equals(idarticolo, token.idarticolo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acces, idarticolo);
    }
}

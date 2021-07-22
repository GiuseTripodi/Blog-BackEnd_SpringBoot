package com.example.demo.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "blog_articles", schema = "blog_progetto_sistemi_distribuiti", catalog = "")
public class BlogArticles {
    private long id;
    private Long musica;
    private Long filosofia;
    private Long politica;
    private Long scienza;
    private Long storia;
    private Long giochi;
    private Long libri;
    private Long cibo;
    private Long internet;
    private Long economia;
    private Long medicina;
    private String author;
    private Date time;
    private String title;
    private String text;
    private Long numDislike;
    private Long numLike;
    private Long attualitÃ;
    private Long scuola;
    private Long legge;
    private Long datasource;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "musica", nullable = true)
    public Long getMusica() {
        return musica;
    }

    public void setMusica(Long musica) {
        this.musica = musica;
    }

    @Basic
    @Column(name = "filosofia", nullable = true)
    public Long getFilosofia() {
        return filosofia;
    }

    public void setFilosofia(Long filosofia) {
        this.filosofia = filosofia;
    }

    @Basic
    @Column(name = "politica", nullable = true)
    public Long getPolitica() {
        return politica;
    }

    public void setPolitica(Long politica) {
        this.politica = politica;
    }

    @Basic
    @Column(name = "scienza", nullable = true)
    public Long getScienza() {
        return scienza;
    }

    public void setScienza(Long scienza) {
        this.scienza = scienza;
    }

    @Basic
    @Column(name = "storia", nullable = true)
    public Long getStoria() {
        return storia;
    }

    public void setStoria(Long storia) {
        this.storia = storia;
    }

    @Basic
    @Column(name = "giochi", nullable = true)
    public Long getGiochi() {
        return giochi;
    }

    public void setGiochi(Long giochi) {
        this.giochi = giochi;
    }

    @Basic
    @Column(name = "libri", nullable = true)
    public Long getLibri() {
        return libri;
    }

    public void setLibri(Long libri) {
        this.libri = libri;
    }

    @Basic
    @Column(name = "cibo", nullable = true)
    public Long getCibo() {
        return cibo;
    }

    public void setCibo(Long cibo) {
        this.cibo = cibo;
    }

    @Basic
    @Column(name = "internet", nullable = true)
    public Long getInternet() {
        return internet;
    }

    public void setInternet(Long internet) {
        this.internet = internet;
    }

    @Basic
    @Column(name = "economia", nullable = true)
    public Long getEconomia() {
        return economia;
    }

    public void setEconomia(Long economia) {
        this.economia = economia;
    }

    @Basic
    @Column(name = "medicina", nullable = true)
    public Long getMedicina() {
        return medicina;
    }

    public void setMedicina(Long medicina) {
        this.medicina = medicina;
    }

    @Basic
    @Column(name = "author", nullable = true, length = 13)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "time", nullable = true)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 159)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "text", nullable = true, length = -1)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "num_dislike", nullable = true)
    public Long getNumDislike() {
        return numDislike;
    }

    public void setNumDislike(Long numDislike) {
        this.numDislike = numDislike;
    }

    @Basic
    @Column(name = "num_like", nullable = true)
    public Long getNumLike() {
        return numLike;
    }

    public void setNumLike(Long numLike) {
        this.numLike = numLike;
    }

    @Basic
    @Column(name = "attualitÃ ", nullable = true)
    public Long getAttualitÃ() {
        return attualitÃ;
    }

    public void setAttualitÃ(Long attualitÃ) {
        this.attualitÃ = attualitÃ;
    }

    @Basic
    @Column(name = "scuola", nullable = true)
    public Long getScuola() {
        return scuola;
    }

    public void setScuola(Long scuola) {
        this.scuola = scuola;
    }

    @Basic
    @Column(name = "legge", nullable = true)
    public Long getLegge() {
        return legge;
    }

    public void setLegge(Long legge) {
        this.legge = legge;
    }

    @Basic
    @Column(name = "datasource", nullable = true)
    public Long getDatasource() {
        return datasource;
    }

    public void setDatasource(Long datasource) {
        this.datasource = datasource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogArticles that = (BlogArticles) o;
        return id == that.id &&
                Objects.equals(musica, that.musica) &&
                Objects.equals(filosofia, that.filosofia) &&
                Objects.equals(politica, that.politica) &&
                Objects.equals(scienza, that.scienza) &&
                Objects.equals(storia, that.storia) &&
                Objects.equals(giochi, that.giochi) &&
                Objects.equals(libri, that.libri) &&
                Objects.equals(cibo, that.cibo) &&
                Objects.equals(internet, that.internet) &&
                Objects.equals(economia, that.economia) &&
                Objects.equals(medicina, that.medicina) &&
                Objects.equals(author, that.author) &&
                Objects.equals(time, that.time) &&
                Objects.equals(title, that.title) &&
                Objects.equals(text, that.text) &&
                Objects.equals(numDislike, that.numDislike) &&
                Objects.equals(numLike, that.numLike) &&
                Objects.equals(attualitÃ, that.attualitÃ) &&
                Objects.equals(scuola, that.scuola) &&
                Objects.equals(legge, that.legge) &&
                Objects.equals(datasource, that.datasource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, musica, filosofia, politica, scienza, storia, giochi, libri, cibo, internet, economia, medicina, author, time, title, text, numDislike, numLike, attualitÃ, scuola, legge, datasource);
    }
}

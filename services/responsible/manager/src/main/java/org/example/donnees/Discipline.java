/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.donnees;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author sully.millet
 */
@Entity
@Table(name = "discipline")
@NamedQueries({
    @NamedQuery(name = "Discipline.findAll", query = "SELECT d FROM Discipline d"),
    @NamedQuery(name = "Discipline.findByCodeDiscipline", query = "SELECT d FROM Discipline d WHERE d.codeDiscipline = :codeDiscipline"),
    @NamedQuery(name = "Discipline.findByIntitule", query = "SELECT d FROM Discipline d WHERE d.intitule = :intitule")})
public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "code_discipline")
    private Integer codeDiscipline;
    @Column(name = "intitule")
    private String intitule;
    @ManyToMany(mappedBy = "disciplines")
    private Set<Sportif> sportifSet = new HashSet<>();
    @JoinColumn(name = "code_sport", referencedColumnName = "code_sport")
    @ManyToOne
    private Sport sport;

    public Discipline() {
    }

    public Discipline(Integer codeDiscipline) {
        this.codeDiscipline = codeDiscipline;
    }

    public Integer getCodeDiscipline() {
        return codeDiscipline;
    }

    public void setCodeDiscipline(Integer codeDiscipline) {
        this.codeDiscipline = codeDiscipline;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Set<Sportif> getSportifs() {
        return sportifSet;
    }

    public void setSportifs(Set<Sportif> sportifSet) {
        this.sportifSet = sportifSet;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport codeSport) {
        this.sport = codeSport;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeDiscipline != null ? codeDiscipline.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Discipline)) {
            return false;
        }
        Discipline other = (Discipline) object;
        if ((this.codeDiscipline == null && other.codeDiscipline != null) || (this.codeDiscipline != null && !this.codeDiscipline.equals(other.codeDiscipline))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.Discipline[ codeDiscipline=" + codeDiscipline + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bll.PersistenceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rike4
 */
@Entity
@Table(name = "TIPO_CONSULTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoConsulta.findAll", query = "SELECT t FROM TipoConsulta t"),
    @NamedQuery(name = "TipoConsulta.findById", query = "SELECT t FROM TipoConsulta t WHERE t.id = :id"),
    @NamedQuery(name = "TipoConsulta.findByNome", query = "SELECT t FROM TipoConsulta t WHERE t.nome = :nome"),
    @NamedQuery(name = "TipoConsulta.findByValor", query = "SELECT t FROM TipoConsulta t WHERE t.valor = :valor")
})
public class TipoConsulta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
     @SequenceGenerator(
            name = "TIPO_CONSULTA_SEQ",
            sequenceName = "TIPO_CONSULTA_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_CONSULTA_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private double valor;
    @OneToMany(mappedBy = "idTipo")
    private List<Consulta> consultaCollection;
    
    private static EntityManager em;

    public TipoConsulta() {
        em = PersistenceManager.getEntityManager();
        consultaCollection = new ArrayList<>();
    }

    public TipoConsulta(Integer id) {
        em = PersistenceManager.getEntityManager();
        consultaCollection = new ArrayList<>();
        this.id = id;
    }

    public TipoConsulta(String nome, Double valor) {
        em = PersistenceManager.getEntityManager();
        consultaCollection = new ArrayList<>();
        this.nome = nome;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public Collection<Consulta> getConsultaCollection() {
        return consultaCollection;
    }

    public void setConsultaCollection(List<Consulta> consultaCollection) {
        this.consultaCollection = consultaCollection;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoConsulta)) {
            return false;
        }
        TipoConsulta other = (TipoConsulta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TipoConsulta[ id=" + id + " ]";
    }
    
    public int createT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.persist((TipoConsulta)this);
        em.getTransaction().commit();
        
        return this.getId();
    }
    
    public int create(){
        em = PersistenceManager.getEntityManager();
        em.persist((TipoConsulta)this);
        return this.getId();
    }

    public void updateT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.merge((TipoConsulta)this);
        em.getTransaction().commit();
    }
    
    public static List<TipoConsulta> retrieveAll() {
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("TipoConsulta.findAll");
        return query.getResultList();
    }

}

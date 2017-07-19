/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bll.PersistenceManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "LINHA_ARTIGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LinhaArtigo.findAll", query = "SELECT l FROM LinhaArtigo l"),
    @NamedQuery(name = "LinhaArtigo.findById", query = "SELECT l FROM LinhaArtigo l WHERE l.id = :id"),
    @NamedQuery(name = "LinhaArtigo.findByQuantidade", query = "SELECT l FROM LinhaArtigo l WHERE l.quantidade = :quantidade")})
public class LinhaArtigo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "LINHAARTIGO_SEQ",
            sequenceName = "LINHAARTIGO_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "LINHAARTIGO_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "QUANTIDADE")
    private int quantidade;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private double valor;
    @JoinColumn(name = "ID_VENDA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Venda idVenda;
    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Produto produto;

    private static EntityManager em;
    
    public LinhaArtigo() {
    }

    public LinhaArtigo(Integer id) {
        this.id = id;
    }

    public LinhaArtigo(Integer id, int quantidade) {
        this.id = id;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Venda getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Venda idVenda) {
        this.idVenda = idVenda;
    }


    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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
        if (!(object instanceof LinhaArtigo)) {
            return false;
        }
        LinhaArtigo other = (LinhaArtigo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.LinhaArtigo[ id=" + id + " ]";
    }
    
    public int createT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.persist((LinhaArtigo)this);
        em.getTransaction().commit();
        
        return this.getId();
    }
    
    public int create(){
        em = PersistenceManager.getEntityManager();
        em.persist((LinhaArtigo)this);
        return this.getId();
    }

    public void updateT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.merge((LinhaArtigo)this);
        em.getTransaction().commit();
    }
    
    public static List<LinhaArtigo> retrieveAll() {
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("LinhaArtigo.findAll");
        return query.getResultList();
    }

}

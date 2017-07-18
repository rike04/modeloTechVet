/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bll.PersistenceManager;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
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
@Table(name = "TIPO_PRODUTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProduto.findAll", query = "SELECT t FROM TipoProduto t"),
    @NamedQuery(name = "TipoProduto.findById", query = "SELECT t FROM TipoProduto t WHERE t.id = :id"),
    @NamedQuery(name = "TipoProduto.findByNome", query = "SELECT t FROM TipoProduto t WHERE t.nome = :nome"),
    @NamedQuery(name = "TipoProduto.findByDescricao", query = "SELECT t FROM TipoProduto t WHERE t.descricao = :descricao")})
public class TipoProduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "TIPOPRODUTO_SEQ",
            sequenceName = "TIPOPRODUTO_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TIPOPRODUTO_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codTipo", fetch = FetchType.LAZY)
    private List<Produto> listaProdutos;
    
    private static EntityManager em;

    public TipoProduto() {
    }

    public TipoProduto(Integer id) {
        this.id = id;
    }

    public TipoProduto(Integer id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Produto> getProdutoList() {
        return listaProdutos;
    }

    public void setProdutoList(List<Produto> produtoCollection) {
        this.listaProdutos = produtoCollection;
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
        if (!(object instanceof TipoProduto)) {
            return false;
        }
        TipoProduto other = (TipoProduto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TipoProduto[ id=" + id + " ]";
    }
    
        public int createT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.persist((TipoProduto)this);
        em.getTransaction().commit();
        
        return this.getId();
    }
    
    public int create(){
        em = PersistenceManager.getEntityManager();
        em.persist((TipoProduto)this);
        return this.getId();
    }

    public void updateT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.merge((TipoProduto)this);
        em.getTransaction().commit();
    }
    
    public static List<TipoProduto> retrieveAll() {
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("TipoProduto.findAll");
        return query.getResultList();
    }

    
}

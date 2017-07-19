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
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Henrique Faria e Sergio Araujo
 */
@Entity
@Table(name = "INTE_RETIRA_PROD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InteRetiraProd.findAll", query = "SELECT i FROM InteRetiraProd i"),
    @NamedQuery(name = "InteRetiraProd.findByIdProduto", query = "SELECT i FROM InteRetiraProd i WHERE i.inteRetiraProdPK.idProduto = :idProduto"),
    @NamedQuery(name = "InteRetiraProd.findByIdInternamento", query = "SELECT i FROM InteRetiraProd i WHERE i.inteRetiraProdPK.idInternamento = :idInternamento"),
    @NamedQuery(name = "InteRetiraProd.findByQuantidade", query = "SELECT i FROM InteRetiraProd i WHERE i.quantidade = :quantidade")})
public class InteRetiraProd implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InteRetiraProdPK inteRetiraProdPK;
    @Basic(optional = false)
    @Column(name = "QUANTIDADE")
    private int quantidade;
    @JoinColumn(name = "ID_INTERNAMENTO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Internamento internamento;
    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Produto produto;

    private static EntityManager em;
    
    public InteRetiraProd() {
        em = PersistenceManager.getEntityManager();
    }

    public InteRetiraProd(InteRetiraProdPK inteRetiraProdPK) {
        em = PersistenceManager.getEntityManager();
        this.inteRetiraProdPK = inteRetiraProdPK;
    }

    public InteRetiraProd(InteRetiraProdPK inteRetiraProdPK, int quantidade) {
        em = PersistenceManager.getEntityManager();
        this.inteRetiraProdPK = inteRetiraProdPK;
        this.quantidade = quantidade;
    }

    public InteRetiraProd(int idProduto, int idInternamento) {
        em = PersistenceManager.getEntityManager();
        this.inteRetiraProdPK = new InteRetiraProdPK(idProduto, idInternamento);
    }

    public InteRetiraProdPK getInteRetiraProdPK() {
        return inteRetiraProdPK;
    }

    public void setInteRetiraProdPK(InteRetiraProdPK inteRetiraProdPK) {
        this.inteRetiraProdPK = inteRetiraProdPK;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Internamento getInternamento() {
        return internamento;
    }

    public void setInternamento(Internamento internamento) {
        this.internamento = internamento;
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
        hash += (inteRetiraProdPK != null ? inteRetiraProdPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InteRetiraProd)) {
            return false;
        }
        InteRetiraProd other = (InteRetiraProd) object;
        if ((this.inteRetiraProdPK == null && other.inteRetiraProdPK != null) || (this.inteRetiraProdPK != null && !this.inteRetiraProdPK.equals(other.inteRetiraProdPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.InteRetiraProd[ inteRetiraProdPK=" + inteRetiraProdPK + " ]";
    }
    
    public void read(Internamento internamento){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("InteRetiraProd.findByIdInternamento");
        query.setParameter("idInternamento", internamento);
        
        InteRetiraProd i = (InteRetiraProd) query.getSingleResult();
        this.setProduto(i.getProduto());
        this.setQuantidade(i.getQuantidade());
        this.setInternamento(i.getInternamento());
        this.setInteRetiraProdPK(i.getInteRetiraProdPK());
    }

    public static List<InteRetiraProd> retrieveAll(){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("InteRetiraProd.findAll");
        return query.getResultList();
    }
    
}

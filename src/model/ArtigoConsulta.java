/*
 * 
 */

package model;

import bll.PersistenceManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "ARTIGO_CONSULTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArtigoConsulta.findAll", query = "SELECT a FROM ArtigoConsulta a"),
    @NamedQuery(name = "ArtigoConsulta.findById", query = "SELECT a FROM ArtigoConsulta a WHERE a.id = :id"),
    @NamedQuery(name = "ArtigoConsulta.findByQuantidade", query = "SELECT a FROM ArtigoConsulta a WHERE a.quantidade = :quantidade")})
public class ArtigoConsulta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "ARTIGOCONSULTA_SEQ",
            sequenceName = "ARTIGOCONSULTA_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ARTIGOCONSULTA_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "QUANTIDADE")
    private int quantidade;
    @ManyToOne(fetch = FetchType.LAZY)
    private Consulta consulta;
    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Produto idProduto;
    
    private static EntityManager em;

    public ArtigoConsulta() {
        em = PersistenceManager.getEntityManager();
    }

    public ArtigoConsulta(Integer id) {
        em = PersistenceManager.getEntityManager();
        this.id = id;
    }

    public ArtigoConsulta(Integer id, int quantidade) {
        em = PersistenceManager.getEntityManager();
        this.id = id;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @XmlTransient
    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Produto getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Produto idProduto) {
        this.idProduto = idProduto;
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
        if (!(object instanceof ArtigoConsulta)) {
            return false;
        }
        ArtigoConsulta other = (ArtigoConsulta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ArtigoConsulta[ id=" + id + " ]";
    }
    
    public int createT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.persist((ArtigoConsulta)this);
        em.getTransaction().commit();
        
        return this.getId();
    }
   
    public int create(){
        em = PersistenceManager.getEntityManager();
        em.persist((ArtigoConsulta)this);
        return this.getId();
    }

    public void updateT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.merge((ArtigoConsulta)this);
        em.getTransaction().commit();
    }
    
    public void read(int idArtigoConsulta){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("ArtigoConsulta.findById");
        query.setParameter("id", idArtigoConsulta);
        
        ArtigoConsulta art = (ArtigoConsulta) query.getSingleResult();
        this.setId(art.getId());
        this.setIdProduto(art.getIdProduto());
        this.setConsulta(art.getConsulta());
        this.setQuantidade(art.getQuantidade());
        
        System.out.println("ID = " + this.getId());
    }

    public static List<ArtigoConsulta> retrieveAll(){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("ArtigoConsulta.findAll");
        return query.getResultList();
    }
    
//    public static void remove(int idEliminar) {
//        PersistenceManager.getEntityManager().getTransaction().begin();
//        Query query = em.createNamedQuery("Cliente.removeById");
//        query.setParameter("id", idEliminar);
//        query.executeUpdate();
//        PersistenceManager.getEntityManager().getTransaction().commit();
//    }
    
}

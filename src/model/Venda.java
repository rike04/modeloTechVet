/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bll.PersistenceManager;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Henrique Faria e Sergio Araujo
 */
@Entity
@Table(name = "VENDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venda.findAll", query = "SELECT v FROM Venda v"),
    @NamedQuery(name = "Venda.findById", query = "SELECT v FROM Venda v WHERE v.id = :id"),
    @NamedQuery(name = "Venda.findByData", query = "SELECT v FROM Venda v WHERE v.data = :data"),
    @NamedQuery(name = "Venda.findByTotal", query = "SELECT v FROM Venda v WHERE v.total = :total")})
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "VENDA_SEQ",
            sequenceName = "VENDA_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "VENDA_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "TOTAL")
    private double total;
    @Basic(optional = false)
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cliente idCliente;
    @JoinColumn(name = "ID_LINHA_ART", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private LinhaArtigo idLinhaArt;
    
    private static EntityManager em;

    public Venda() {
        
    }

    public Venda(Integer id) {
        this.id = id;
    }

    public Venda(Integer id, Date data, double total) {
        this.id = id;
        this.data = data;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }


    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public LinhaArtigo getIdLinhaArt() {
        return idLinhaArt;
    }

    public void setIdLinhaArt(LinhaArtigo idLinhaArt) {
        this.idLinhaArt = idLinhaArt;
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
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Venda[ id=" + id + " ]";
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public int createT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.persist((Venda)this);
        em.getTransaction().commit();
        
        return this.getId();
    }
    
    public int create(){
        em = PersistenceManager.getEntityManager();
        em.persist((Venda)this);
        return this.getId();
    }

    public void updateT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.merge((Venda)this);
        em.getTransaction().commit();
    }
    
    public void read(int idVenda){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Venda.findById");
        query.setParameter("id", idVenda);
        
        Venda v = (Venda) query.getSingleResult();
        this.setId(v.getId());
        this.setData(v.getData());
        this.setIdCliente(v.getIdCliente());
        this.setIdLinhaArt(v.getIdLinhaArt());
        this.setTotal(v.getTotal());
        
        System.out.println("ID = " + this.getId());
    }

    public static List<Venda> retrieveAll(){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Venda.findAll");
        return query.getResultList();
    }
    
    public static List<Venda> retrieveByData(Date data) {
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Venda.findByData");
        query.setParameter("data", data);
        List<Venda> lista = query.getResultList();
        return lista;
    }
    
//    public static void remove(int idEliminar) {
//        PersistenceManager.getEntityManager().getTransaction().begin();
//        Query query = em.createNamedQuery("Cliente.removeById");
//        query.setParameter("id", idEliminar);
//        query.executeUpdate();
//        PersistenceManager.getEntityManager().getTransaction().commit();
//    }
    
}

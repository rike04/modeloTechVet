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
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Henrique Faria e Sergio Araujo
 */
@Entity
@Table(name = "UTILIZADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilizador.findAll", query = "SELECT u FROM Utilizador u"),
    @NamedQuery(name = "Utilizador.findById", query = "SELECT u FROM Utilizador u WHERE u.id = :id"),
    @NamedQuery(name = "Utilizador.findByNome", query = "SELECT u FROM Utilizador u WHERE u.nome = :nome"),
    @NamedQuery(name = "Utilizador.findByUsername", query = "SELECT u FROM Utilizador u WHERE u.username = :username"),
    @NamedQuery(name = "Utilizador.findByPassword", query = "SELECT u FROM Utilizador u WHERE u.password = :password"),
    @NamedQuery(name = "Utilizador.findByFuncao", query = "SELECT u FROM Utilizador u WHERE u.funcao = :funcao")})
public class Utilizador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "UTILIZADOR_SEQ",
            sequenceName = "UTILIZADOR_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "UTILIZADOR_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "FUNCAO")
    private String funcao;
    
    private static EntityManager em;

    public Utilizador() {
        em = PersistenceManager.getEntityManager();
    }

    public Utilizador(Integer id) {
        em = PersistenceManager.getEntityManager();
        this.id = id;
    }

    public Utilizador(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        em = PersistenceManager.getEntityManager();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
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
        if (!(object instanceof Utilizador)) {
            return false;
        }
        Utilizador other = (Utilizador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Utilizador[ id=" + id + " ]";
    }
    
      /**
     * Create Transacional: Cria um cliente de forma transacional
     * @return ID do cliente criado
     */
    public int createT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.persist((Utilizador)this);
        em.getTransaction().commit();
        
        return this.getId();
    }
    
    /**
     * Create não transacional: Permite criar um cliente 
     * dentro de uma transação complexa, iniciada anteriormente...
     * @return ID do cliente criado
     */
    public int create(){
        em = PersistenceManager.getEntityManager();
        em.persist((Utilizador)this);
        return this.getId();
    }

    public void updateT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.merge((Utilizador)this);
        em.getTransaction().commit();
    }
    
    /**
     * Lê um cliente da BD
     * @param idcliente ID do cliente a ler da BD
     */    
    public void read(int idcliente){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Utilizador.findById");
        query.setParameter("id", idcliente);
        
        Cliente cli = (Cliente) query.getSingleResult();
        this.setId(cli.getId());
        this.setNome(cli.getNome());
        
        
        System.out.println("ID = " + this.getId());
    }
    
     public void readByNome(String nomeUtilizador) throws Exception{
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Utilizador.findByUsername");
        query.setParameter("username", nomeUtilizador);
        
        Utilizador uti = (Utilizador) query.getSingleResult();
        this.setId(uti.getId());
        this.setNome(uti.getNome());
        this.setFuncao(uti.getFuncao());
        this.setPassword(uti.getPassword());
        this.setUsername(uti.getUsername());
        
        System.out.println(uti.getUsername());
    }
     
     public static List<Utilizador> retrieveAll() {
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Utilizador.findAll");
        return query.getResultList();
     }
     
}

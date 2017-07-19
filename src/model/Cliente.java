/*
 * 
 */
package model;

import bll.PersistenceManager;
import java.io.Serializable;
import java.util.ArrayList;
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
 * @author Henrique Faria e Sergio Araujo
 */
@Entity
@Table(name = "CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findById", query = "SELECT c FROM Cliente c WHERE c.id = :id"),
    @NamedQuery(name = "Cliente.findByNome", query = "SELECT c FROM Cliente c WHERE c.nome = :nome"),
    @NamedQuery(name = "Cliente.findByMorada", query = "SELECT c FROM Cliente c WHERE c.morada = :morada"),
    @NamedQuery(name = "Cliente.findByTelemovel", query = "SELECT c FROM Cliente c WHERE c.telemovel = :telemovel"),
    @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email"),
    @NamedQuery(name = "Cliente.findByNif", query = "SELECT c FROM Cliente c WHERE c.nif = :nif"),
    @NamedQuery(name = "Cliente.removeById", query = "DELETE FROM Cliente c WHERE c.id = :id"),
    @NamedQuery(name = "Cliente.findByLetters", query = "SELECT c FROM Cliente c WHERE c.nome LIKE CONCAT(:nome, '%')")
})

public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "CLIENTE_SEQ",
            sequenceName = "CLIENTE_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CLIENTE_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @Column(name = "MORADA")
    private String morada;
    @Basic(optional = false)
    @Column(name = "TELEMOVEL")
    private String telemovel;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "NIF")
    private String nif;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente", fetch = FetchType.LAZY)
    private List<Venda> listaVendas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Paciente> listaPacientes;
    
    private static EntityManager em;

    public Cliente() {
        listaPacientes = new ArrayList<>();
        listaVendas = new ArrayList<>();
    }

    public Cliente(Integer id) {
        em = PersistenceManager.getEntityManager();
        this.id = id;
    }

    public Cliente(Integer id, String nome, String morada, String telemovel, String email, String nif) {
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.telemovel = telemovel;
        this.email = email;
        this.nif = nif;
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

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    @XmlTransient
    public List<Venda> getListaVendas() {
        return listaVendas;
    }

    public void setListaVendas(List<Venda> listaVendas) {
        this.listaVendas = listaVendas;
    }

    @XmlTransient
    public List<Paciente> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Paciente> listaPacientes) {
        this.listaPacientes = listaPacientes;
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cliente[ id=" + id + " ]";
    }
    
     /**
     * Create Transacional: Cria um cliente de forma transacional
     * @return ID do cliente criado
     */
    public int createT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.persist((Cliente)this);
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
        em.persist((Cliente)this);
        return this.getId();
    }

    public void updateT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.merge((Cliente)this);
        em.getTransaction().commit();
    }
    
    /**
     * Lê um cliente da BD
     * @param idcliente ID do cliente a ler da BD
     */    
    public void read(int idcliente){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Cliente.findById");
        query.setParameter("id", idcliente);
        
        Cliente cli = (Cliente) query.getSingleResult();
        this.setId(cli.getId());
        this.setNome(cli.getNome());
        this.setMorada(cli.getMorada());
        this.setEmail(cli.getEmail());
        this.setTelemovel(cli.getTelemovel());
        this.setNif(cli.getNif());   
        this.setListaVendas(cli.getListaVendas());
        this.setListaPacientes(cli.getListaPacientes());
        
        System.out.println("ID = " + this.getId());
    }

    /**
     * Lê os clientes com o nome indicado
     * @param nome Nome dos clientes a ler da BD
     * @return Lista com clientes que satisfazem o critério de seleçção
     */
    public static List<Cliente> readByNome(String nome){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Cliente.findByLetters");
        query.setParameter("nome", nome);
        List<Cliente> lista = query.getResultList();
        return lista;
    }

    public static List<Cliente> retrieveAll(){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Cliente.findAll");
        return query.getResultList();
    }
    
    public static List<Cliente> retrieveByNIF(String nif) {
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Cliente.findByNif");
        query.setParameter("nif", nif);
        List<Cliente> lista = query.getResultList();
        return lista;
    }
    
    public static void remove(int idEliminar) {
        PersistenceManager.getEntityManager().getTransaction().begin();
        Query query = em.createNamedQuery("Cliente.removeById");
        query.setParameter("id", idEliminar);
        query.executeUpdate();
        PersistenceManager.getEntityManager().getTransaction().commit();
    }
    
}

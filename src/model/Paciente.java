/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bll.PersistenceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rike4
 */
@Entity
@Table(name = "PACIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paciente.findAll", query = "SELECT p FROM Paciente p"),
    @NamedQuery(name = "Paciente.findById", query = "SELECT p FROM Paciente p WHERE p.id = :id"),
    @NamedQuery(name = "Paciente.findByNome", query = "SELECT p FROM Paciente p WHERE p.nome = :nome"),
    @NamedQuery(name = "Paciente.findByEspecie", query = "SELECT p FROM Paciente p WHERE p.especie = :especie"),
    @NamedQuery(name = "Paciente.findByRaca", query = "SELECT p FROM Paciente p WHERE p.raca = :raca"),
    @NamedQuery(name = "Paciente.findBySexo", query = "SELECT p FROM Paciente p WHERE p.sexo = :sexo"),
    @NamedQuery(name = "Paciente.findByCor", query = "SELECT p FROM Paciente p WHERE p.cor = :cor"),
    @NamedQuery(name = "Paciente.findByDatanasc", query = "SELECT p FROM Paciente p WHERE p.datanasc = :datanasc"),
    @NamedQuery(name = "Paciente.findByFoto", query = "SELECT p FROM Paciente p WHERE p.foto = :foto"),
    @NamedQuery(name = "Paciente.findByHistorico", query = "SELECT p FROM Paciente p WHERE p.historico = :historico"),
    @NamedQuery(name = "Paciente.findByEstado", query = "SELECT p FROM Paciente p WHERE p.estado = :estado"),
    @NamedQuery(name = "Paciente.removeById", query = "DELETE FROM Paciente p WHERE p.id = :id")
})

public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "PACIENTE_SEQ",
            sequenceName = "PACIENTE_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PACIENTE_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @Column(name = "ESPECIE")
    private String especie;
    @Basic(optional = false)
    @Column(name = "RACA")
    private String raca;
    @Basic(optional = false)
    @Column(name = "SEXO")
    private String sexo;
    @Column(name = "COR")
    private String cor;
    @Basic(optional = false)
    @Column(name = "DATANASC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datanasc;
    @Column(name = "FOTO")
    private String foto;
    @Column(name = "HISTORICO")
    private String historico;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private short estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private List<Internamento> internamentoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private List<Consulta> consultaCollection;
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cliente idCliente;
    
    private static EntityManager em;

    public Paciente() {
        em = PersistenceManager.getEntityManager();
        internamentoCollection = new ArrayList<>();
        consultaCollection = new ArrayList<>();
        historico = new String();
        foto = new String();
    }

    public Paciente(Integer id) {
        em = PersistenceManager.getEntityManager();
        this.id = id;
        internamentoCollection = new ArrayList<>();
        consultaCollection = new ArrayList<>();
    }

    public Paciente(Integer id, String nome, String especie, String raca, String sexo, Date datanasc, short estado) {
        em = PersistenceManager.getEntityManager();
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.sexo = sexo;
        this.datanasc = datanasc;
        this.estado = estado;
        internamentoCollection = new ArrayList<>();
        consultaCollection = new ArrayList<>();
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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Date getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Internamento> getInternamentoCollection() {
        return internamentoCollection;
    }

    public void setInternamentoCollection(List<Internamento> internamentoCollection) {
        this.internamentoCollection = internamentoCollection;
    }

    @XmlTransient
    public List<Consulta> getConsultaCollection() {
        return consultaCollection;
    }

    public void setConsultaCollection(List<Consulta> consultaCollection) {
        this.consultaCollection = consultaCollection;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
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
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Paciente[ id=" + id + " ]";
    }
    
        public int createT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.persist((Paciente)this);
        em.getTransaction().commit();
        
        return this.getId();
    }
    
    public int create(){
        em = PersistenceManager.getEntityManager();
        em.persist( (Paciente) this);
        return this.getId();
    }

    public void updateT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.merge( (Paciente) this);
        em.getTransaction().commit();
       
    }
    
    public void addConsulta(Consulta c) {
        this.getConsultaCollection().add(c);
    }
    
    public void remove(int idEliminar) {
        PersistenceManager.getEntityManager().getTransaction().begin();
        Query query = em.createNamedQuery("Paciente.removeById");
        query.setParameter("id", idEliminar);
        query.executeUpdate();
        PersistenceManager.getEntityManager().getTransaction().commit();
    }
    
    public void readById(int id) {        
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Paciente.findById");
        query.setParameter("id", id);
        
        Paciente p = (Paciente) query.getSingleResult();
        this.setId(p.getId());
        this.setNome(p.getNome());
        this.setEspecie(p.getEspecie());
        this.setCor(p.getCor());
        this.setDatanasc(p.getDatanasc());
        this.setEstado(p.getEstado());
        this.setIdCliente(p.getIdCliente());
        this.setFoto(p.getFoto());
        this.setHistorico(p.getHistorico());
        this.setSexo(p.getSexo());
        this.setRaca(p.getRaca());
        this.setInternamentoCollection(p.getInternamentoCollection());
        this.setConsultaCollection(p.getConsultaCollection());
        
        System.out.println("ID = " + this.getId());
    }
    
    public static List<Paciente> retrivePacientesVivos() {
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Paciente.findByEstado");
        query.setParameter("estado", (short) 1);
        
        return query.getResultList();
    }
    
    public static List<Paciente> retrievePacientesbyNome(String nome) {
       em = PersistenceManager.getEntityManager();
       Query query = em.createNamedQuery("Paciente.findByNome");
       query.setParameter("nome", nome);
       
       return query.getResultList();
    }
    
    public static List<Paciente> retrieveAll() {
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Paciente.findAll");
        
        return query.getResultList();
    }
   
}

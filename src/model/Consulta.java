package model;

import bll.PersistenceManager;
import bll.Util;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "CONSULTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consulta.findAll", query = "SELECT c FROM Consulta c"),
    @NamedQuery(name = "Consulta.findById", query = "SELECT c FROM Consulta c WHERE c.id = :id"),
    @NamedQuery(name = "Consulta.findByLocal", query = "SELECT c FROM Consulta c WHERE c.local = :local"),
    @NamedQuery(name = "Consulta.findByEstado", query = "SELECT c FROM Consulta c WHERE c.estado = :estado"),
    @NamedQuery(name = "Consulta.findByPago", query = "SELECT c FROM Consulta c WHERE c.pago = :pago"),
    @NamedQuery(name = "Consulta.findByDesctratamento", query = "SELECT c FROM Consulta c WHERE c.desctratamento = :desctratamento"),
    @NamedQuery(name = "Consulta.findByDatahora", query = "SELECT c FROM Consulta c WHERE c.datahora LIKE :datahora"),
    @NamedQuery(name = "Consulta.findByValor", query = "SELECT c FROM Consulta c WHERE c.valor = :valor"),
    @NamedQuery(name = "Consulta.removeById", query = "DELETE FROM Consulta c WHERE c.id = :id")
})
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "CONSULTA_SEQ",
            sequenceName = "CONSULTA_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSULTA_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "LOCAL")
    private String local;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private double valor;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private short estado;
    @Basic(optional = false)
    @Column(name = "PAGO")
    private short pago;
    @Column(name = "DESCTRATAMENTO")
    private String desctratamento;
    @Column(name = "DATAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datahora; 
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ArtigoConsulta> listaArtigosConsulta;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "consulta", fetch = FetchType.LAZY)
    private Receita receita;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idConsulta", fetch = FetchType.LAZY)
    private Internamento internamento;
    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Paciente paciente;
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID")
    @ManyToOne
    private TipoConsulta tipoConsulta;
    
    private static EntityManager em;
    
    public Consulta() {
        em = PersistenceManager.getEntityManager();
    }

    public Consulta(Integer id) {
        em = PersistenceManager.getEntityManager();
        this.id = id;
    }

    public Consulta(Integer id, String local, short estado, short pago, double valor) {
        em = PersistenceManager.getEntityManager();
        this.id = id;
        this.local = local;
        this.estado = estado;
        this.pago = pago;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public short getPago() {
        return pago;
    }

    public void setPago(short pago) {
        this.pago = pago;
    }

    public String getDesctratamento() {
        return desctratamento;
    }

    public void setDesctratamento(String desctratamento) {
        this.desctratamento = desctratamento;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }


    @XmlTransient
    public List<ArtigoConsulta> getListaArtigosConsulta() {
        return listaArtigosConsulta;
    }

    public void setListaArtigosConsulta(List<ArtigoConsulta> listaArtigosConsulta) {
        this.listaArtigosConsulta = listaArtigosConsulta;
    }

    @XmlTransient
    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    @XmlTransient
    public Internamento getInternamento() {
        return internamento;
    }

    public void setInternamento(Internamento internamento) {
        this.internamento = internamento;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public TipoConsulta getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(TipoConsulta tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Consulta)) {
            return false;
        }
        Consulta other = (Consulta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Consulta[ id=" + id + " ]";
    }
   
    public int createT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.persist((Consulta)this);
        em.getTransaction().commit();
        
        return this.getId();
    }

    public int create(){
        em = PersistenceManager.getEntityManager();
        em.persist((Consulta)this);
        return this.getId();
    }

    public void updateT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.merge((Consulta)this);
        em.getTransaction().commit();
    }
    
    public void readById(int idConsulta){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Consulta.findById");
        query.setParameter("id", idConsulta);
        
        Consulta cnt = (Consulta) query.getSingleResult();
        this.setId(cnt.getId());
        this.setListaArtigosConsulta(cnt.getListaArtigosConsulta());
        this.setDatahora(cnt.getDatahora());
        this.setDesctratamento(cnt.getDesctratamento());
        this.setEstado(cnt.getEstado());
        this.setPaciente(cnt.getPaciente());
        this.setTipoConsulta(cnt.getTipoConsulta());
        this.setInternamento(cnt.getInternamento());
        this.setLocal(cnt.getLocal());
        this.setPago(cnt.getPago());
        this.setReceita(cnt.getReceita());
        this.setValor(cnt.getValor());
    }

    public static List<Consulta> readByData(LocalDate data){
        java.sql.Date dataSQL = java.sql.Date.valueOf(data);
        
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Consulta.findByDatahora");
        query.setParameter("datahora", dataSQL);
        return query.getResultList();
    }

    public static List<Consulta> retrieveAll(){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Consulta.findAll");
        return query.getResultList();
    }
    
    public void remove(int idEliminar) {
        PersistenceManager.getEntityManager().getTransaction().begin();
        Query query = em.createNamedQuery("Consulta.removeById");
        query.setParameter("id", idEliminar);
        query.executeUpdate();
        PersistenceManager.getEntityManager().getTransaction().commit();
    }

  
}

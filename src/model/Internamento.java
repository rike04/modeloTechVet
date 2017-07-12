/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "INTERNAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Internamento.findAll", query = "SELECT i FROM Internamento i"),
    @NamedQuery(name = "Internamento.findById", query = "SELECT i FROM Internamento i WHERE i.id = :id"),
    @NamedQuery(name = "Internamento.findByGuiamed", query = "SELECT i FROM Internamento i WHERE i.guiamed = :guiamed"),
    @NamedQuery(name = "Internamento.findByDatae", query = "SELECT i FROM Internamento i WHERE i.datae = :datae"),
    @NamedQuery(name = "Internamento.findByDatas", query = "SELECT i FROM Internamento i WHERE i.datas = :datas"),
    @NamedQuery(name = "Internamento.findByObs", query = "SELECT i FROM Internamento i WHERE i.obs = :obs")})
public class Internamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "INTERNAMENTO_SEQ",
            sequenceName = "INTERNAMENTO_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "INTERNAMENTO_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "GUIAMED")
    private String guiamed;
    @Basic(optional = false)
    @Column(name = "DATAE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datae;
    @Basic(optional = false)
    @Column(name = "DATAS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datas;
    @Basic(optional = false)
    @Column(name = "OBS")
    private String obs;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "internamento")
    private Collection<InteRetiraProd> inteRetiraProdCollection;
    @JoinColumn(name = "ID_CONSULTA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Consulta idConsulta;
    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Paciente idPaciente;

    public Internamento() {
    }

    public Internamento(Integer id) {
        this.id = id;
    }

    public Internamento(Integer id, Date datae, Date datas, String obs) {
        this.id = id;
        this.datae = datae;
        this.datas = datas;
        this.obs = obs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGuiamed() {
        return guiamed;
    }

    public void setGuiamed(String guiamed) {
        this.guiamed = guiamed;
    }

    public Date getDatae() {
        return datae;
    }

    public void setDatae(Date datae) {
        this.datae = datae;
    }

    public Date getDatas() {
        return datas;
    }

    public void setDatas(Date datas) {
        this.datas = datas;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @XmlTransient
    public Collection<InteRetiraProd> getInteRetiraProdCollection() {
        return inteRetiraProdCollection;
    }

    public void setInteRetiraProdCollection(Collection<InteRetiraProd> inteRetiraProdCollection) {
        this.inteRetiraProdCollection = inteRetiraProdCollection;
    }

    public Consulta getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Consulta idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
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
        if (!(object instanceof Internamento)) {
            return false;
        }
        Internamento other = (Internamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Internamento[ id=" + id + " ]";
    }
    
}

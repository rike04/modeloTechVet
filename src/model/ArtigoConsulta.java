/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @ManyToMany(mappedBy = "artigoConsultaCollection")
    private Collection<Consulta> consultaCollection;
    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Produto idProduto;

    public ArtigoConsulta() {
    }

    public ArtigoConsulta(Integer id) {
        this.id = id;
    }

    public ArtigoConsulta(Integer id, int quantidade) {
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
    public Collection<Consulta> getConsultaCollection() {
        return consultaCollection;
    }

    public void setConsultaCollection(Collection<Consulta> consultaCollection) {
        this.consultaCollection = consultaCollection;
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
    
}

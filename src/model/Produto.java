/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rike4
 */
@Entity
@Table(name = "PRODUTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findById", query = "SELECT p FROM Produto p WHERE p.id = :id"),
    @NamedQuery(name = "Produto.findByNome", query = "SELECT p FROM Produto p WHERE p.nome = :nome"),
    @NamedQuery(name = "Produto.findByPreco", query = "SELECT p FROM Produto p WHERE p.preco = :preco"),
    @NamedQuery(name = "Produto.findByStock", query = "SELECT p FROM Produto p WHERE p.stock = :stock"),
    @NamedQuery(name = "Produto.findByDescricao", query = "SELECT p FROM Produto p WHERE p.descricao = :descricao"),
    @NamedQuery(name = "Produto.findByStockmin", query = "SELECT p FROM Produto p WHERE p.stockmin = :stockmin")})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "PRODUTO_SEQ",
            sequenceName = "PRODUTO_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PRODUTO_SEQ")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PRECO")
    private BigDecimal preco;
    @Basic(optional = false)
    @Column(name = "STOCK")
    private int stock;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "STOCKMIN")
    private int stockmin;
    @JoinColumn(name = "COD_TIPO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoProduto codTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
    private Collection<InteRetiraProd> inteRetiraProdCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProduto")
    private Collection<LinhaArtigo> linhaArtigoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProduto")
    private Collection<ArtigoConsulta> artigoConsultaCollection;

    public Produto() {
    }

    public Produto(Integer id) {
        this.id = id;
    }

    public Produto(Integer id, String nome, BigDecimal preco, int stock, int stockmin) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.stock = stock;
        this.stockmin = stockmin;
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

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getStockmin() {
        return stockmin;
    }

    public void setStockmin(int stockmin) {
        this.stockmin = stockmin;
    }

    public TipoProduto getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(TipoProduto codTipo) {
        this.codTipo = codTipo;
    }

    @XmlTransient
    public Collection<InteRetiraProd> getInteRetiraProdCollection() {
        return inteRetiraProdCollection;
    }

    public void setInteRetiraProdCollection(Collection<InteRetiraProd> inteRetiraProdCollection) {
        this.inteRetiraProdCollection = inteRetiraProdCollection;
    }

    @XmlTransient
    public Collection<LinhaArtigo> getLinhaArtigoCollection() {
        return linhaArtigoCollection;
    }

    public void setLinhaArtigoCollection(Collection<LinhaArtigo> linhaArtigoCollection) {
        this.linhaArtigoCollection = linhaArtigoCollection;
    }

    @XmlTransient
    public Collection<ArtigoConsulta> getArtigoConsultaCollection() {
        return artigoConsultaCollection;
    }

    public void setArtigoConsultaCollection(Collection<ArtigoConsulta> artigoConsultaCollection) {
        this.artigoConsultaCollection = artigoConsultaCollection;
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
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Produto[ id=" + id + " ]";
    }
    
}

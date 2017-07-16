/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    @Basic(optional = false)
    @Column(name = "STOCK")
    private int stock;
    @Basic(optional = false)
    @Column(name = "PRECO")
    private double preco;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "STOCKMIN")
    private int stockmin;
    @JoinColumn(name = "COD_TIPO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoProduto codTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
    private List<InteRetiraProd> inteRetiraProdCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
    private List<LinhaArtigo> linhaArtigoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProduto")
    private List<ArtigoConsulta> artigoConsultaCollection;

    private static EntityManager em;
    
    public Produto() {
        
    }

    public Produto(Integer id) {
        this.id = id;
    }

    public Produto(Integer id, String nome, double preco, int stock, int stockmin) {
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
    public List<InteRetiraProd> getInteRetiraProdList() {
        if (inteRetiraProdCollection == null) {
            inteRetiraProdCollection = new ArrayList<>();
        }
        return inteRetiraProdCollection;
    }

    public void setInteRetiraProdList(List<InteRetiraProd> inteRetiraProdCollection) {
        this.inteRetiraProdCollection = inteRetiraProdCollection;
    }

    @XmlTransient
    public List<LinhaArtigo> getLinhaArtigoList() {
        if (linhaArtigoCollection == null) {
            linhaArtigoCollection = new ArrayList<>();
        }
        return linhaArtigoCollection;
    }

    public void setLinhaArtigoList(List<LinhaArtigo> linhaArtigoCollection) {
        this.linhaArtigoCollection = linhaArtigoCollection;
    }

    @XmlTransient
    public List<ArtigoConsulta> getArtigoConsultaList() {
        if (artigoConsultaCollection == null) {
            artigoConsultaCollection = new ArrayList<>();
        }
        return artigoConsultaCollection;
    }

    public void setArtigoConsultaCollection(List<ArtigoConsulta> artigoConsultaCollection) {
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public int createT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.persist((Produto)this);
        em.getTransaction().commit();
        
        return this.getId();
    }
    
    public int create(){
        em = PersistenceManager.getEntityManager();
        em.persist((Produto)this);
        return this.getId();
    }

    public void updateT(){
        em = PersistenceManager.getEntityManager();
        em.getTransaction().begin();
        em.merge((Produto)this);
        em.getTransaction().commit();
    }

    public void read(int idcliente){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Cliente.findById");
        query.setParameter("id", idcliente);
        
        Produto p = (Produto) query.getSingleResult();
        this.setId(p.getId());
        this.setNome(p.getNome());
        this.setPreco(p.getPreco());
        this.setDescricao(p.getDescricao());
        this.setCodTipo(p.getCodTipo());
        this.setArtigoConsultaCollection(p.getArtigoConsultaList());
        this.setStock(p.getStock());
        this.setStockmin(p.getStockmin());
        this.setInteRetiraProdList(p.getInteRetiraProdList());
        this.setLinhaArtigoList(p.getLinhaArtigoList());
        
        System.out.println("ID = " + this.getId());
    }

    public static List<Produto> readByNome(String nome){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Produto.findByNome");
        query.setParameter("nome", nome);
        List<Produto> lista = query.getResultList();
        return lista;
    }

    public static List<Produto> retrieveAll(){
        em = PersistenceManager.getEntityManager();
        Query query = em.createNamedQuery("Produto.findAll");
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

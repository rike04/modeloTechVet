
package model;

import bll.PersistenceManager;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityManager;

/**
 *
 * @author rike4
 */
@Embeddable
public class InteRetiraProdPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_PRODUTO")
    private int idProduto;
    @Basic(optional = false)
    @Column(name = "ID_INTERNAMENTO")
    private int idInternamento;
    
    public InteRetiraProdPK() {
    }

    public InteRetiraProdPK(int idProduto, int idInternamento) {    
        this.idProduto = idProduto;
        this.idInternamento = idInternamento;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdInternamento() {
        return idInternamento;
    }

    public void setIdInternamento(int idInternamento) {
        this.idInternamento = idInternamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProduto;
        hash += (int) idInternamento;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InteRetiraProdPK)) {
            return false;
        }
        InteRetiraProdPK other = (InteRetiraProdPK) object;
        if (this.idProduto != other.idProduto) {
            return false;
        }
        if (this.idInternamento != other.idInternamento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.InteRetiraProdPK[ idProduto=" + idProduto + ", idInternamento=" + idInternamento + " ]";
    }
    
}

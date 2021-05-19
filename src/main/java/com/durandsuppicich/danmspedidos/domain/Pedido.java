package com.durandsuppicich.danmspedidos.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PEDIDO", schema = "MS_PEDIDOS")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO")
    private Integer id;

    @Column(nullable = false)
    private Instant fecha;

    @OneToOne
    @JoinColumn(name = "ID_ESTADO_PEDIDO")
    private EstadoPedido estado;

    @OneToOne
    @JoinColumn(name = "ID_OBRA")
    private Obra obra;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PEDIDO", referencedColumnName = "ID_PEDIDO")
    private List<DetallePedido> detalles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public void addDetalle(DetallePedido detalle) {
        this.detalles.add(detalle);
    }

    @Override
    public String toString() {
        return "Pedido [detalles=" + detalles + ", estado=" + estado + ", fecha=" + fecha + ", id=" + id + ", obra="
                + obra + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((detalles == null) ? 0 : detalles.hashCode());
        result = prime * result + ((estado == null) ? 0 : estado.hashCode());
        result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((obra == null) ? 0 : obra.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pedido other = (Pedido) obj;
        if (detalles == null) {
            if (other.detalles != null)
                return false;
        } else if (!detalles.equals(other.detalles))
            return false;
        if (estado == null) {
            if (other.estado != null)
                return false;
        } else if (!estado.equals(other.estado))
            return false;
        if (fecha == null) {
            if (other.fecha != null)
                return false;
        } else if (!fecha.equals(other.fecha))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (obra == null) {
            if (other.obra != null)
                return false;
        } else if (!obra.equals(other.obra))
            return false;
        return true;
    }
}

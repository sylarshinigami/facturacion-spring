package com.ronald.datajpa.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table( name = "facturas")
public class Factura implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty( message = "La descripcion no puede estar vacia.")
	private String descripcion;

	
	private String observacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd")

	private Date createAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id")
	private List<ItemFactura> items;

	public Factura() {
		super();
		items = new ArrayList<ItemFactura>();
	}

	public void addItemFactura(ItemFactura item) {
		items.add(item);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@XmlTransient
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	public Double getTotal() {
		Double total = 0.0;
		int size = items.size();
		for (int i = 0; i < size; i++)
			total += items.get(i).CalcularImporte();
		return total;
	}

	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
}

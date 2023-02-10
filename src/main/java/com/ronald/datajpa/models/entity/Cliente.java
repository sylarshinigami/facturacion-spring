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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3074859343666802232L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Size(min = 3, max = 120)
	private String nombre;

	@NotEmpty
	@Size(min = 3, max = 120)
	private String apellido;

	@NotEmpty
	@Email
	private String email;

	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE) // para guardar solo las fechas sin tiempo.
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createAt;
	
	private String foto;
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Factura> facturas;

	
	public Cliente() {
		super();
		facturas = new ArrayList<>();
	}

	public Cliente(Long id, String nombre, String apellido, String email, Date createAt) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.createAt = createAt;
	}
//	
//	@PrePersist
//	public void prePersist() {
//		this.createAt = new Date();
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	
	public void addFactura(Factura factura) {
		facturas.add(factura);
	}

	@Override
	public String toString() {
		return nombre.concat(" ").concat(apellido);
	}

}

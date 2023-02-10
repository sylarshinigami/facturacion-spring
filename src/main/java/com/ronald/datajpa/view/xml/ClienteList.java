package com.ronald.datajpa.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ronald.datajpa.models.entity.Cliente;

//clase wrapper
@XmlRootElement(name = "Clientes")
public class ClienteList {

	@XmlElement(name = "cliente")
	private List<Cliente> clientesList;

	public ClienteList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClienteList(List<Cliente> clientesList) {
		super();
		this.clientesList = clientesList;
	}

	public List<Cliente> getClientes() {
		return clientesList;
	}

	public void setClientes(List<Cliente> clientesList) {
		this.clientesList = clientesList;
	}
	
	
	
	
}

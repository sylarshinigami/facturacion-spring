package com.ronald.datajpa.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.ronald.datajpa.models.entity.Cliente;
import com.ronald.datajpa.models.service.IClienteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;


@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteRestController {


    @Autowired
	private IClienteService clienteService;

    @GetMapping( "/listar")
    @Secured("ROLE_ADMIN")
	public List<Cliente> listarClientesJson() {
		return clienteService.findAll();
	}
	
}

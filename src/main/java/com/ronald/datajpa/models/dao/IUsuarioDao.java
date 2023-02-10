package com.ronald.datajpa.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ronald.datajpa.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	
	public Usuario findByUsername(String username);
	
}

package com.ronald.datajpa.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ronald.datajpa.models.dao.IUsuarioDao;
import com.ronald.datajpa.models.entity.Role;
import com.ronald.datajpa.models.entity.Usuario;

@Service("jpaUserDetailService")
public class JpaUserDetailService implements UserDetailsService{

	
	@Autowired
	private IUsuarioDao dao;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	@Transactional( readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = dao.findByUsername(username);
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if( usuario == null) {
			logger.error("error login: no existe el usuario: " + username);
			throw new UsernameNotFoundException("no existe en el sistema");
		}
		
		for(Role role: usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		if( authorities.isEmpty()) {
			logger.error("error login: no tiene roles asignados " + username);
			throw new UsernameNotFoundException("El usuario no tiene roles asignados");
		}
			
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

}

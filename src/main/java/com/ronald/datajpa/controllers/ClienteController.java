package com.ronald.datajpa.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ronald.datajpa.models.entity.Cliente;
import com.ronald.datajpa.models.service.IClienteService;
import com.ronald.datajpa.models.service.IUploadFileService;
import com.ronald.datajpa.util.paginator.PageRender;

@Controller
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadService;

	private Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private MessageSource messageSource;//para extraer los mensajes de idiomas de properties.
	

	@GetMapping({ "/listar", "", "/" })
	public String listarClientes(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request, Locale locale) {

		if (authentication != null)
			log.info("usuario autenticado, " + authentication.getName());

//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null)
//			log.info("usuario autenticado, obtenido forma estatica: " + auth.getName());
//		
//		if(hasRole("ROLE_ADMIN"))
//			log.info("Tienes permiso de administrador");
//		if(hasRole("ROLE_USER"))
//			log.info("Tienes permisos normales");
		
//		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
//		if(securityContext.isUserInRole("ADMIN"))
//			log.info("Rol validado de otra manera");
//		
//		if(request.isUserInRole("ROLE_USER"))
//			log.info("autenticado con el objeto request");
//		

		Pageable pageRequest = PageRequest.of(page, 7);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

		
		
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);

		return "cliente/listar";
	}

	
	
	@GetMapping("/form")
	@Secured("ROLE_ADMIN")
	public String insertarCliente(Model model) {

		Cliente cliente = new Cliente();
		
		
		
		
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Insercion datos de Cliente");
		return "cliente/form";
	}

	@PostMapping("/form") // estos dos deben de ir siempre juntos
	@Secured("ROLE_ADMIN")
	public String guardarCliente(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash) {

		if (result.hasErrors()) {
			if (cliente.getId() == null)
				model.addAttribute("titulo", "Insercion datos de Cliente");
			else
				model.addAttribute("titulo", "Editado al cliente: ".concat(cliente.getNombre()));
			return "cliente/form";
		}

		// para colocar el respectivo de mensaje
		if (cliente.getId() == null)
			flash.addFlashAttribute("success", "Cliente guardado con exito.");
		else
			flash.addFlashAttribute("info", "Cliente actualizado con exito.");

		if (!foto.isEmpty()) {
			// Path directorioRecursos = Paths.get("C://Temp//uploads");
			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {
				uploadService.delete(cliente.getFoto());
			}
			String uniqueFilename = "default.png";
			try {
				uniqueFilename = uploadService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cliente.setFoto(uniqueFilename);
		}

		clienteService.save(cliente);
		return "redirect:/listar";
	}

	// otra manera de subir fotos
	@GetMapping(value = "/uploads/{filename:.+}")
	@Secured("ROLE_USER")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		Resource recurso = null;
		try {
			recurso = uploadService.load(filename);
		} catch (MalformedURLException e) {
			log.error(e.getMessage());

		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@GetMapping(value = "/ver/{id}")
	@Secured("ROLE_USER")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		if (id != null) {
			Cliente cliente = clienteService.fetchByIdWithFacturas(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "el cliente no existe en la base de datos");
				return "redirect:/listar";
			}

			model.addAttribute("cliente", cliente);
			model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());
			return "cliente/ver";
		}
		return "redirect:/listar";

	}

	@GetMapping("/form/{id}")
	@Secured("ROLE_USER")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
		} else {
			flash.addFlashAttribute("error", "El cliente no se puede actualizar");
			return "redirect:cliente/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Editado al cliente: ".concat(cliente.getNombre()));
		return "cliente/form";

	}

	@GetMapping("/eliminar/{id}")
	@Secured("ROLE_ADMIN")
	public String eliminar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);
			clienteService.delete(id);
			flash.addFlashAttribute("warning", "Cliente eliminado con exito.");
			uploadService.delete(cliente.getFoto());
		}

		return "redirect:/listar";

	}

	private boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return false;
		}

		Authentication auth = context.getAuthentication();
		if (auth == null)
			return false;		
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		return authorities.contains(new SimpleGrantedAuthority(role));
		
//		for (GrantedAuthority authority : authorities)
//			if (role.equals(authority.getAuthority()))
//				return true;

//		return true;

	}



}

package com.ronald.datajpa.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ronald.datajpa.models.entity.Cliente;
import com.ronald.datajpa.models.entity.Factura;
import com.ronald.datajpa.models.entity.ItemFactura;
import com.ronald.datajpa.models.entity.Producto;
import com.ronald.datajpa.models.service.IClienteService;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;

	private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long id, Model model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos.");
			return "redirect:/listar";
		}

		model.addAttribute("titulo", "Creacion de Factura.");
		Factura factura = new Factura();
		factura.setCliente(cliente);

		model.addAttribute("factura", factura);
		return "/factura/form";
	}

	
	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return clienteService.findByNombre(term);
	}

	
	@PostMapping(value = "/form")
	public String guardar(@Valid Factura factura, BindingResult resul,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
			RedirectAttributes flash, Model model, SessionStatus status) {

		if (resul.hasErrors()) {
			model.addAttribute("titulo", "Crear Factura");
			return "factura/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Factura");
			model.addAttribute("error", "error: la factura No puede quedar vacia");
			return "factura/form";

		}

		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItemFactura(linea);

			log.info("ID: " + itemId[i] + " cantidad: " + cantidad[i]);
		}

		clienteService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("success", "Factura creada con exito");
		return "redirect:/ver/" + factura.getCliente().getId();
	}



	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model,
	RedirectAttributes flash){
		Factura factura = clienteService.fetchFacturaByIdWithClienteWithItemFacturaWithProducto(id);

		if(factura == null){
			flash.addFlashAttribute("error", "La factura no existe en la base de datos.");
			return "redirect:/listar";
		}

		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));

		return "factura/ver";
	}


	@GetMapping("/eliminar/{id}")
	public String eliminar (@PathVariable(value= "id") Long id, RedirectAttributes flash) {
		
		Factura factura = clienteService.findFacturaById(id);
		if(factura != null) {
			clienteService.deleteFactura(id);
			flash.addFlashAttribute("success", "Factura eliminada con exito");
			return "redirect:/ver/"+ factura.getCliente().getId();
		}
		flash.addFlashAttribute("error", "La factura no se puede eliminar");
		return "redirect:/listar";
	}

}

package com.ronald.datajpa.view.csv;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.ronald.datajpa.models.entity.Cliente;
import com.ronald.datajpa.models.service.ClienteServiceImpl;
import com.ronald.datajpa.models.service.IClienteService;

@Component("cliente/listar.csv")
public class ClienteCsvView extends AbstractView{
	
	@Autowired
	private IClienteService clienteService;
	

	public ClienteCsvView() {
		setContentType("text/csv");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
		response.setContentType(getContentType());
		
		List<Cliente> clientes = clienteService.findAll();
		ICsvBeanWriter beanwrite = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] header = {"id", "nombre", "apellido", "email", "createAt"};
		beanwrite.writeHeader(header);
		for(Cliente cliente : clientes) {
			beanwrite.write(cliente, header);
		}
		
		beanwrite.close();
		
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
	
	

}

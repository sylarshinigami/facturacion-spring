package com.ronald.datajpa.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ronald.datajpa.models.entity.Factura;
import com.ronald.datajpa.models.entity.ItemFactura;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocaleResolver localResolver;
	
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		Locale locale = localResolver.resolveLocale(request);
		
		//otra manera de obtener los mensajes traducidos de messages
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		
		Factura factura = (Factura) model.get("factura");
		
		PdfPTable tablaDatosCliente = new PdfPTable(1);
		tablaDatosCliente.setSpacingAfter(20);
		
		PdfPCell celda = new PdfPCell(new Phrase(messageSource.getMessage("text.facturacion.datosCliente",null, locale)));
		celda.setPadding(5);		
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setBackgroundColor(new Color(184, 218, 255));

		tablaDatosCliente.addCell(celda);
		tablaDatosCliente.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		tablaDatosCliente.addCell(factura.getCliente().getEmail());
		
		PdfPTable tablaDatosFactura = new PdfPTable(1);
		tablaDatosFactura.setSpacingAfter(20);
		
		celda = new PdfPCell(new Phrase(messageSource.getMessage("text.facturacion.datosFactura",null, locale)));
		celda.setPadding(5);		
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setBackgroundColor(new Color(195, 230, 203));
		
		tablaDatosFactura.addCell(celda);
		tablaDatosFactura.addCell(messageSource.getMessage("text.facturacion.folio",null, locale) + ": " + factura.getId());
		tablaDatosFactura.addCell(messageSource.getMessage("text.facturacion.descripcion",null, locale) + ": " + factura.getDescripcion());
		tablaDatosFactura.addCell(messageSource.getMessage("text.facturacion.createAt",null, locale) + ": " +factura.getCreateAt());

		PdfPTable tablaProductos = new PdfPTable(4);
		tablaProductos.setWidths(new float[] {2.5f, 1, 1, 1});
		
		celda = new PdfPCell();
		celda.setBackgroundColor(new Color(204, 204, 204));		
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setPhrase(new Phrase(mensajes.getMessage("text.facturacion.producto")));
		tablaProductos.addCell(celda);
		celda.setPhrase(new Phrase(mensajes.getMessage("text.facturacion.precio")));
		tablaProductos.addCell(celda);
		celda.setPhrase(new Phrase(mensajes.getMessage("text.facturacion.cantidad")));
		tablaProductos.addCell(celda);
		celda.setPhrase(new Phrase(mensajes.getMessage("text.facturacion.total")));
		tablaProductos.addCell(celda);

		for (ItemFactura item: factura.getItems()){
			tablaProductos.addCell(item.getProducto().getNombre());
			tablaProductos.addCell(item.getProducto().getPrecio().toString());
			
			celda = new PdfPCell(new Phrase(item.getCantidad().toString()));
			celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tablaProductos.addCell(celda);
			
			tablaProductos.addCell(item.CalcularImporte().toString());
		}

		PdfPCell footerProducto = new PdfPCell(new Phrase(messageSource.getMessage("text.facturacion.total",null, locale) + ": "));
		footerProducto.setColspan(3);
		footerProducto.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tablaProductos.addCell(footerProducto);
		tablaProductos.addCell(factura.getTotal().toString());

		document.add(tablaDatosCliente);
		document.add(tablaDatosFactura);
		document.add(tablaProductos);
	}

}

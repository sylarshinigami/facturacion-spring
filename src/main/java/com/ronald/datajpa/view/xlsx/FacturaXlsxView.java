package com.ronald.datajpa.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ronald.datajpa.models.entity.Factura;
import com.ronald.datajpa.models.entity.ItemFactura;

@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

//	@Autowired
//	private MessageSource messageSource;
//
//	@Autowired
//	private LocaleResolver localResolver;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Factura factura = (Factura) model.get("factura");
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
		MessageSourceAccessor mensajes = getMessageSourceAccessor();

		Sheet sheet = workbook.createSheet("factura Spring");

		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(mensajes.getMessage("text.facturacion.datosCliente"));

		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());

		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());

		//encadenando los metodos
		sheet.createRow(4).createCell(0).setCellValue(mensajes.getMessage("text.facturacion.datosFactura"));
		sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.facturacion.folio") + factura.getId());
		sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("text.facturacion.datosFactura") + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("text.facturacion.createAt") + factura.getCreateAt());
		
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue(mensajes.getMessage("text.facturacion.producto"));
		header.createCell(1).setCellValue(mensajes.getMessage("text.facturacion.precio"));
		header.createCell(2).setCellValue(mensajes.getMessage("text.facturacion.cantidad"));
		header.createCell(3).setCellValue(mensajes.getMessage("text.facturacion.total"));
		
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);

		
		int rowNumb = 10;
		for(ItemFactura item: factura.getItems()) {
			Row fila = sheet.createRow(rowNumb++);
			
			cell = fila.createCell(0);
			cell.setCellValue(item.getProducto().getNombre());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(1);
			cell.setCellValue(item.getProducto().getPrecio());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(2);
			cell.setCellValue(item.getCantidad());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(3);
			cell.setCellValue(item.CalcularImporte());
			cell.setCellStyle(tbodyStyle);
			
			
		}

		Row filaTotal = sheet.createRow(rowNumb);
		cell = filaTotal.createCell(2);
		cell.setCellStyle(theaderStyle);
		cell.setCellValue(mensajes.getMessage("text.facturacion.total"));
		
		cell = filaTotal.createCell(3);
		cell.setCellStyle(theaderStyle);
		cell.setCellValue(factura.getTotal());

	}

}

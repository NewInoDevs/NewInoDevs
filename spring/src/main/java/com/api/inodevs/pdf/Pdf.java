package com.api.inodevs.pdf;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.api.inodevs.entidades.Conta;
import com.api.inodevs.entidades.Contrato;
import com.api.inodevs.entidades.Unidade;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class Pdf {
	
    private void writeTableHeader(PdfPTable table, String[] mesesGrafico, Font fontTabela, int mes, int ano) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(8);
         
	    cell.setPhrase(new Phrase("Contrato", fontTabela));
	    table.addCell(cell);  
       
	    int mesT = mes+1;
	    int anoT = ano-1;
        for (int i = 11; i >= 0; i--) {
        	if (mesT > 12) {
        		anoT++;
        		mesT-=12;
        	}
		    cell.setPhrase(new Phrase(mesesGrafico[i]+"/\n"+anoT, fontTabela));
		    mesT++;
		    table.addCell(cell);  
		}

    }
     
    private void writeTableData(PdfPTable table, Contrato contrato, Float[] consumosConta) {
        table.addCell(String.valueOf(contrato.getCodigo()));
        for (int i = 11; i >= 0; i--) {
        	if (consumosConta[i] == null) {
        		table.addCell("");
        	} else {
        		table.addCell(String.valueOf(consumosConta[i]));
        	}
		}
    }
	
    public void export(HttpServletResponse response,  Unidade unidade, int ano, String mesExtenso, int mes, String[] mesesGrafico,
			Conta contaAguaAtual, Float[] contaAguaMes, Float[] gastoAguaMes, Conta contaEnergiaAtual,
			Float[] contaEnergiaMes, Float[] gastoEnergiaMes, Conta contaGasAtual, Float[] contaGasMes,
			Float[] gastoGasMes, Float mediaA, Float mediaE, Float mediaG, Contrato contratoAgua, 
			Contrato contratoEnergia, Contrato contratoGas) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitulo.setSize(18);
        fontTitulo.setColor(Color.BLUE);
        
        Font fontSub = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontSub.setSize(15);
        
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(12);
        
        Font fontTabela = FontFactory.getFont(FontFactory.HELVETICA);
        fontTabela.setSize(9);
        fontTabela.setColor(Color.WHITE);
         
        Paragraph titulo = new Paragraph("Relatório de " + mes + " de " + ano, fontTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        
        Paragraph sub = new Paragraph("\nUnidade: " + unidade.getNome(), fontSub);
        sub.setAlignment(Paragraph.ALIGN_CENTER);
        
        //CONTRATO ÁGUA
        
        if(contaAguaAtual != null) {
        	Paragraph textoA = new Paragraph("\n\nConcessionaria: " + contratoAgua.getConcessionaria().getNome()
            		+ "\n\nConsumo: " + contaAguaAtual.getConsumo()
            		+ "\nValor: " + contaAguaAtual.getValor_total()
            		+ "\nMédia do Consumo Anual: " + mediaA
            		+ "\nMédia Ideal de Consumo: " + contaAguaAtual.getMedia_consumo()
            		+ "\n"
            		, font);
            
            PdfPTable tableA = new PdfPTable(13);
            tableA.setWidthPercentage(100f);
            tableA.setWidths(new float[] {1f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f});
            tableA.setSpacingBefore(20);
             
            writeTableHeader(tableA, mesesGrafico, fontTabela, mes, ano);
            writeTableData(tableA, contratoAgua, contaAguaMes);
            
    	    document.add(titulo);
    	    document.add(sub);
    	    document.add(textoA);
            document.add(tableA);
        }
        else {
        	Paragraph tituloN = new Paragraph("\nNão existem contas de Água desse mês.");
        	document.add(tituloN);
        }
        
        
        //CONTRATO ENERGIA
        
        if(contaEnergiaAtual != null) {
        	Paragraph textoE = new Paragraph("\n\nConcessionaria: " + contratoEnergia.getConcessionaria().getNome()
            		+ "\n\nConsumo: " + contaEnergiaAtual.getConsumo()
            		+ "\nValor: " + contaEnergiaAtual.getValor_total()
            		+ "\nMédia do Consumo Anual: " + mediaE
            		+ "\nMédia Ideal de Consumo: " + contaEnergiaAtual.getMedia_consumo()
            		+ "\n"
            		, font);
            
            PdfPTable tableE = new PdfPTable(13);
            tableE.setWidthPercentage(100f);
            tableE.setWidths(new float[] {1f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f});
            tableE.setSpacingBefore(20);
             
            writeTableHeader(tableE, mesesGrafico, fontTabela, mes, ano);
            writeTableData(tableE, contratoEnergia, contaEnergiaMes);
            

    	    document.add(textoE);
            document.add(tableE);
        }
        
        else {
        	Paragraph tituloN = new Paragraph("\nNão existem contas de Energia desse mês.");
        	document.add(tituloN);
        }
        //CONTRATO GAS
        
        if(contaGasAtual != null) {
        	Paragraph textoG = new Paragraph("\n\nConcessionaria: " + contratoGas.getConcessionaria().getNome()
            		+ "\n\nConsumo: " + contaGasAtual.getConsumo()
            		+ "\nValor: " + contaGasAtual.getValor_total()
            		+ "\nMédia do Consumo Anual: " + mediaG
            		+ "\nMédia Ideal de Consumo: " + contaGasAtual.getMedia_consumo()
            		+ "\n"
            		, font);
            
            PdfPTable tableG = new PdfPTable(13);
            tableG.setWidthPercentage(100f);
            tableG.setWidths(new float[] {1f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f});
            tableG.setSpacingBefore(20);
             
            writeTableHeader(tableG, mesesGrafico, fontTabela, mes, ano);
            writeTableData(tableG, contratoGas, contaGasMes);
            

    	    document.add(textoG);
            document.add(tableG);
        }
        
        else {
        	Paragraph tituloN = new Paragraph("\nNão existem contas de Gás desse mês.");
        	document.add(tituloN);
        }
        document.close();
        
    }
}
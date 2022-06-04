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
        font.setSize(11);
        
        Font alerta = FontFactory.getFont(FontFactory.HELVETICA);
        alerta.setSize(11);
        alerta.setColor(Color.RED);
        
        Font fontTabelaTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTabelaTitulo.setSize(12);
        fontTabelaTitulo.setColor(Color.WHITE);
        
        Font fontTabela = FontFactory.getFont(FontFactory.HELVETICA);
        fontTabela.setSize(9);
        fontTabela.setColor(Color.WHITE);
         
        Paragraph titulo = new Paragraph("Relatório de " + mes + " de " + ano, fontTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(titulo);
	    
        Paragraph sub = new Paragraph("Unidade: " + unidade.getNome(), fontSub);
        sub.setSpacingBefore(5);

        sub.setAlignment(Paragraph.ALIGN_CENTER);
        
        Paragraph texto = new Paragraph("\n\nConcessionaria: " + contratoAgua.getConcessionaria().getNome()
        		+ "\n\nConsumo: " + contaAguaAtual.getConsumo()
        		+ "\nValor: " + contaAguaAtual.getValor_total()
        		+ "\nMédia do Consumo Anual: " + mediaA
        		+ "\nMédia Ideal de Consumo: " + contaAguaAtual.getMedia_consumo()
        		+ "\n"
        		, font);
        
        PdfPTable table = new PdfPTable(13);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f});
        table.setSpacingBefore(20);
         
        writeTableHeader(table, mesesGrafico, fontTabela, mes, ano);
        if(contratoAgua != null) {
        	writeTableData(table, contratoAgua, contaAguaMes, fontTabelaDado, "m³"); 
        } 
        if(contratoEnergia != null) {
        	writeTableData(table, contratoEnergia, contaEnergiaMes, fontTabelaDado,"kWh");
        } 
        if(contratoGas != null) {
        	writeTableData(table, contratoGas, contaGasMes, fontTabelaDado, "m³");
        } 
        document.add(table);
        
        PdfPTable tableV = new PdfPTable(13);
        tableV.setWidthPercentage(100f);
        tableV.setWidths(new float[] {1f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f});
        tableV.setSpacingBefore(20);  
        PdfPCell cellV = new PdfPCell();
        cellV.setBackgroundColor(Color.decode("#800080"));
        cellV.setPadding(5);
	    cellV.setPhrase(new Phrase("Tabela de Valor Gasto Mensal (R$)", fontTabelaTitulo));
	    cellV.setColspan(13);
	    cellV.setHorizontalAlignment(1);
	    tableV.addCell(cellV);  
        writeTableHeader(tableV, mesesGrafico, fontTabela, mes, ano);
        if(contratoAgua != null) {
        	writeTableData(tableV, contratoAgua, gastoAguaMes, fontTabelaDado, null); 
        } 
        if(contratoEnergia != null) {
        	writeTableData(tableV, contratoEnergia, gastoEnergiaMes, fontTabelaDado, null);
        } 
        if(contratoGas != null) {
        	writeTableData(tableV, contratoGas, gastoGasMes, fontTabelaDado, null);
        } 
        document.add(tableV);
        
        Paragraph sub2 = new Paragraph("Dados deste mês:", fontSub);
        sub2.setSpacingBefore(10);
        sub2.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(sub2);
        
        //CONTRATO ÁGUA
        
        if(contratoAgua != null) {
        	fontSub.setColor(Color.BLUE);
        	Paragraph textoA = new Paragraph("\nConcessionária: " + contratoAgua.getConcessionaria().getNome(), fontSub);
        	document.add(textoA);
        	if (contaAguaAtual != null) {
            	Paragraph texto2A = new Paragraph("Consumo: " + contaAguaAtual.getConsumo()
                		+ "\nValor: " + contaAguaAtual.getValor_total()
                		+ "\nMédia do Consumo Anual: " + mediaA
                		+ "\nMédia Ideal de Consumo: " + contaAguaAtual.getMedia_consumo()
                		, font);
                		document.add(texto2A);
                if (contaAguaAtual.getMedia_consumo() < mediaA) {
                	Paragraph alertaA = new Paragraph("Consumo de Água acima da média ideal!", alerta);
                	document.add(alertaA);
                }
        	}
	        else {
	        	Paragraph tituloNA = new Paragraph("Ainda não há uma conta de Água desse mês.");
	        	document.add(tituloNA);
	        } 
        }
        
        //CONTRATO ENERGIA
        
        if(contratoEnergia != null) {
        	fontSub.setColor(Color.RED);
        	Paragraph textoE = new Paragraph("\nConcessionaria: " + contratoEnergia.getConcessionaria().getNome(), fontSub);
        	document.add(textoE);
        	if (contaEnergiaAtual != null) {
            	Paragraph texto2E = new Paragraph("Consumo: " + contaEnergiaAtual.getConsumo()
	        		+ "\nValor: " + contaEnergiaAtual.getValor_total()
	        		+ "\nMédia do Consumo Anual: " + mediaE
	        		+ "\nMédia Ideal de Consumo: " + contaEnergiaAtual.getMedia_consumo()
	        		+ "\n"
	        		, font);
                	document.add(texto2E);
               if (contaEnergiaAtual.getMedia_consumo() < mediaE) {
                   Paragraph alertaE = new Paragraph("Consumo de Energia acima da média ideal!", alerta);
                   document.add(alertaE);
               }
        	}
	        else {
	        	Paragraph tituloNE = new Paragraph("Ainda não há uma conta de Energia desse mês.");
	        	document.add(tituloNE);
	        }
        }

        //CONTRATO GAS
        
        if(contratoGas != null) {
        	fontSub.setColor(Color.decode("#005000"));
        	Paragraph textoG = new Paragraph("\nConcessionaria: " + contratoGas.getConcessionaria().getNome(), fontSub);
        	document.add(textoG);
        	if (contaGasAtual != null) {
            	Paragraph texto2G = new Paragraph("Consumo: " + contaGasAtual.getConsumo()
            		+ "\nValor: " + contaGasAtual.getValor_total()
            		+ "\nMédia do Consumo Anual: " + mediaG
            		+ "\nMédia Ideal de Consumo: " + contaGasAtual.getMedia_consumo()
            		, font);
                	document.add(texto2G);
                if (contaGasAtual.getMedia_consumo() < mediaG) {
                    Paragraph alertaG = new Paragraph("Consumo de Gás acima da média ideal!", alerta);
                    document.add(alertaG);
                }
        	}
	        else {
	        	Paragraph tituloNG = new Paragraph("Ainda não há uma conta de Gás desse mês.");
	        	document.add(tituloNG);
	        }                         
        }
        document.close();
         
    }
}
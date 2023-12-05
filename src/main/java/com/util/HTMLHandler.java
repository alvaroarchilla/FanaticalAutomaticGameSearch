package com.util;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fanatical.pages.SteamGamePage;
import com.fanatical.pages.SteamGamePage.SteamGamePageData;

public class HTMLHandler {

	private List<SteamGamePage> steamGamesPages;
	
	private final static String BREAK = "\n";
	private final static String TAB = "\t";
	private final static String TAB2 = "\t\t";
	
	public HTMLHandler (List<SteamGamePage>  steamGamePage) {
		this.steamGamesPages = steamGamePage;
	}
	
	public void generate() throws IOException {
		 
		File tempFile = null;
		FileWriter fw = null ;
		BufferedWriter bw = null;
		
		try {
			 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
			 LocalDateTime now = LocalDateTime.now();  
			 tempFile = File.createTempFile("GAME_REPORT_" + dtf.format(now), ".html");
			  
			 fw = new FileWriter(tempFile);
			 bw = new BufferedWriter(fw);
		  
			 bw.write(createHTML());
		  
		} catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  fw.close();
		  bw.close();
		}
		
		Desktop.getDesktop().open(tempFile);
	}
	
	protected String createHTML()  {
		StringBuilder page = new StringBuilder();
		
		page.append("<!DOCTYPE html>" + BREAK);
		page.append("<html lang=\"es\">" + BREAK);
		page.append("<head>" + BREAK);
		page.append(TAB + "<meta charset=\"utf-8\">" + BREAK);
		page.append(TAB + "<title>HTML</title>" + BREAK);
		page.append(TAB + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" + BREAK);
		page.append(TAB + " <link rel=\"stylesheet\" href=\"estilo.css\">" + BREAK);
		page.append("</head>" + BREAK);
		page.append("<body>" + BREAK);
		page.append(TAB + "<table style=\"width:100%\">" + BREAK);
		
		page.append(createTitlesTable());
		
		for(SteamGamePage gamePage : this.steamGamesPages) {
			page.append(createLine(gamePage));
		}
		
		page.append(TAB + "</table>");

		page.append("</body>");
		page.append("</html>");
		
		return page.toString();
	}
	
	protected String createTitlesTable() {
		StringBuilder line = new StringBuilder();
		
		line.append(TAB + "<tr>" + BREAK);
		line.append(TAB2 + "<th> " + "Game Name" + "</th>" + BREAK);
		line.append(TAB2 + "<th> " + "Price" + "</th>" + BREAK);
		line.append(TAB2 + "<th> " + "Reviews" + "</th>" + BREAK);
		line.append(TAB2 + "<th> " + "Categories" + "</th>" + BREAK);
		line.append(TAB2 + "<th> " + "Game Label" + "</th>" + BREAK);
		line.append(TAB2 + "<th> " + "Release Date" + "</th>" + BREAK);
		line.append(TAB2 + "<th> " + "Editor" + "</th>" + BREAK);
		line.append(TAB + "</tr>" + BREAK);
		
		return line.toString();
	}
	
	protected String createLine(SteamGamePage steamGamePage) {
		StringBuilder line = new StringBuilder();
		
		line.append(TAB + "<tr>" + BREAK);
		line.append(TAB2 + addURLtoHTMLTD(getGameData(steamGamePage, SteamGamePageData.URL), true)
			+ getGameData(steamGamePage, SteamGamePageData.GAME_NAME)+ "</td>" + BREAK);
		line.append(TAB2 + "<td>" + getGameData(steamGamePage, SteamGamePageData.PRICE)+ "</td>" + BREAK);
		line.append(TAB2 + "<td>" + getGameData(steamGamePage, SteamGamePageData.REVIEWS)+ "</td>" + BREAK);
		line.append(TAB2 + "<td>" + getGameData(steamGamePage, SteamGamePageData.CATEGORIES)+ "</td>" + BREAK);
		line.append(TAB2 + "<td>" + getGameData(steamGamePage, SteamGamePageData.GAME_LABEL)+ "</td>" + BREAK);
		line.append(TAB2 + "<td>" + getGameData(steamGamePage, SteamGamePageData.RELEASE_DATE)+ "</td>" + BREAK);
		line.append(TAB2 + "<td>" + getGameData(steamGamePage, SteamGamePageData.EDITOR)+ "</td>" + BREAK);

		line.append(TAB + "</tr>" + BREAK);
		
		return line.toString();
	}
	
	protected String addURLtoHTMLTD(String url, boolean openNewTab) {
		
		StringBuilder result = new StringBuilder("<td href='" + url);
		
		if(openNewTab) {
			result.append("target='blank'");
		}

		result.append(">");
		return result.toString();
	}
	
	protected String getGameData(SteamGamePage steamGamePage, SteamGamePageData selectedData) {
		return steamGamePage.getGenericData(selectedData);
	}
}

package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.JogosDAO;
import model.Jogo;
import spark.Request;
import spark.Response;

public class JogoService {
	private JogosDAO jogosDAO = new JogosDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_TITULO = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	
	public JogoService() {
		makeForm();
	}

	public void makeForm() {
		makeForm(FORM_INSERT, new Jogo(), FORM_ORDERBY_TITULO);
	}

	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Jogo(), orderBy);
	}

	public void makeForm(int tipo, Jogo jogo, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
			while(entrada.hasNext()){
				form += (entrada.nextLine() + "\n");
			}
			entrada.close();
		} catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umJogo = "";
		if(tipo != FORM_INSERT) {
			umJogo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/jogo/list/1\">Novo Jogo</a></b></font></td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t</table>";
			umJogo += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/jogo/";
			String name, buttonLabel;
            
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Jogo";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + jogo.getId();
				name = "Atualizar jogo (ID " + jogo.getId() + ")";
				buttonLabel = "Atualizar";
			}
            
            String tituloJogo = jogo.getTitulo() != null ? jogo.getTitulo() : "";
            String precoJogo = jogo.getPreco() > 0 ? String.valueOf(jogo.getPreco()) : "";

			umJogo += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umJogo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td>Título do Jogo: <input class=\"input--register\" type=\"text\" name=\"titulo\" value=\""+ tituloJogo +"\"></td>";
			umJogo += "\t\t\t<td>Preço: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\""+ precoJogo +"\"></td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td colspan=\"2\">&nbsp;</td>";
			umJogo += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t</table>";
			umJogo += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umJogo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Jogo (ID " + jogo.getId() + ")</b></font></td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td>&nbsp;Título: "+ jogo.getTitulo() +"</td>";
			umJogo += "\t\t\t<td>Preço: R$ "+ jogo.getPreco() +"</td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t\t<tr>";
			umJogo += "\t\t\t<td colspan=\"3\">&nbsp;</td>";
			umJogo += "\t\t</tr>";
			umJogo += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		
		
		form = form.replace("<UM-PRODUTO>", umJogo);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Jogos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
				"\n<tr>\n" + 
        		"\t<td><a href=\"/jogo/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/jogo/list/" + FORM_ORDERBY_TITULO + "\"><b>Título</b></a></td>\n" +
        		"\t<td><a href=\"/jogo/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Jogo> jogos;
		if (orderBy == FORM_ORDERBY_ID) {                 	jogos = jogosDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			jogos = jogosDAO.getOrderByTitulo();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			jogos = jogosDAO.getOrderByPreco();
		} else {											jogos = jogosDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Jogo p : jogos) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getTitulo() + "</td>\n" +
            		  "\t<td>R$ " + p.getPreco() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/jogo/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/jogo/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteJogo('" + p.getId() + "', '" + p.getTitulo() + "', '" + p.getPreco() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		

		form = form.replace("<LISTAR-PRODUTO>", list);				
	}
	
	public Object insert(Request request, Response response) {
		String titulo = request.queryParams("titulo");
		float preco = Float.parseFloat(request.queryParams("preco"));
		
		String resp = "";
		Jogo jogo = new Jogo(-1, titulo, preco);
		
		if(jogosDAO.insert(jogo) == true) {
            resp = "Jogo (" + titulo + ") inserido!";
            response.status(201);
		} else {
			resp = "Jogo (" + titulo + ") não inserido!";
			response.status(404);
		}
			
		makeForm();
		return form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Jogo jogo = (Jogo) jogosDAO.get(id);
		
		if (jogo != null) {
			response.status(200);
			makeForm(FORM_DETAIL, jogo, FORM_ORDERBY_TITULO);
        } else {
            response.status(404);
            String resp = "Jogo " + id + " não encontrado.";
    		makeForm();
    		return form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }
		return form;
	}

	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Jogo jogo = (Jogo) jogosDAO.get(id);
		
		if (jogo != null) {
			response.status(200);
			makeForm(FORM_UPDATE, jogo, FORM_ORDERBY_TITULO);
        } else {
            response.status(404);
            String resp = "Jogo " + id + " não encontrado.";
    		makeForm();
    		return form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }
		return form;
	}
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Jogo jogo = (Jogo) jogosDAO.get(id);
        String resp = "";       

        if (jogo != null) {
        	jogo.setTitulo(request.queryParams("titulo"));
        	jogo.setPreco(Float.parseFloat(request.queryParams("preco")));
        	jogosDAO.update(jogo);
        	response.status(200);
            resp = "Jogo (ID " + jogo.getId() + ") atualizado!";
        } else {
            response.status(404);
            resp = "Jogo (ID " + id + ") não encontrado!";
        }
		makeForm();
		return form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Jogo jogo = (Jogo) jogosDAO.get(id);
        String resp = "";       

        if (jogo != null) {
            jogosDAO.delete(id);
            response.status(200);
            resp = "Jogo (" + id + ") excluído!";
        } else {
            response.status(404);
            resp = "Jogo (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replace("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}
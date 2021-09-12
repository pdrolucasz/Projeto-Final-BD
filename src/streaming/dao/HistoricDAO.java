package streaming.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class HistoricDAO {
	
	private Connection connection;
	
	public HistoricDAO() {
		connection = new Conection().getConnection();
	}
	
	public void readHistoric() {
		
		try {
			Statement stmt = connection.createStatement();
			
			ResultSet rs = stmt.executeQuery("select *, users.name as user_name, film.name as film_name from historic inner join users on historic.user_id = users.id inner join film on historic.film_id = film.id");
			
			while(rs.next()) {
				System.out.print("Id do filme no histórico: " + rs.getInt("id"));
				System.out.print("\tNome do filme: " + rs.getString("film_name"));
				System.out.print("\tNome do usuário: " + rs.getString("user_name"));
				System.out.print("\tAssistido " + rs.getInt("duration_end") + " minutos" + " de " + rs.getInt("duration") + " minutos");
				System.out.println();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void insertFilmHistoric(int userId, int filmId, int duration_end) {
		
		String query = "insert into historic (user_id, film_id, duration_end)" +
				"values"+ "(" + userId + ", " + filmId + ", " + duration_end +")";
		
		try {
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Filme adicionado ao histórico com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível adicionar filme ao histórico!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateHistoric(String query) {
		
		try {
			Statement stmt = connection.createStatement();
			//"update film_list set user_id = ', film_id = ', watched = true"
			int result = stmt.executeUpdate(query);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Histórico atualizado com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível atualizar o histórico!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteFilmHistoric(int idFilmHistoric) {
		
		try {
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate("delete from historic where id = " + idFilmHistoric);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Filme removido do histórico com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível remover filme do histórico!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}
	
	private static java.util.Date gerarData(String nascData) {
		
		SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			java.util.Date casdastroData = data.parse(nascData);
			return casdastroData;
		} catch ( Exception ex) {
			JOptionPane.showMessageDialog(null, "Data invalida");
			return new java.util.Date();
		}
		
	}
	
	public static void main(String[] args) throws ParseException {
		
		UsersDAO users = new UsersDAO();
		FilmDAO films = new FilmDAO();
		HistoricDAO dao = new HistoricDAO();
		Scanner sc = new Scanner(System.in);
		
		int option;
		
		do {
			System.out.println("================================================");
			System.out.println("1: Histórico\n2: Adicionar\n3: Atualizar\n4: Remover");
			option = sc.nextInt();
			
			switch (option) {
				case 1:
					System.out.println("============Histórico============");
					dao.readHistoric();
					break;
				case 2:
					System.out.println("============Adicionar filme ao histórico============");
					System.out.println("Digite o id do usuário: ");
					users.readUsers();
					int userId = sc.nextInt();
					
					System.out.println("Digite o id do filme: ");
					films.readFilms();
					int filmId = sc.nextInt();
					
					System.out.println("Digite o minuto onde parou o filme: ");
					int durationEnd = sc.nextInt();
					
					dao.insertFilmHistoric(userId, filmId, durationEnd);
					break;
				case 3:
					System.out.println("============Atualizar histórico============");
					System.out.println("Escolhe o filme da lista para atualizar: ");
					dao.readHistoric();
					int filmHistoricId = sc.nextInt();
					
					System.out.println("Digite o id do usuário: ");
					users.readUsers();
					int updateUserId = sc.nextInt();
					
					System.out.println("Digite o id do filme: ");
					films.readFilms();
					int updateFilmId = sc.nextInt();
					
					System.out.println("Digite o minuto onde parou o filme: ");
					int updateDurationEnd = sc.nextInt();
					
					String query = "update historic set user_id = " + updateUserId + ", film_id = " + updateFilmId + ", duration_end = "+ updateDurationEnd + " where id = " + filmHistoricId;
					
//					String query = "update film_list set " + 
//							"user_id = " + 1 + ", " +
//							"film_id = " + 1 + ", " + 
//							"watched = " + true +
//							"where id = " + 2;
					dao.updateHistoric(query);
					break;
				case 4:
					System.out.println("============Remover filme do histórico============");
					System.out.println("Escolhe o filme do histórico para remover: ");
					dao.readHistoric();
					int filmHistoricRemove = sc.nextInt();
					dao.deleteFilmHistoric(filmHistoricRemove);
					break;
				default:
					System.out.println("Opção não encontrada");
					option = 5;
			}
			
		} while(option >= 1 && option <=4);
	}


}

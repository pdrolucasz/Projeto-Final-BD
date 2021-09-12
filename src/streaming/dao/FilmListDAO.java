package streaming.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class FilmListDAO {
	
	private Connection connection;
	
	public FilmListDAO() {
		connection = new Conection().getConnection();
	}
	
	public void readFilmsList() {
		
		try {
			Statement stmt = connection.createStatement();
			
			ResultSet rs = stmt.executeQuery("select *, users.name as user_name, film.name as film_name from film_list inner join users on film_list.user_id = users.id inner join film on film_list.film_id = film.id");
			
			while(rs.next()) {
				System.out.print("Id do filme na lista: " + rs.getInt("id"));
				System.out.print("\tNome do filme: " + rs.getString("film_name"));
				System.out.print("\tNome do usuário: " + rs.getString("user_name"));
				System.out.print("\tAssistido: " + (rs.getBoolean("watched") == true ? "sim" : "não"));
				System.out.println();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void insertFilmList(int userId, int filmId, boolean watched) {
		
		String query = "insert into film_list (user_id, film_id, watched)" +
				"values"+ "(" + userId + ", " + filmId + ", " + watched +")";
		
		try {
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Filme adicionado a lista com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível adicionar filme a lista!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateFilmList(String query) {
		
		try {
			Statement stmt = connection.createStatement();
			//"update film_list set user_id = ', film_id = ', watched = true"
			int result = stmt.executeUpdate(query);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Lista atualizado com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível atualizar a lista!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteFilmList(int idFilmList) {
		
		try {
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate("delete from film_list where id = " + idFilmList);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Filme removido da lista com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível remover filme da lista!");
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
		FilmListDAO dao = new FilmListDAO();
		Scanner sc = new Scanner(System.in);
		
		int option;
		
		do {
			System.out.println("================================================");
			System.out.println("1: Lista\n2: Adicionar\n3: Atualizar\n4: Remover");
			option = sc.nextInt();
			
			switch (option) {
				case 1:
					System.out.println("============Filmes============");
					dao.readFilmsList();
					break;
				case 2:
					System.out.println("============Adicionar filme a lista============");
					System.out.println("Digite o id do usuário: ");
					users.readUsers();
					int userId = sc.nextInt();
					
					System.out.println("Digite o id do filme: ");
					films.readFilms();
					int filmId = sc.nextInt();
					
					System.out.println("Filme já assistido? [s/n] ");
					char responseQuestion = sc.next().charAt(0);
					boolean filmWatched = responseQuestion == 's' ? true : false;
					
					dao.insertFilmList(userId, filmId, filmWatched);
					break;
				case 3:
					System.out.println("============Atualizar lista============");
					System.out.println("Escolhe o filme da lista para atualizar: ");
					dao.readFilmsList();
					int filmListId = sc.nextInt();
					
					System.out.println("Digite o id do usuário: ");
					users.readUsers();
					int updateUserId = sc.nextInt();
					
					System.out.println("Digite o id do filme: ");
					films.readFilms();
					int updateFilmId = sc.nextInt();
					
					System.out.println("Filme já assistido? [s/n] ");
					char updateResponseQuestion = sc.next().charAt(0);
					boolean updateFilmWatched = updateResponseQuestion == 's' ? true : false;
					
					String query = "update film_list set user_id = " + updateUserId + ", film_id = " + updateFilmId + ", watched = "+ updateFilmWatched + " where id = " + filmListId;
					
//					String query = "update film_list set " + 
//							"user_id = " + 1 + ", " +
//							"film_id = " + 1 + ", " + 
//							"watched = " + true +
//							"where id = " + 2;
					dao.updateFilmList(query);
					break;
				case 4:
					System.out.println("============Remover filme da lista============");
					System.out.println("Escolhe o filme da lista para remover: ");
					dao.readFilmsList();
					int filmListRemove = sc.nextInt();
					dao.deleteFilmList(filmListRemove);
					break;
				default:
					System.out.println("Opção não encontrada");
					option = 5;
			}
			
		} while(option >= 1 && option <=4);
	}

}

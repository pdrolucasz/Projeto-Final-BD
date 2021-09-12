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

public class FilmDAO {
	
	private Connection connection;
	
	public FilmDAO() {
		connection = new Conection().getConnection();
	}
	
	public void readFilms() {
		
		try {
			Statement stmt = connection.createStatement();
			
			ResultSet rs = stmt.executeQuery("select *, category.name as category_name from film inner join category on film.category_id = category.id");
			
			while(rs.next()) {
				System.out.print("Id do filme: " + rs.getInt("id"));
				System.out.print("\tNome do filme: " + rs.getString("name"));
				System.out.print("\tCategory: " + rs.getString("category_name"));
				System.out.print("\tDuração em minutos: " + rs.getString("duration"));
				System.out.print("\tLançamento: " + rs.getString("release_date"));
				System.out.println();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void insertFilm(String nameFilm, String descriptionFilm, int categoryIdFilm,
			int duration, Date release_date) {
		
		String query = "insert into film (name, description, category_id, duration, release_date)" +
				"values"+ "(" + "'" + nameFilm + "'" + ", "
					+ "'" + descriptionFilm + "'" + ", "  + categoryIdFilm + 
					", "  + duration + ", " + "'" + release_date + "'" +")";
		
		try {
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Filme criado com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível criar filme!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateUser(String query) {
		
		try {
			Statement stmt = connection.createStatement();
			//"update users set name = 'Pedro', release_date = 'Pedro', category_id = 1"
			// duration = 1 release_date = 'Pedro' where id = 1
			int result = stmt.executeUpdate(query);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Filme atualizado com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível atualizar filme!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteFilm(int idUser) {
		
		try {
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate("delete from film where id = " + idUser);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Filme removido com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível remover filme!");
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
		
		CategoryDAO categories = new CategoryDAO();
		FilmDAO dao = new FilmDAO();
		Scanner sc = new Scanner(System.in);
		
		int option;
		
		do {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			System.out.println("================================================");
			System.out.println("1: Filmes\n2: Adicionar\n3: Atualizar\n4: Remover");
			option = sc.nextInt();
			
			switch (option) {
				case 1:
					System.out.println("============Filmes============");
					dao.readFilms();
					break;
				case 2:
					System.out.println("============Adicionar filme============");
					System.out.println("Digite o nome do filme: ");
					String nameFilm = sc.next();
					nameFilm += sc.nextLine();
					
					System.out.println("Digite a descrição do filme: ");
					String descriptionFilm = sc.next();
					descriptionFilm += sc.nextLine();
					
					System.out.println("Escolha a categoria: ");
					categories.readCategories();
					int categoryIdFilm = sc.nextInt();
					
					System.out.println("Duração do filme em minutos: ");
					int durationFilm = sc.nextInt();
					
					System.out.println("Digite a data de lançamento do filme: (dd/MM/yyyy)");
					String releaseDate = sc.next();
					java.util.Date releaseDateFilm = gerarData(releaseDate);
					Date releaseDateFilmSql = new Date(releaseDateFilm.getTime());
					dao.insertFilm(nameFilm, descriptionFilm, categoryIdFilm, durationFilm, releaseDateFilmSql);
					break;
				case 3:
					System.out.println("============Atualizar filme============");
					System.out.println("Escolha o id do usuário");
					dao.readFilms();
					int idFilm = sc.nextInt();
					
					System.out.println("Digite o nome do filme: ");
					String updateNameFilm = sc.next();
					updateNameFilm += sc.nextLine();
					
					System.out.println("Digite a descrição do filme: ");
					String updateDescriptionFilm = sc.next();
					updateDescriptionFilm += sc.nextLine();
					
					System.out.println("Escolha a categoria: ");
					categories.readCategories();
					int updateCategoryIdFilm = sc.nextInt();
					
					System.out.println("Duração do filme em minutos: ");
					int updateDurationFilm = sc.nextInt();
					
					System.out.println("Digite a data de lançamento do filme: (dd/MM/yyyy)");
					String updateReleaseDate = sc.next();
					java.util.Date updateReleaseDateFilm = gerarData(updateReleaseDate);
					Date updateReleaseDateFilmSql = new Date(updateReleaseDateFilm.getTime());
					
					String query = "update film set " +
							"name = " + "'" + updateNameFilm + "'"+ ", " +
							"description = " + "'" + updateDescriptionFilm + "'" + ", " + 
							"category_id = " + updateCategoryIdFilm + ", " +
							"duration = " + updateDurationFilm + ", " +
							"release_date = " + "'" + updateReleaseDateFilmSql + "'" +
							"where id = " + idFilm; 
					dao.updateUser(query);
					break;
				case 4:
					System.out.println("============Remover filme============");
					System.out.println("Escolha o id do filme");
					dao.readFilms();
					int idFilmRemove = sc.nextInt();
					dao.deleteFilm(idFilmRemove);
					break;
				default:
					System.out.println("Opção não encontrada");
					option = 5;
			}
			
		} while(option >= 1 && option <=4);
	}

}

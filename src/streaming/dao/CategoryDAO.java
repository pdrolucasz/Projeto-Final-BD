package streaming.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CategoryDAO {
	
	private Connection connection;
	
	public CategoryDAO() {
		connection = new Conection().getConnection();
	}
	
	public void readCategories() {
		
		try {
			Statement stmt = connection.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from category");
			
			while(rs.next()) {
				System.out.print("Id da categoria: " + rs.getInt("id"));
				System.out.print("\tNome da categoria: " + rs.getString("name"));
				System.out.println();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void insertCategory(String nameCategory) {
		
		String query = "insert into category (name)" + "values"+ "(" + "'" + nameCategory + "'" + ")";
		
		try {
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Categoria criada com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível criar a categoria!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateCategory(String query) {
		
		try {
			Statement stmt = connection.createStatement();
			//"update category set name = 'Comédia' where id = 1"
			int result = stmt.executeUpdate(query);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Categoria atualizada com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível atualizar a categoria!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteCategory(int idCategory) {
		
		try {
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate("delete from category where id = " + idCategory);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Categoria removida com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("Não foi possível remover a categoria!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}
	
	public static void main(String[] args) {
		
		CategoryDAO dao = new CategoryDAO();
		Scanner sc = new Scanner(System.in);
		
		int option;
		
		do {
			System.out.println("================================================");
			System.out.println("1: Categorias\n2: Adicionar\n3: Atualizar\n4: Remover");
			option = sc.nextInt();
			
			switch (option) {
				case 1:
					System.out.println("============Categorias============");
					dao.readCategories();
					break;
				case 2:
					System.out.println("============Adicionar categoria============");
					System.out.println("Digite o nome da categoria: ");
					String nameCategory = sc.next();
					dao.insertCategory(nameCategory);
					break;
				case 3:
					System.out.println("============Atualizar categoria============");
					System.out.println("Escolha o id da categoria");
					dao.readCategories();
					int idCategory = sc.nextInt();
					System.out.println("Digite o nome da categoria: ");
					String updateNameCategory = sc.next();
					String query = "update category set name = "+ "'" + updateNameCategory + "'" + "where id = " + idCategory; 
					dao.updateCategory(query);
					break;
				case 4:
					System.out.println("============Remover categoria============");
					System.out.println("Escolha o id da categoria");
					dao.readCategories();
					int idCategoryRemove = sc.nextInt();
					dao.deleteCategory(idCategoryRemove);
					break;
				default:
					System.out.println("Opção não encontrada");
					option = 5;
			}
			
		} while(option >= 1 && option <=4);
	}

}

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

public class UsersDAO {
	
	private Connection connection;
	
	public UsersDAO() {
		connection = new Conection().getConnection();
	}
	
	public void readUsers() {
		
		try {
			Statement stmt = connection.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from users");
			
			while(rs.next()) {
				System.out.print("Id do usu�rio: " + rs.getInt("id"));
				System.out.print("\tNome do usu�rio: " + rs.getString("name"));
				System.out.print("\tEmail do usu�rio: " + rs.getString("email"));
				System.out.print("\tData de nascimento: " + rs.getString("birth_date"));
				System.out.println();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void insertUser(String nameUser, String emailUser, Date birthDateUser) {
		
		String query = "insert into users (name, email, birth_date)" +
				"values"+ "(" + "'" + nameUser + "'" + ", "
					+ "'" + emailUser + "'" + ", " + "'" + birthDateUser + "'" + ")";
		
		try {
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Usu�rio criado com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("N�o foi poss�vel criar usu�rio!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateUser(String query) {
		
		try {
			Statement stmt = connection.createStatement();
			//"update users set name = 'Pedro', email = 'Pedro', birth_date = 'Pedro' where id = 1"
			int result = stmt.executeUpdate(query);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Usu�rio atualizado com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("N�o foi poss�vel atualizar usu�rio!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteUser(int idUser) {
		
		try {
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate("delete from users where id = " + idUser);
			if (result == 1) {
				System.out.println("================================================");
				System.out.println("Usu�rio removido com sucesso!");
			} else {
				System.out.println("================================================");
				System.out.println("N�o foi poss�vel remover usu�rio!");
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
		
		UsersDAO dao = new UsersDAO();
		Scanner sc = new Scanner(System.in);
		
		int option;
		
		do {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			System.out.println("================================================");
			System.out.println("1: Usu�rios\n2: Adicionar\n3: Atualizar\n4: Remover");
			option = sc.nextInt();
			
			switch (option) {
				case 1:
					System.out.println("============Usu�rios============");
					dao.readUsers();
					break;
				case 2:
					System.out.println("============Adicionar usu�rio============");
					System.out.println("Digite o nome do usu�rio: ");
					String nameUser = sc.next();
					nameUser += sc.nextLine();
					
					System.out.println("Digite o email do usu�rio: ");
					String emailUser = sc.next();
					emailUser += sc.nextLine();
					
					System.out.println("Digite a data de nascimento do usu�rio: (dd/MM/yyyy)");
					String birthDate = sc.next();
					java.util.Date birthDateUser = gerarData(birthDate);
					Date birthDateUserSql = new Date(birthDateUser.getTime());
					
					dao.insertUser(nameUser, emailUser, birthDateUserSql);
					break;
				case 3:
					System.out.println("============Atualizar usu�rio============");
					System.out.println("Escolha o id do usu�rio");
					dao.readUsers();
					int idUser = sc.nextInt();
					
					System.out.println("Digite o nome do usu�rio: ");
					String updateNameUser = sc.next();
					updateNameUser += sc.nextLine();
					
					System.out.println("Digite o email do usu�rio: ");
					String updateEmailUser = sc.next();
					updateEmailUser += sc.nextLine();
					
					System.out.println("Digite a data de nascimento do usu�rio: (dd/MM/yyyy)");
					String updateBirthDate = sc.next();
					java.util.Date updateBirthDateUser = gerarData(updateBirthDate);
					Date updateBirthDateUserSql = new Date(updateBirthDateUser.getTime());
					
					String query = "update users set " +
							"name = " + "'" + updateNameUser + "'"+ ", " +
							"email = " + "'" + updateEmailUser + "'" + ", " + 
							"birth_date = " + "'" + updateBirthDateUserSql + "'" +
							"where id = " + idUser; 
					dao.updateUser(query);
					break;
				case 4:
					System.out.println("============Remover usu�rio============");
					System.out.println("Escolha o id do usu�rio");
					dao.readUsers();
					int idUserRemove = sc.nextInt();
					dao.deleteUser(idUserRemove);
					break;
				default:
					System.out.println("Op��o n�o encontrada");
					option = 5;
			}
			
		} while(option >= 1 && option <=4);
	}
	
}

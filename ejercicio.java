package ejerciciopracticoud2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ejercicio {

	public static void main(String[] args) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db2amtbis","root", "root");
		Scanner reader = new Scanner(System.in);
        boolean salir = false;
        while(!salir)
        {
        	
        	System.out.println("********  Elija una de las siguientes opciones para la tabla alumnos ********");
        	System.out.println();
        	System.out.println("1.Inserte un nuevo registro");
        	System.out.println("2.Elimine un registro");
        	System.out.println("3.Actualizar tabla");
        	System.out.println("4.Devolver registro encontrado");
        	System.out.println("5.Devolver todos los alumnos de una provincia en concreto");
        	System.out.println("6.Terminar programa");
        	System.out.println();
        	System.out.println();
        	int opcion = 0;
        	boolean avanzar = false;
        	int fallo = 0;
        	while(!avanzar)
        	{
        		try {
        			if (fallo > 0)
        				System.out.println("Elija una opción correcta");
        			if (fallo == 0)
        				System.out.println("Elija una opción");
        			opcion = reader.nextInt();
        			switch(opcion)
        			{
        				case 1:
        					avanzar = true;
        					break;
        				case 2:
        					avanzar = true;
        					break;
        				case 3:
        					avanzar = true;
        					break;
        				case 4:
        					avanzar = true;
        					break;
        				case 5:
        					avanzar = true;
        					break;
        				case 6:
        					avanzar = true;
        					break;
        			}	
        		}catch(InputMismatchException e)
        		{
        			System.out.println("(Debes Insertar un número)\n");
        			fallo++;
        			reader.next();
        		}
        	}
        	System.out.println();
        	if(opcion == 1)
        		insertar(conn);
        	if(opcion == 2)
        		eliminar(conn);
        	if(opcion == 3)
        		actualizar(conn);
        	if(opcion == 4)
        		buscar(conn);
        	if(opcion == 5)
        		provincias(conn);
        	if(opcion == 6)
        		salir = true;
        }
	}
	
	public static void insertar(Connection conn) throws SQLException {
		Statement sent = conn.createStatement();
		Scanner reader = new Scanner(System.in);
		System.out.println("Que valores desea insertar de un alumno nuevo en la tabla alumnos\n");
		System.out.println("Introduzca el valor de IDN:");
		String IDN = reader.nextLine();
		System.out.println("Introduzca el valor de APENOM:");
		String APENOM = reader.nextLine();
		System.out.println("Introduzca el valor de DIRECC:");
		String DIRECC = reader.nextLine();
		System.out.println("Introduzca el valor de PROV:");
		String PROV = reader.nextLine();
		System.out.println("Introduzca el valor de EMAIL:");
		String EMAIL = reader.nextLine();
		LocalDate DATE = LocalDate.now();
		LocalTime TIME = LocalTime.now();
		Instant TIMESTAMP = Instant.now();
		String ssql = "INSERT IGNORE INTO ALUMNOS VALUES ('"+ IDN +"',' "+ APENOM +" ','"+ DIRECC +"','"
		+ PROV +"','"+ EMAIL +"', '"+ DATE +"', '"+TIME+"', '"+TIMESTAMP+"')";
		int rs = sent.executeUpdate(ssql);
		System.out.println(ssql);
		System.out.println(rs + " filas afectadas");
	}
	
	public static void eliminar(Connection conn) throws SQLException {
		Statement sent = conn.createStatement();
		Scanner reader = new Scanner(System.in);
		System.out.println("Que alumno desea eliminar\n");
		System.out.println("Introduzca el valor de su IDN:");
		String IDN = reader.nextLine();
	
		String ssql = "DELETE FROM ALUMNOS WHERE IDN="+ IDN +"";
		int rs = sent.executeUpdate(ssql);
		System.out.println(ssql);
		System.out.println(rs + " filas afectadas");
	}
	
	public static void actualizar(Connection conn) throws SQLException {
		Statement sent = conn.createStatement();
		Scanner reader = new Scanner(System.in);
		System.out.println("Que campo deseas cambiar");
		String CAMPO = reader.nextLine();
		System.out.println("Que valor deseas actualizar");
		String valor = reader.nextLine();
		LocalDate DATE = LocalDate.now();
		LocalTime TIME = LocalTime.now();
		Long datetime = System.currentTimeMillis();
        Timestamp TIMESTAMP = new Timestamp(datetime);
        System.out.println(TIMESTAMP);
		String ssql = "UPDATE ALUMNOS SET " + CAMPO + "= " +valor+", FECHA = '" +DATE+"', HORA = '" +TIME+"', MARCATIEMPO = '" + 
        TIMESTAMP + "'";
		int rs = sent.executeUpdate(ssql);
		System.out.println(ssql);
		System.out.println(rs + " filas afectadas");
	}
	public static void buscar(Connection conn) throws SQLException {
		Statement sent = conn.createStatement();
		Scanner reader = new Scanner(System.in);
		System.out.println("Que registro concreto quieres devolver(Introduzca su IDN)");
		String IDN = reader.nextLine();
		String ssql = "SELECT * FROM ALUMNOS WHERE IDN = " + IDN;
		ResultSet rs = sent.executeQuery(ssql);
		System.out.println();
		System.out.println("IDN               APENOM                   DIRECC            PROV       EMAIL            FECHA         HORA          TIMESTAMP");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
		while(rs.next())
		{
			System.out.printf("%d    %11s %22s %11s %19s %17s %11s %22s\n", rs.getInt("IDN"),rs.getString("APENOM"),
					rs.getString("DIRECC"),rs.getString("PROV"),rs.getString("EMAIL"), rs.getString("FECHA"),  rs.getString("HORA")
					,rs.getString("MARCATIEMPO"));
		}
		rs.close();
		System.out.println();
	}
	
	private static void provincias(Connection conn) throws SQLException {
		Statement sent = conn.createStatement();
		Scanner reader = new Scanner(System.in);
		System.out.println("De que provincia quieres consultar los alumnos");
		String PROV = reader.nextLine();
		String ssql = "SELECT * FROM ALUMNOS WHERE PROV = '" + PROV + "'";
		ResultSet rs = sent.executeQuery(ssql);
		System.out.println();
		System.out.println("IDN               APENOM                   DIRECC            PROV       EMAIL              FECHA         HORA          TIMESTAMP");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
		while(rs.next())
		{
			System.out.printf("%d    %11s %22s %11s %19s %17s %11s %22s\n", rs.getInt("IDN"),rs.getString("APENOM"),
					rs.getString("DIRECC"),rs.getString("PROV"),rs.getString("EMAIL"), rs.getString("FECHA"),  rs.getString("HORA")
					,rs.getString("MARCATIEMPO"));
		}
		rs.close();
		System.out.println();
	}


}

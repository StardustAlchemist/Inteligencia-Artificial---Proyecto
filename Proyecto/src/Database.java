import java.sql.*;


public class Database {
	
	
	public void eliminar_tablas() throws ClassNotFoundException {
		// load the sqlite-JDBC driver using the current class loader
		        Class.forName("org.sqlite.JDBC");

		        Connection connection = null;
		        try {
		            // create a database connection
		            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		            Statement statement = connection.createStatement();
		            statement.setQueryTimeout(30);  // set timeout to 30 sec.

		            statement.executeUpdate("drop table if exists Frecuencia");
		            statement.executeUpdate("drop table if exists Palabra");
		            statement.executeUpdate("drop table if exists Etiqueta");
		            
		        } catch (SQLException e) {
		            // if the error message is "out of memory", 
		            // it probably means no database file is found
		            System.err.println(e.getMessage());
		        } finally {
		            try {
		                if (connection != null) {
		                    connection.close();
		                }
		            } catch (SQLException e) {
		                // connection close failed.
		                System.err.println(e);
		            }
		        }
		    }
	
	
	public void crear_tablas() throws ClassNotFoundException {
		// load the sqlite-JDBC driver using the current class loader
		        Class.forName("org.sqlite.JDBC");

		        Connection connection = null;
		        try {
		            // create a database connection
		            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		            Statement statement = connection.createStatement();
		            statement.setQueryTimeout(30);  // set timeout to 30 sec.
		            
		            
		            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
		            		+ "Palabra("
		            		+ "id PRIMARY KEY, "
		            		+ "name string UNIQUE"
		            		+ ")");
		            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
		            		+ "Etiqueta("
		            		+ "id PRIMARY KEY,"
		            		+ "name string UNIQUE"
		            		+ ")");
		            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
		            		+ "Frecuencia("
		            		+ "id_palabra integer, "
		            		+ "id_etiqueta integer, "
		            		+ "conteo integer,"
		            		+ "FOREIGN KEY(id_palabra) REFERENCES Palabra(id),"
		            		+ "FOREIGN KEY(id_etiqueta) REFERENCES Etiqueta(id),"
		            		+ "PRiMARY KEY(id_palabra,id_etiqueta)"
		            		+ ")");
		        } catch (SQLException e) {
		            // if the error message is "out of memory", 
		            // it probably means no database file is found
		            System.err.println(e.getMessage());
		        } finally {
		            try {
		                if (connection != null) {
		                    connection.close();
		                }
		            } catch (SQLException e) {
		                // connection close failed.
		                System.err.println(e);
		            }
		        }
		    }
	
	
	
	public void insertar_palabra(String palabra, String etiqueta) throws ClassNotFoundException {
		// load the sqlite-JDBC driver using the current class loader
		        Class.forName("org.sqlite.JDBC");

		        Connection connection = null;
		        try {
		            // create a database connection
		            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		            Statement statement = connection.createStatement();
		            statement.setQueryTimeout(30);  // set timeout to 30 sec.

		            ResultSet existe_palabra = statement.executeQuery("select * from Palabra Where name = '"+palabra+"'");
		            
		            if(existe_palabra.next()) {
		            	//existe la palabra
		            	int id_palabra = existe_palabra.getInt("id");
		            	ResultSet existe_etiqueta = statement.executeQuery("select * from Etiqueta Where name = '"+etiqueta+"'");
		            	if(existe_etiqueta.next()) {
		            		int id_etiqueta = existe_etiqueta.getInt("id");
		            		//existe la etiqueta
		            		ResultSet existe_frecuencia = statement.executeQuery("select * from Frecuencia Where id_palabra="+id_palabra+" AND id_etiqueta="+id_etiqueta);
			            	if(existe_frecuencia.next()) {
			            		//existe en la tabla frequencia
			            		statement.executeUpdate("update Frecuencia SET conteo=conteo+1 WHERE id_palabra="+id_palabra+" AND id_etiqueta="+id_etiqueta);
			            	}else {
			            		//no existe en la tabla frequencia
			            		statement.executeUpdate("insert into Frecuencia(id_palabra,id_etiqueta,conteo) values("+id_palabra+","+id_etiqueta+",1)");
			            	}
		            	}else {
		            		//no existe la etiqueta
		            		statement.executeUpdate("insert into Etiqueta(name) values('"+etiqueta+"')");
		            		existe_etiqueta = statement.executeQuery("select * from Etiqueta Where name = '"+etiqueta+"'");
		            		existe_etiqueta.next();
		            		int id_etiqueta = existe_etiqueta.getInt("id");
		            		statement.executeUpdate("insert into Frecuencia(id_palabra,id_etiqueta,conteo) values("+id_palabra+","+id_etiqueta+",1)");
		            	}
		            }else {
		            	//no existe la palabra
		            	ResultSet existe_etiqueta = statement.executeQuery("select * from Etiqueta Where name = '"+etiqueta+"'");
		            	if(existe_etiqueta.next()) {
		            		int id_etiqueta = existe_etiqueta.getInt("id");
		            		//existe la etiqueta
		            		statement.executeUpdate("insert into Palabra(name) values('"+palabra+"')");
		            		existe_palabra = statement.executeQuery("select * from Palabra Where name = '"+palabra+"'");
		            		existe_palabra.next();
		            		int id_palabra = existe_etiqueta.getInt("id");
		            		statement.executeUpdate("insert into Frecuencia(id_palabra,id_etiqueta,conteo) values("+id_palabra+","+id_etiqueta+",1)");
		            	}else {
		            		//no existe la etiqueta
		            		statement.executeUpdate("insert into Etiqueta(name) values('"+etiqueta+"')");
		            		statement.executeUpdate("insert into Palabra(name) values('"+palabra+"')");
		            		existe_palabra = statement.executeQuery("select * from Palabra Where name = '"+palabra+"'");
		            		existe_palabra.next();
		            		int id_palabra = existe_etiqueta.getInt("id");
		            		existe_etiqueta = statement.executeQuery("select * from Etiqueta Where name = '"+etiqueta+"'");
		            		existe_etiqueta.next();
		            		int id_etiqueta = existe_etiqueta.getInt("id");
		            		statement.executeUpdate("insert into Frecuencia(id_palabra,id_etiqueta,conteo) values("+id_palabra+","+id_etiqueta+",1)");
		            	}
		            }		            
		        } catch (SQLException e) {
		            // if the error message is "out of memory", 
		            // it probably means no database file is found
		            System.err.println(e.getMessage());
		        } finally {
		            try {
		                if (connection != null) {
		                    connection.close();
		                }
		            } catch (SQLException e) {
		                // connection close failed.
		                System.err.println(e);
		            }
		        }
		    }
	

    public void mostrar_tabla() throws ClassNotFoundException {
		// load the sqlite-JDBC driver using the current class loader
		        Class.forName("org.sqlite.JDBC");

		        Connection connection = null;
		        try {
		            // create a database connection
		            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		            Statement statement = connection.createStatement();
		            statement.setQueryTimeout(30);  // set timeout to 30 sec.

		            ResultSet rs = statement.executeQuery("SELECT Palabra.name, Frecuencia.conteo FROM Palabra INNER JOIN Frecuencia ON Palabra.id = Frecuencia.id_palabra");
		            while (rs.next()) {
		                System.out.println("Nombre "+rs.getString("name")+" Conteo "+rs.getInt("conteo"));
		            }
		        } catch (SQLException e) {
		            // if the error message is "out of memory", 
		            // it probably means no database file is found
		            System.err.println(e.getMessage());
		        } finally {
		            try {
		                if (connection != null) {
		                    connection.close();
		                }
		            } catch (SQLException e) {
		                // connection close failed.
		                System.err.println(e);
		            }
		        }
		    }
	
	
    public boolean existe_palabra(String word) throws ClassNotFoundException {
		// load the sqlite-JDBC driver using the current class loader
		        Class.forName("org.sqlite.JDBC");

		        Connection connection = null;
		        try {
		            // create a database connection
		            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		            Statement statement = connection.createStatement();
		            statement.setQueryTimeout(30);  // set timeout to 30 sec.

		            ResultSet rs = statement.executeQuery("select * from Palabra Where name = '"+word+"'");
		            while (rs.next()) {
		                return true;
		            }
		        } catch (SQLException e) {
		            // if the error message is "out of memory", 
		            // it probably means no database file is found
		            System.err.println(e.getMessage());
		        } finally {
		            try {
		                if (connection != null) {
		                    connection.close();
		                }
		            } catch (SQLException e) {
		                // connection close failed.
		                System.err.println(e);
		            }
		        }
		        return false;
		    }
    
    
    public boolean existe_etiqueta(String word) throws ClassNotFoundException {
		// load the sqlite-JDBC driver using the current class loader
		        Class.forName("org.sqlite.JDBC");

		        Connection connection = null;
		        try {
		            // create a database connection
		            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		            Statement statement = connection.createStatement();
		            statement.setQueryTimeout(30);  // set timeout to 30 sec.

		            ResultSet rs = statement.executeQuery("select * from Etiqueta Where name = '"+word+"'");
		            while (rs.next()) {
		                return true;
		            }
		        } catch (SQLException e) {
		            // if the error message is "out of memory", 
		            // it probably means no database file is found
		            System.err.println(e.getMessage());
		        } finally {
		            try {
		                if (connection != null) {
		                    connection.close();
		                }
		            } catch (SQLException e) {
		                // connection close failed.
		                System.err.println(e);
		            }
		        }
		        return false;
		    }
	
	public void connectDB() throws ClassNotFoundException {
		// load the sqlite-JDBC driver using the current class loader
		        Class.forName("org.sqlite.JDBC");

		        Connection connection = null;
		        try {
		            // create a database connection
		            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		            Statement statement = connection.createStatement();
		            statement.setQueryTimeout(30);  // set timeout to 30 sec.

		            statement.executeUpdate("drop table if exists Frecuencia");
		            statement.executeUpdate("drop table if exists Palabra");
		            statement.executeUpdate("drop table if exists Etiqueta");
		            
		            
		            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
		            		+ "Palabra("
		            		+ "id PRIMARY KEY, "
		            		+ "name string UNIQUE"
		            		+ ")");
		            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
		            		+ "Etiqueta("
		            		+ "id PRIMARY KEY"
		            		+ ", name string UNIQUE"
		            		+ ")");
		            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
		            		+ "Frecuencia("
		            		+ "id_palabra integer, "
		            		+ "id_etiqueta integer, "
		            		+ "conteo integer,"
		            		+ "FOREIGN KEY(id_palabra) REFERENCES Palabra(id),"
		            		+ "FOREIGN KEY(id_etiqueta) REFERENCES Etiqueta(id),"
		            		+ "PRiMARY KEY(id_palabra,id_etiqueta)"
		            		+ ")");
		        } catch (SQLException e) {
		            // if the error message is "out of memory", 
		            // it probably means no database file is found
		            System.err.println(e.getMessage());
		        } finally {
		            try {
		                if (connection != null) {
		                    connection.close();
		                }
		            } catch (SQLException e) {
		                // connection close failed.
		                System.err.println(e);
		            }
		        }
		    }

	
}

package sql.config;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
=======
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
>>>>>>> 682628f10b4231d1259ea5f51b647b5f995d5cc3
import java.util.Properties;

public class DatabaseConfig {

    public static Connection getConnection() throws IOException, SQLException {
        Properties props = new Properties();

        // Charge le fichier database.properties à partir des ressources
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new IOException("Properties file not found: database.properties");
            }

            props.load(input);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
<<<<<<< HEAD

    public static void initializeDatabase() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root"; // À remplacer par la valeur de database.properties si besoin
        String password = "Justine@2227"; // À remplacer par la valeur de database.properties si besoin
        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password)) {
            executeSqlScript(conn, "/sql/schema.sql");
            executeSqlScript(conn, "/sql/insertData.sql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeSqlScript(Connection conn, String resourcePath) throws Exception {
        try (InputStream is = DatabaseConfig.class.getResourceAsStream(resourcePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String[] statements = sb.toString().split(";");
            try (Statement stmt = conn.createStatement()) {
                for (String sql : statements) {
                    sql = sql.trim();
                    if (!sql.isEmpty() && !sql.startsWith("--")) {
                        stmt.execute(sql);
                    }
                }
            }
        }
    }
=======
>>>>>>> 682628f10b4231d1259ea5f51b647b5f995d5cc3
}

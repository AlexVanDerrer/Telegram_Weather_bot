import java.sql.*;


class DB {
    private static Connection conn;
    private static Statement statmt;
    private static ResultSet resSet;

    static void Conn() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:SQL_TelBot.s3db");

        System.out.println("База Подключена!");
    }

    // сохдание таблицы
    static void CreateTable() throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'Users' " +
                "('id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'Name' Char, " +
                "'LastName' Char, " +
                "'Chat_id' INT, " +
                "'Subscribe' Bool);");

        System.out.println("Таблица создана или уже существует.");
    }

    // заполнение таблицы
    static void WriteDB(String sql) throws SQLException {
        statmt.execute(sql);
        System.out.println("Таблица заполнена");
    }

    // изменение таблицы
    static void DelDB(String sql) throws SQLException {
        statmt = conn.createStatement();
        statmt.execute(sql);
        System.out.println("Таблица изменена");
    }

    // вывод данных таблицы
    static void ReadDB() throws ClassNotFoundException, SQLException {
        resSet = statmt.executeQuery("SELECT * FROM Users");

        while (resSet.next()) {
            int id = resSet.getInt("id");
            String name = resSet.getString("Name");
            String lastName = resSet.getString("LastName");
            String ChatId = resSet.getString("Chat_id");
            boolean sub = resSet.getBoolean("Subscribe");
            System.out.println("ID = " + id);
            System.out.println("Name = " + name);
            System.out.println("LastName = " + lastName);
            System.out.println("Chat_id = " + ChatId);
            System.out.println("Subscribe = " + sub);
        }

        System.out.println("Таблица выведена");
    }

    // Закрытие
    static void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}

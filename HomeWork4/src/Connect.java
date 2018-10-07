import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	public void start() {
		Connection con = null; // соединение с бд
        Statement stmt = null; // хранит и выполняет sql запросы
        ResultSet res = null; // получает результаты выполнения sql запросов
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

        try {
            // динамическая регистрация драйвера SQLite
            Driver d = (Driver) Class.forName("org.sqlite.JDBC").newInstance();

            //создаем подключение к бд к базе данным по пути
            String url = "jdbc:sqlite:D:\\Java\\SQLite\\Work\\Student.db";
            con = DriverManager.getConnection(url);

            //подготовка SQL запроса
            String sql = "SELECT * FROM InfoCourse";
            stmt = (Statement) con.createStatement();

            String spec = "start";
            while(!spec.equals("Выход")) {
            	spec = specialization(read);
            	
            	// выполнение SQL запроса
                res = stmt.executeQuery(sql);
	            while (res.next()){
	            	if(spec.equals(res.getString("spezialization"))) {
	            		System.out.println();
		                System.out.println("Имя: " + res.getString("name"));
		                System.out.println("Фамилия: " + res.getString("surname"));
		                System.out.println("Возраст: " + res.getInt("age"));
		                System.out.println("Факультет: " + res.getString("faculty"));
		                System.out.println("Специальность: " + res.getString("spezialization"));
		                System.out.println("Номер курса: " + res.getInt("number_of_course"));
		                System.out.println("Дата поступления: " + res.getString("date_start"));
		                System.out.println("Дата окончания: " + res.getString("date_end"));
		                System.out.println();
	            	}
	            }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            {
                try{
                    if (res!=null)res.close();
                    if (stmt!=null)stmt.close();
                    if (con!=null)con.close();
                }catch (Exception e){e.printStackTrace();}
            }
        }
	}
	
	private String specialization(BufferedReader read) {
		System.out.println("Вывести студентов специальности:" );
		System.out.println("1 - Медицинская электроника" );
		System.out.println("2 - Программируемые мобильные системы" );
		System.out.println("3 - Автоматизированные системы обработки информации" );
		System.out.println("4 - Микро - и наноэлектронные технологии и системы" );
		System.out.println("5 - Квантовые информационные системы" );
		System.out.println("6 - Нанотехнологии и наноматериалы в электронике" );
		System.out.println("7 - Выход" );
		int select = input(read);
		String s = checkSpecialization(select);
		return s;
	}
	
	private int input(BufferedReader read) {
		int number = 0;
		try {
			number = Integer.parseInt(read.readLine());
		}catch(IOException e) {e.printStackTrace();}
		return number;
	}
	
	private String checkSpecialization(int select) {
		String spec = "start";
		if(select == 1) {spec = "Медицинская электроника";}
		else if(select == 2) {spec = "Программируемые мобильные системы";}
		else if(select == 3) {spec = "Автоматизированные системы обработки информации";}
		else if(select == 4) {spec = "Микро - и наноэлектронные технологии и системы";}
		else if(select == 5) {spec = "Квантовые информационные системы";}
		else if(select == 6) {spec = "Нанотехнологии и наноматериалы в электронике";}
		else if(select == 7) {spec = "Выход";}
		return spec;
	}
}

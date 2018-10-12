import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	Connection con = null; // соединение с бд
    Statement stmt = null; // хранит и выполняет sql запросы
    ResultSet res = null; // получает результаты выполнения sql запросов
    BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
    
	public void start() {
		int s = 0;
        while(s!=3) {
        	s = menuAction();
        	if (s == 1) {in();}
        	else if(s == 2) {request();}
        }
	}
	
	private int menuAction() {
		System.out.println();
		System.out.println("Действие:" );
		System.out.println("1 - Добавть информацию в бд" );
		System.out.println("2 - Вывести информацию из бд" );
		System.out.println("3 - Выход" );
		int s = input();
		
		return s;
	}
	
	private String specialization() {
		System.out.println();
		System.out.println("Вывести студентов специальности:" );
		System.out.println("1 - Медицинская электроника" );
		System.out.println("2 - Программируемые мобильные системы" );
		System.out.println("3 - Автоматизированные системы обработки информации" );
		System.out.println("4 - Микро - и наноэлектронные технологии и системы" );
		System.out.println("5 - Квантовые информационные системы" );
		System.out.println("6 - Нанотехнологии и наноматериалы в электронике" );
		System.out.println("7 - Выход" );
		int select = input();
		String s = checkSpecialization(select);
		return s;
	}
	
	private int specializationId() {
		System.out.println();
		System.out.println("Какая специальность специальность:" );
		System.out.println("1 - Медицинская электроника" );
		System.out.println("2 - Программируемые мобильные системы" );
		System.out.println("3 - Автоматизированные системы обработки информации" );
		System.out.println("4 - Микро - и наноэлектронные технологии и системы" );
		System.out.println("5 - Квантовые информационные системы" );
		System.out.println("6 - Нанотехнологии и наноматериалы в электронике" );
		int select = input();
		return select;
	}
	
	private int numberId() {
		System.out.println();
		System.out.println("Номер курса" );
		System.out.println("1 - первый курс" );
		System.out.println("2 - второй курс" );
		System.out.println("3 - третий курс" );
		System.out.println("4 - четвертый курс" );
		int select = input();
		return select;
	}
	
	private int facultyId() {
		System.out.println();
		System.out.println("Какой факультет" );
		System.out.println("1 - Факультет компьютерного проектирования" );
		System.out.println("2 - Факультет информационных технологий и управления" );
		System.out.println("3 - Факультет радиотехники и электроники" );
		int select = input();
		return select;
	}
	
	private int dateId() {
		System.out.println();
		System.out.println("Год окончания" );
		System.out.println("1 - 01.09.19" );
		System.out.println("2 - 01.09.20" );
		System.out.println("3 - 01.09.21" );
		System.out.println("3 - 01.09.22" );
		int select = input();
		return select;
	}
	
	private int input() {
		int number = 0;
		try {
			number = Integer.parseInt(read.readLine());
		}catch(IOException e) {e.printStackTrace();}
		return number;
	}
	
	private String inputString() {
		String s = null;
		try {
			s = read.readLine();
		}catch(IOException e) {e.printStackTrace();}
		return s;
	}
	
	private void connect() {
		try {
		// динамическая регистрация драйвера SQLite
        Driver d = (Driver) Class.forName("org.sqlite.JDBC").newInstance();

        //создаем подключение к бд к базе данным по пути
        String url = "jdbc:sqlite:D:\\Java\\SQLite\\Work\\Student.db";
        con = DriverManager.getConnection(url);
		}catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
	}
	
	private void in() {
		System.out.println();
		System.out.println("Введите имя студента");
		String name = inputString();
		System.out.println("Введите фамилию студента");
		String surname = inputString();
		System.out.println("Введите возраст студента");
		int age = input();
		int faculty = facultyId();
		int specialization = specializationId();
		int number = numberId();
		int date = dateId();
		insert(faculty, specialization, number, date, name, surname, age);
	}
	
	private void insert(int faculty, int specialization, int number, int date, String name, String surname, int age) {
	    try{
	    	connect();
	    	String queryStudent = "INSERT INTO Student (name, surname, age) VALUES (?, ?, ?)"; 
		    String queryInfo = "INSERT INTO Info (faculty_id, specialization_id, number_of_course_id, date_id, student_id) VALUES (?, ?, ?, ?, ?)";        
		    PreparedStatement statement1 = con.prepareStatement(queryStudent);
		    PreparedStatement statement2 = con.prepareStatement(queryInfo);
		    statement1.setString(1, name);
		    statement1.setString(2, surname);
		    statement1.setInt(3, age);
		    statement1.executeUpdate();
		    int student = maxId();
		    
		    statement2.setInt(1, faculty);
		    statement2.setInt(2, specialization);
		    statement2.setInt(3, number);
		    statement2.setInt(4, date);
		    statement2.setInt(5, student);
		    statement2.executeUpdate();
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
	
	private int maxId() {
		int r = 0;
		try {
            //подготовка SQL запроса
            String sql = "SELECT * FROM MaxIdStudent";
            stmt = (Statement) con.createStatement();

            // выполнение SQL запроса
            res = stmt.executeQuery(sql);
		    r = res.getInt("id");

            
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
		
		return r;
	}
	
	
	private void request() {
		try {
			connect();
			
            //подготовка SQL запроса
            String sql = "SELECT * FROM InfoCourse";
            stmt = (Statement) con.createStatement();

            String spec = "start";
            while(!spec.equals("Выход")) {
            	spec = specialization();
            	
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
}

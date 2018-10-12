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
	Connection con = null; // ���������� � ��
    Statement stmt = null; // ������ � ��������� sql �������
    ResultSet res = null; // �������� ���������� ���������� sql ��������
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
		System.out.println("��������:" );
		System.out.println("1 - ������� ���������� � ��" );
		System.out.println("2 - ������� ���������� �� ��" );
		System.out.println("3 - �����" );
		int s = input();
		
		return s;
	}
	
	private String specialization() {
		System.out.println();
		System.out.println("������� ��������� �������������:" );
		System.out.println("1 - ����������� �����������" );
		System.out.println("2 - ��������������� ��������� �������" );
		System.out.println("3 - ������������������ ������� ��������� ����������" );
		System.out.println("4 - ����� - � ��������������� ���������� � �������" );
		System.out.println("5 - ��������� �������������� �������" );
		System.out.println("6 - �������������� � ������������� � �����������" );
		System.out.println("7 - �����" );
		int select = input();
		String s = checkSpecialization(select);
		return s;
	}
	
	private int specializationId() {
		System.out.println();
		System.out.println("����� ������������� �������������:" );
		System.out.println("1 - ����������� �����������" );
		System.out.println("2 - ��������������� ��������� �������" );
		System.out.println("3 - ������������������ ������� ��������� ����������" );
		System.out.println("4 - ����� - � ��������������� ���������� � �������" );
		System.out.println("5 - ��������� �������������� �������" );
		System.out.println("6 - �������������� � ������������� � �����������" );
		int select = input();
		return select;
	}
	
	private int numberId() {
		System.out.println();
		System.out.println("����� �����" );
		System.out.println("1 - ������ ����" );
		System.out.println("2 - ������ ����" );
		System.out.println("3 - ������ ����" );
		System.out.println("4 - ��������� ����" );
		int select = input();
		return select;
	}
	
	private int facultyId() {
		System.out.println();
		System.out.println("����� ���������" );
		System.out.println("1 - ��������� ������������� ��������������" );
		System.out.println("2 - ��������� �������������� ���������� � ����������" );
		System.out.println("3 - ��������� ������������ � �����������" );
		int select = input();
		return select;
	}
	
	private int dateId() {
		System.out.println();
		System.out.println("��� ���������" );
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
		// ������������ ����������� �������� SQLite
        Driver d = (Driver) Class.forName("org.sqlite.JDBC").newInstance();

        //������� ����������� � �� � ���� ������ �� ����
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
		System.out.println("������� ��� ��������");
		String name = inputString();
		System.out.println("������� ������� ��������");
		String surname = inputString();
		System.out.println("������� ������� ��������");
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
		if(select == 1) {spec = "����������� �����������";}
		else if(select == 2) {spec = "��������������� ��������� �������";}
		else if(select == 3) {spec = "������������������ ������� ��������� ����������";}
		else if(select == 4) {spec = "����� - � ��������������� ���������� � �������";}
		else if(select == 5) {spec = "��������� �������������� �������";}
		else if(select == 6) {spec = "�������������� � ������������� � �����������";}
		else if(select == 7) {spec = "�����";}
		return spec;
	}
	
	private int maxId() {
		int r = 0;
		try {
            //���������� SQL �������
            String sql = "SELECT * FROM MaxIdStudent";
            stmt = (Statement) con.createStatement();

            // ���������� SQL �������
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
			
            //���������� SQL �������
            String sql = "SELECT * FROM InfoCourse";
            stmt = (Statement) con.createStatement();

            String spec = "start";
            while(!spec.equals("�����")) {
            	spec = specialization();
            	
            	// ���������� SQL �������
                res = stmt.executeQuery(sql);
	            while (res.next()){
	            	if(spec.equals(res.getString("spezialization"))) {
	            		System.out.println();
		                System.out.println("���: " + res.getString("name"));
		                System.out.println("�������: " + res.getString("surname"));
		                System.out.println("�������: " + res.getInt("age"));
		                System.out.println("���������: " + res.getString("faculty"));
		                System.out.println("�������������: " + res.getString("spezialization"));
		                System.out.println("����� �����: " + res.getInt("number_of_course"));
		                System.out.println("���� �����������: " + res.getString("date_start"));
		                System.out.println("���� ���������: " + res.getString("date_end"));
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

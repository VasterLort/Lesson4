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
		Connection con = null; // ���������� � ��
        Statement stmt = null; // ������ � ��������� sql �������
        ResultSet res = null; // �������� ���������� ���������� sql ��������
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

        try {
            // ������������ ����������� �������� SQLite
            Driver d = (Driver) Class.forName("org.sqlite.JDBC").newInstance();

            //������� ����������� � �� � ���� ������ �� ����
            String url = "jdbc:sqlite:D:\\Java\\SQLite\\Work\\Student.db";
            con = DriverManager.getConnection(url);

            //���������� SQL �������
            String sql = "SELECT * FROM InfoCourse";
            stmt = (Statement) con.createStatement();

            String spec = "start";
            while(!spec.equals("�����")) {
            	spec = specialization(read);
            	
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
	
	private String specialization(BufferedReader read) {
		System.out.println("������� ��������� �������������:" );
		System.out.println("1 - ����������� �����������" );
		System.out.println("2 - ��������������� ��������� �������" );
		System.out.println("3 - ������������������ ������� ��������� ����������" );
		System.out.println("4 - ����� - � ��������������� ���������� � �������" );
		System.out.println("5 - ��������� �������������� �������" );
		System.out.println("6 - �������������� � ������������� � �����������" );
		System.out.println("7 - �����" );
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
		if(select == 1) {spec = "����������� �����������";}
		else if(select == 2) {spec = "��������������� ��������� �������";}
		else if(select == 3) {spec = "������������������ ������� ��������� ����������";}
		else if(select == 4) {spec = "����� - � ��������������� ���������� � �������";}
		else if(select == 5) {spec = "��������� �������������� �������";}
		else if(select == 6) {spec = "�������������� � ������������� � �����������";}
		else if(select == 7) {spec = "�����";}
		return spec;
	}
}

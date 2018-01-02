package com.bellinfo.patient.portal.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bellinfo.patient.portal.modal.PatientInfo;

public class PRRepository {


	private static final String CREATE_QUERY = "create table pr_registration(name character varying(40) NOT NULL, password character varying(40) NOT NULL, email character varying(40) NOT NULL)";
	private static final String INSERT_QUERY = "INSERT INTO pr_registration (name, password, email) values(?,?,?)";
	
	
    private Connection con = null;
	
	private void getConnection(){
		try {		
			Class.forName("org.postgresql.Driver");
			con  = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/b6", "postgres", "abcd12345");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't able to connect 1");
		} catch (SQLException e) {
		    e.printStackTrace();
		    System.out.println("Couldn't able to connect 2");
		}	
	}
	
	public boolean createRegistration(){
		boolean result = false;
		boolean status = false;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT EXISTS (SELECT 1 FROM pg_tables where schemaname='public' AND tablename='pr_registration')");
			while(rs.next()){
				status= rs.getBoolean(1);
			}
			if(status){
				System.out.println("Table already created...skiping it");
			}else{
				ps = con.prepareStatement(CREATE_QUERY);
				result = ps.execute();
				System.out.println("successfully created");
			}
			
		} catch (SQLException e) {
			System.out.println("exception in creation");
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				st.close();
				if(!status){
					ps.close();
				}
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
		
	}
	
	public int insertCustInfo(PatientInfo info){
		int result = 0;
		PreparedStatement ps = null;
		createRegistration();
		getConnection();

		try {
			ps = con.prepareStatement(INSERT_QUERY);
			ps.setString(1, info.getFullname());
			ps.setString(2, info.getPassword());
			ps.setString(3, info.getEmail());
			//ps.setString(4, info.getClass());
			
			result = ps.executeUpdate();
			
			System.out.println("inserted successfully");
		} catch (SQLException e) {
			System.out.println("exception in insertion");
			e.printStackTrace();
		}finally{
		
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
	
	
}

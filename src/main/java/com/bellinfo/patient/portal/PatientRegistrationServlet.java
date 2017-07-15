package com.bellinfo.patient.portal;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.bellinfo.patient.portal.modal.PatientInfo;
import com.bellinfo.patient.portal.repository.PRRepository;

public class PatientRegistrationServlet extends HttpServlet{
	String rContact = null;
	String hosName = null;
	private static  final String SUCCESS ="Hey, Successfully Registred at ";
	private static final String FAILED ="Something went wrong, please contact at ";
	public void init(ServletConfig sc){
		rContact =sc.getInitParameter("Reg-Contact");
		ServletContext serContext = sc.getServletContext();
		hosName = serContext.getInitParameter("hospital-name");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		String message = null;
		//Extracting Patinet Inputs
	     String name = req.getParameter("FullName");
	     String email = req.getParameter("Email");
	     String password = req.getParameter("Password");
	   // Populating PatientInfo  
	     PatientInfo pi = new PatientInfo();
	     pi.setFullname(name);
	     pi.setPassword(password);
	     pi.setEmail(email);
	    //Persisting PR info 
	     PRRepository pr = new PRRepository();
	     int result = pr.insertCustInfo(pi);
	     
	   //Validating the status & return
	     if(result!=0){
	    	 message  = SUCCESS + hosName;
	     }else{
	    	 message = FAILED + rContact;
	     }
	     
	     //Seeting in to response and flush it
	     req.setAttribute("status", message);
	     RequestDispatcher rd = req.getRequestDispatcher("register-success.jsp");
	     rd.forward(req, resp);
		
	     
		
	}
	

}

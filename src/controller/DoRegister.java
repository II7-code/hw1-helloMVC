package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Customer;
import service.CustomerService;

/**
 * Servlet implementation class DoRegister
 */
@WebServlet("/doRegister")
public class DoRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		String name= request.getParameter("name");
		String email = request.getParameter("email");
		
		CustomerService service = (CustomerService) CustomerService.getInstance();
		
		//계정 등록 부분
		Customer customer = service.findCustomer(id); //먼저 같은 계정 있는지 검색
		if(customer == null) {
			customer = new Customer(id,password,name,gender,email);
			service.addCustomer(customer);
		}	
		else {
			System.out.println("중복된 계정입니다.");
			customer =null;
		}
		
		//page에 넘기는 부분
		String page;
		
		if(customer == null) {
			page ="/view/registerFail.jsp";
			request.setAttribute("id", id);
		}
		else {
			page ="/view/registerSuccess.jsp";
			request.setAttribute("customer", customer);
		}
			
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

}

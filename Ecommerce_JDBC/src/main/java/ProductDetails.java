import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.DBConnection;

/**
 * Servlet implementation class ProductDetails
 */
@WebServlet("/ProductDetails")
public class ProductDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
				
				//read from html 
				String productid = request.getParameter("pid");
				
				System.out.println("ProductId :-" + productid);
			
				PrintWriter out = response.getWriter();
				out.println("<html><body>");
				
				InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
				Properties props = new Properties();
				
				props.load(in);
				
				DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"), props.getProperty("password"));
				
				String sql = "select * from eproduct where ID =?";
				
				
				int id = Integer.parseInt(productid);
				
				PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
				
				stmt.setInt(1, id);
								
				ResultSet rs = stmt.executeQuery();
				
				
				while(rs.next()) {
					out.println("<table>" + "<tr>" + "<th>ID</th>" +"<th>Name</th>" +"<th>Price</th>" + "<th>Date</th>" +"</tr>");
					
					out.println("<tr>"+ "<td>"+ rs.getInt("ID")+"</td>"+ "<td>"+ rs.getString("name")+"</td>"+"<td>"+ rs.getDouble("price") +"</td>"+ "<td>" 
					+ rs.getDate("date_added")+"</td>" + "</tr>");
				}
				
				stmt.close();
				
				out.println("</body></html>");
				conn.closeConnection();
			
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class ListMovies
 */
@WebServlet("/ListMovies")
public class ListMovies extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String dns = "ec2-18-188-80-27.us-east-2.compute.amazonaws.com";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListMovies() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        String sql;
        Connection connection = null;
        Statement statement = null;
        PreparedStatement statement1 = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        String keyword = request.getParameter("keyword");
        response.setContentType("text/html");
        
		PrintWriter out = response.getWriter();
        String name = "Movie List CSCI 4830";
        String docType =
            "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType +
            "<html>\n" +
            "<head><title>" + name + "</title></head>\n" +
            "<body bgcolor = \"##CCCCFF\">\n" +
            "<h1 align = \"center\">" + name + "</h1>\n");
        out.println("<form action=\"SearchMovies\" method=\"post\">" +
            "<p align=\"center\">Search: <input type=\"test\" name=\"search\"/></p>" +
        	"<p align=\"center\"><input type=\"submit\" value=\"Search\"/></p></form>");
        

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dns + ":3306/myDB?useSSL=false", "wphillipson", "Cl@ssWork");
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            System.out.println("Connection Failed!:\n" + e2.getMessage());
        }

        sql = "SELECT * FROM Movies";
        try {

            statement1 = connection.prepareStatement(sql);
     
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        try {

            rs = statement1.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
        	System.out.println("Listing all movies...");
            while (rs.next()) {
                //Retrieve by column name
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String genre = rs.getString("Genre");
                String rDate = rs.getString("ReleaseDate");
                
                out.println("<h2 align=\"center\">" + title + "</h2>");
                out.println("<p align=\"center\">" + desc + "<br>" + 
                			genre + "<br>" + 
                			rDate + "</p>");
            	System.out.println("   Listed " + title);
                
            }
            out.println("</body></html>");
            System.out.println("Listed all movies.");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
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

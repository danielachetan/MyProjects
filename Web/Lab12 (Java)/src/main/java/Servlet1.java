
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/Servlet1")
@MultipartConfig(fileSizeThreshold = 6291456, maxFileSize = 10485760, maxRequestSize = 20971520)
public class Servlet1 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + "uploads";
        File uploadFolder = new File(uploadFilePath);
        if (!uploadFolder.exists())
            uploadFolder.mkdirs();
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Lab12</title>");
        out.println("</head>");
        out.println("<body>");
        for (Part part : request.getParts())
            if (part != null && part.getSize() > 0) {
                String fileName = part.getSubmittedFileName();
                part.write(uploadFilePath + File.separator + fileName);
                out.println("<p>File successfully uploaded to " + uploadFolder.getAbsolutePath() + File.separator + fileName + "</p><br>");
            }
        out.println("</body>");
        out.println("</html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Lab12</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<form action='upload' method='post' enctype='multipart/form-data'>");
        out.println("<label>Select a file: </label>");
        out.println("<input type='file' name='myfile' id='myfile'>");
        out.println("<br>");
        out.println("<input type='submit' value='Upload'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
}

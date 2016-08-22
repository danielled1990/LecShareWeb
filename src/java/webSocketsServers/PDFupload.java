/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSocketsServers;

/**
 *
 * @author Danielle
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Collection;
import java.util.Scanner;
import javaclass.PDFtoImage;
import javaclass.webSusers;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Danielle
 */
@WebServlet(name = "PDFupload", urlPatterns = {"/PDFupload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class PDFupload extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Create path components to save the file
        final String path = request.getParameter("destination");
        final Part filePart = request.getPart("file");
//    final String fileName = getFileName(filePart);
        final PrintWriter writer = response.getWriter();
        Collection<Part> parts = request.getParts();
        String fileName = "";
        OutputStream out = null;
        InputStream filecontent = null;
        URL pdfFile = this.getServletContext().getResource("WEB-INF\\pdf");
        String pdfFileStr = pdfFile.toString();
        //pdfFileStr=pdfFileStr.replaceAll("file.pdf", "");
        pdfFileStr = pdfFileStr.replace("file:", "");
        for (Part part : parts) {
            for (String header : part.getHeaderNames()) {
                for (String part1 : part.getHeaders(header)) {
                    String[] arrStrings = part1.split("; ");
                    for (String arrString : arrStrings) {
                        if (arrString.startsWith("filename=")) {
                            fileName = arrString.substring(9);
                            fileName = fileName.replaceAll("\"", "");
                            break;
                        }
                    }
                }
            }
        }
        pdfFileStr += fileName;
        File f = new File(pdfFileStr);

        String k = f.getPath();

        StringBuilder fileContent = new StringBuilder();
        //
        for (Part part : parts) {
            fileContent.append(readFromInputStream(part.getInputStream()));

           // printPart(part, out);
            //to write the content of the file to an actual file in the system
            part.write(k);

            //to write the content of the file to a string
            //   fileContent.append(readFromInputStream(part.getInputStream()));
        }

//        PDFtoImage.getInstance().setPage();  
        HttpSession ses = request.getSession();
        String name = (String) ses.getAttribute("username");
        webSusers.getInstance().geBrowserSessionOfUser(name).setURL(f.getPath());//individial path for each user
        //PDFtoImage.getInstance().setURL(f.getPath());
        //     response.sendRedirect("index.html");
        // try {
        //    out = new FileOutputStream(new File(path + File.separator
        //            + fileName));
        //     filecontent = filePart.getInputStream();

  //      int read = 0;
        //      final byte[] bytes = new byte[1024];
 //       while ((read = filecontent.read(bytes)) != -1) {
        //           out.write(bytes, 0, read);
        //       }
        //       writer.println("New file " + fileName + " created at " + path);
        //       LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", 
        //               new Object[]{fileName, path});
        //   } catch (FileNotFoundException fne) {
        //       writer.println("You either did not specify a file to upload or are "
        //               + "trying to upload a file to a protected or nonexistent "
        //               + "location.");
        //       writer.println("<br/> ERROR: " + fne.getMessage());
 //       LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
        //               new Object[]{fne.getMessage()});
        //   } finally {
//        if (out != null) {
//            out.close();
        //       }
        //       if (filecontent != null) {
//           filecontent.close();
//        }
        //       if (writer != null) {
//            writer.close();
//        }
//    }
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

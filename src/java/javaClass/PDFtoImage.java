/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclass;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.websocket.Session;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author Danielle
 */
public class PDFtoImage {
    //private int page =0;
   // private String URL;
    private File file;
    private static PDFtoImage instance = null;
    private Session session;
      public  BufferedImage createImagesFromPDF(String URL,int page) throws IOException,IllegalAccessException,InvocationTargetException{
          file = new File(URL);
       // String pdfFilename = "C:\\Users\\Danielle\\Documents\\NetBeansProjects\\imagewebsockettry\\build\\web\\WEB-INF\\pdf\\׳�׳¨׳×׳•׳�.pdf";
        try (PDDocument document = PDDocument.load(file)) {
       //   PDDocument document = PDDocument.load(new File(URL));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
         //   for (int page = 0; page < document.getNumberOfPages(); ++page)
          //  {
                
                BufferedImage  buf  = pdfRenderer.renderImageWithDPI(page, 100,ImageType.RGB);
                
                // suffix in filename will be used as the file format
        //        ImageIOUtil.writeImage(buf,pdfFilename + "-" + (page+1) + ".png", 300);  
        //    }
            document.close();
           // file.delete();
            return buf;
        }
     }
      protected PDFtoImage(){}
   
      public static PDFtoImage getInstance() {
      if(instance == null) {
         instance = new PDFtoImage();
      }
      return instance;
     }
   /* public void incPage(){
        this.page++;
    }
    public void setURL(String URL){
        this.URL = URL;
    }
    public void setPage(){
        this.page=0;
    }
    public Session getSession(){
        return this.session;
    }
    public void setSession(Session session){
        this.session = session;
        
    }*/
    
}



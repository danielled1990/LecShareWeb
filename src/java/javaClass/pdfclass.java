/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclass;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;

/**
 *
 * @author Danielle
 */
 
public class pdfclass {
  private PDFDocument document = new PDFDocument();
  private List<Image> images;
  public  void createImagesFromPDF() throws IOException, RendererException, DocumentException
  {
     document.load(new File("Web Pages/pdf/slides2c_RelModel-2.pdf"));//לשנות לשם של המצגת
     SimpleRenderer renderer = new SimpleRenderer();

     renderer.setResolution(300);
     images= renderer.render(document);
  }
  public void pdfclass(){}
  
  public List<Image> getimagelist ()
  {
      return images;
  }

}

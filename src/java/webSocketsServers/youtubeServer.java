/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSocketsServers;

import java.nio.ByteBuffer;
 
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
 
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.ghost4j.renderer.RendererException;
import javaclass.PDFtoImage;
import javaclass.webSusers;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

 
 
 
/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo
 * Where "localhost" is the address of the host,
 * "EchoChamber" is the name of the package
 * and "echo" is the address to access this class from the server
 */
@ServerEndpoint(value = "/youtube", configurator = ServerConfiguration.class)
public class youtubeServer {

    private Session wsSession;
    private HttpSession httpSession;
    private Session mSession;
    private final static HashMap<String, Session> sockets = new HashMap<>();

  //  private Image images;
    //   private pdfclass pdfimg = new pdfclass();
    private int page = 0;
    private ArrayList<BufferedImage> bim = new ArrayList<>();
    private boolean flag = false;

    //  private final PDFDocument document = new PDFDocument();

    /**
     * @OnOpen allows us to intercept the creation of a new session. The session
     * class allows us to send data to the user. In the methyd onOpen, we'll let
     * the user know that the handshake was successful.
     */
    @OnOpen
    public void onOpen(EndpointConfig endpoingConfig, Session session) {

        //session.getUserProperties().put("username",endpoingConfig.getUserProperties().get("username"));
        System.out.println(session.getId() + " has opened a connection");
        try {
            //String videoid = (String)session.getUserProperties().get("videoId");
            //if(videoid!=null){
            //    session.getBasicRemote().sendText("videoid");
            // }
            this.wsSession = session;
            this.httpSession = (HttpSession) endpoingConfig.getUserProperties()
                    .get(HttpSession.class.getName());

            if (httpSession != null) {
                String videoId = webSusers.getInstance().geBrowserSessionOfUser((String) httpSession.getAttribute("username")).getVideoId();
                session.getBasicRemote().sendText(videoId);
            }
    //        PDFDocument document = new PDFDocument();
            //  session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

    }

    /**
     * When a user sends a message to the server, this method will intercept the
     * message and allow us to react to it. For now the message is read as a
     * String.
     *
     * @param message
     * @throws org.ghost4j.renderer.RendererException
     * @throws org.ghost4j.document.DocumentException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws RendererException, InvocationTargetException, IllegalAccessException {

    }

    @OnMessage
    public void processUpload(byte[] bytes, boolean last, Session session) throws RendererException, InvocationTargetException, IllegalAccessException, IOException {

        ByteBuffer byteBuffer;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length)) {
            byteBuffer = ByteBuffer.wrap(baos.toByteArray());
            mSession.getAsyncRemote().sendBinary(byteBuffer);
        }
        byteBuffer.clear();

    }

    private BufferedImage getImgae() throws IOException, IllegalAccessException, InvocationTargetException {
        String pdfFilename = "C:\\Users\\Danielle\\Documents\\NetBeansProjects\\imagewebsockettry\\build\\web\\pdf\\slides2c_RelModel-2.pdf";
        try (PDDocument document = PDDocument.load(new File(pdfFilename))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
         //   for (int page = 0; page < document.getNumberOfPages(); ++page)
            //  {

            BufferedImage buf = pdfRenderer.renderImageWithDPI(page, 50, ImageType.RGB);

                // suffix in filename will be used as the file format
            //   ImageIOUtil.writeImage(buf,pdfFilename + "-" + (page+1) + ".png", 300);  
            //    }
            document.close();
            page++;
            return buf;
        }
    }

    /**
     * The user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended");
    }
}


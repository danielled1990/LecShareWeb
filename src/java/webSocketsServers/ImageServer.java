package webSocketsServers;

import com.google.gson.Gson;
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
import javaclass.SessionUtil;
import javaclass.Streamer;
import javaclass.webSusers;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;

/**
 * @ServerEndpoint gives the relative name for the end point This will be
 * accessed via ws://localhost:8080/EchoChamber/echo Where "localhost" is the
 * address of the host, "EchoChamber" is the name of the package and "echo" is
 * the address to access this class from the server
 */
@ServerEndpoint(value = "/image", configurator = ServerConfiguration.class)
public class ImageServer {

    private Session wsSession;
    private HttpSession httpSession;
    private Session mSession;
    private final static HashMap<String, ArrayList<SessionUtil>> sockets = new HashMap<>();

  //  private Image images;
    //   private pdfclass pdfimg = new pdfclass();
   // private int page = 0;
    private ArrayList<BufferedImage> bim = new ArrayList<>();
    private boolean start = true;

    //  private final PDFDocument document = new PDFDocument();

    /**
     * @OnOpen allows us to intercept the creation of a new session. The session
     * class allows us to send data to the user. In the methyd onOpen, we'll let
     * the user know that the handshake was successful.
     */
    @OnOpen
    public void onOpen(EndpointConfig endpoingConfig, Session session) {
        System.out.println(session.getId() + " has opened a connection");
        try {

               // PDFtoImage.getInstance().setSession(session);
            this.wsSession = session;
            this.httpSession = (HttpSession) endpoingConfig.getUserProperties()
                    .get(HttpSession.class.getName());
            if (httpSession != null)//add browser users for mobile guy
            {
                String name = (String) httpSession.getAttribute("username");
                SessionUtil util = webSusers.getInstance().geBrowserSessionOfUser(name);
                wsSession.getUserProperties().put("username", httpSession.getAttribute("username"));
                webSusers.getInstance().geBrowserSessionOfUser(name).setWebSocketImageSession(session);
                ArrayList<SessionUtil> sessionutil = sockets.get(util.getCconnectToBroadCastOf());
                if (sessionutil == null) {
                    sessionutil = new ArrayList<>();
                    sockets.put(util.getCconnectToBroadCastOf(), sessionutil);
                }
                sessionutil.add(util);
            }
            //        PDFDocument document = new PDFDocument();
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
    public void onMessage(String message, Session session) throws RendererException, InvocationTargetException, IllegalAccessException, IOException {
        System.out.println("Message: " + message);
        Gson gson = new Gson();
        if (httpSession == null && start == true) {
            //  Streamer streamer = gson.fromJson(message, Streamer.class);

            start = false;
        }
        if (message.contains("streamer")) { //mobile guy
            //  try{
            Streamer streamer = gson.fromJson(message, Streamer.class);
            ArrayList<SessionUtil> sessionutil = sockets.get(streamer.GetName());
            if (sessionutil == null) {
                sessionutil = new ArrayList<>();
                sockets.put(streamer.GetName(), sessionutil);
            }
            for (int i = 0; i < sessionutil.size(); i++) {
                // String name = (String)session.getUserProperties().get("username");
                String URL = sessionutil.get(i).getURL();
                if (URL != null) {
                    int page = streamer.getPage();
                    BufferedImage b3 = PDFtoImage.getInstance().createImagesFromPDF(URL, page);
                  //  page +=1;
                    sessionutil.get(i).setPage(page);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ImageIO.write(b3, "png", out);
                    ByteBuffer byteBuffer = ByteBuffer.wrap(out.toByteArray());
                    sessionutil.get(i).getWebSocketImageSession().getAsyncRemote().sendBinary(byteBuffer);
                    out.close();
                    byteBuffer.clear();

                }

            }

        } else {
            try {
                String name = (String) session.getUserProperties().get("username");
                String URL = webSusers.getInstance().geBrowserSessionOfUser(name).getURL();
                int page = webSusers.getInstance().geBrowserSessionOfUser(name).getPage();
                BufferedImage b3 = PDFtoImage.getInstance().createImagesFromPDF(URL, page);
                page +=1;
                webSusers.getInstance().geBrowserSessionOfUser(name).setPage(page);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(b3, "png", out);
                ByteBuffer byteBuffer = ByteBuffer.wrap(out.toByteArray());
                webSusers.getInstance().geBrowserSessionOfUser(name).getWebSocketImageSession().getAsyncRemote().sendBinary(byteBuffer);
                out.close();
                byteBuffer.clear();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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

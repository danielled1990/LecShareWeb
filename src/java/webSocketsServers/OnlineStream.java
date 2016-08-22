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
import javaclass.BroadCastUsers;

import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.ghost4j.renderer.RendererException;
import javaclass.PDFtoImage;

import javaclass.StreamList;
import javaclass.Streamer;
import javaclass.wantToConnect;
import javaclass.webSusers;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;

/**
 * @ServerEndpoint gives the relative name for the end point This will be
 * accessed via ws://localhost:8080/EchoChamber/echo Where "localhost" is the
 * address of the host, "EchoChamber" is the name of the package and "echo" is
 * the address to access this class from the server
 */
@ServerEndpoint(value = "/online", configurator = ServerConfiguration.class)
public class OnlineStream {

    private Session wsSession;
    private HttpSession httpSession;
    private Session mSession;
    private final static ArrayList<Session> onlineStreamingUsers = new ArrayList<>();

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
        System.out.println(session.getId() + " has opened a connection");
        try {
           // HttpSession
            //  httpSession =((PrincipalWithSession) session.getUserPrincipal()).getSession();
            this.wsSession = session;
            this.httpSession = (HttpSession) endpoingConfig.getUserProperties()
                    .get(HttpSession.class.getName());
            if (httpSession != null) {
                wsSession.getUserProperties().put("username", httpSession.getAttribute("username"));
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
    public void onMessage(String message, Session session) throws RendererException, InvocationTargetException, IllegalAccessException {
        System.out.println("Message: " + message);

        if (message.contains("refreshing")) {
            StreamList streamList = new StreamList();

            streamList.SetType("streamers");
            for (int i = 0; i < onlineStreamingUsers.size(); i++) {
                String name = (String) onlineStreamingUsers.get(i).getUserProperties().get("username");
                streamList.AddToArrayList(name);
            }
            Gson gson = new Gson();
            String res = gson.toJson(streamList);
            session.getAsyncRemote().sendText(res);

        }
        if (message.contains("streamer")) {
            Gson gson = new Gson();
            Streamer stream = gson.fromJson(message, Streamer.class);
            webSusers.getInstance().insertNewUser(stream.GetName(), session);
            session.getUserProperties().put("username", stream.GetName());
            onlineStreamingUsers.add(session);
            session.getAsyncRemote().sendText(stream.GetName() + "was added to stream list");
        }
        if (message.contains("connect")) {
            Gson gson = new Gson();
            wantToConnect connect = gson.fromJson(message, wantToConnect.class);
            String name = (String) session.getUserProperties().get("username");
            connect.setConnectTo(name);
            webSusers.getInstance().insertNewUser(connect.GetName(), session);
            Session res = webSusers.getInstance().getSessionOfUser(connect.GetConnectTo());
            Streamer wantToConnectToStream = new Streamer();
            String ans = gson.toJson(connect);
            res.getAsyncRemote().sendText(ans);
        }
        if (message.contains("yes")) {
            Gson gson = new Gson();
            BroadCastUsers broadCastUsers = gson.fromJson(message, BroadCastUsers.class);
            Session res = webSusers.getInstance().getSessionOfUser(broadCastUsers.getNameToConnectTo());
            res.getUserProperties().put("videoId", broadCastUsers.getVideoId());//res=null משומה
            //  httpSession.setAttribute("videoId", broadCastUsers.getVideoId());
            // broadCastUsers.setSession(res);
            webSusers.getInstance().addSession(broadCastUsers.getNameToConnectTo());
            webSusers.getInstance().geBrowserSessionOfUser(broadCastUsers.getNameToConnectTo()).setVideoId(broadCastUsers.getVideoId());
            webSusers.getInstance().setStreamUser(broadCastUsers.getName());
            webSusers.getInstance().geBrowserSessionOfUser(broadCastUsers.getNameToConnectTo()).setCconnectToBroadCastOf(broadCastUsers.getName());
            webSusers.getInstance().addSessionToStreamImageUser(broadCastUsers.getName(), broadCastUsers);
            res.getAsyncRemote().sendText(message);
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


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
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 *
 * @author Danielle
 */
public class ServerConfiguration extends ServerEndpointConfig.Configurator {

    private HttpSession session;

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        session = (HttpSession) request.getHttpSession();
        if (session != null) {
            sec.getUserProperties().put(HttpSession.class.getName(), session);
        }

    }

    public HttpSession getSession() {
        return session;
    }

}


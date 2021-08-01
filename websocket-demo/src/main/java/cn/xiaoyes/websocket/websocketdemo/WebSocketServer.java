package cn.xiaoyes.websocket.websocketdemo;


import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/socket")
@Component
public class WebSocketServer {

    private static AtomicInteger onlineCount = new AtomicInteger(0);

    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session){
        onlineCount.incrementAndGet();// 在线人数+1
        clients.put(session.getId(), session);
        System.out.println("有新连接加入:" + session.getId() + " 当前在线人数为: " + onlineCount.get());
    }

    @OnClose
    public void onClose(Session session){
        onlineCount.decrementAndGet();// 在线人数-1
        clients.remove(session.getId());
        System.out.println("一个连接关闭: " + session.getId() + " 当前在线人数为: " + onlineCount.get());
    }

    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println("服务端收到客户端" + session.getId() + "的信息: " + message);
        this.sendAll(message);
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        throwable.printStackTrace();
        System.out.println("发生错误");
    }

    /**
     * 群发消息
     * @param message 消息内容
     */
    private void sendAll(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            sessionEntry.getValue().getAsyncRemote().sendText(message);
        }
    }
}

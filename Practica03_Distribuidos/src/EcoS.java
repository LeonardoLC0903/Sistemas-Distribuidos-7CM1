import java.nio.channels.*;
import java.nio.*;
import java.net.*;
import java.util.*;

public class EcoS {
    private static final int PORT = 9999;
    private static final int PROCESSING_TIME = 5000; // 5 segundos
    private static final Queue<SocketChannel> waitingQueue = new LinkedList<>();
    private static boolean isProcessing = false;
    
    public static void main(String[] args) {
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            serverChannel.socket().bind(new InetSocketAddress(PORT));
            Selector selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Servidor iniciado en el puerto " + PORT);
            
            while (true) {
                selector.select();
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    
                    if (key.isAcceptable()) {
                        SocketChannel clientChannel = serverChannel.accept();
                        if (clientChannel != null) {
                            clientChannel.configureBlocking(false);
                            clientChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println("Cliente conectado desde: " + clientChannel.getRemoteAddress());
                        }
                    }
                    
                    if (key.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(2000);
                        buffer.clear();
                        int bytesRead = clientChannel.read(buffer);
                        
                        if (bytesRead == -1) {
                            clientChannel.close();
                            continue;
                        }
                        
                        buffer.flip();
                        String message = new String(buffer.array(), 0, bytesRead);
                        System.out.println("Mensaje recibido: " + message);
                        
                        if (message.equalsIgnoreCase("SALIR")) {
                            System.out.println("Cliente se ha desconectado.");
                            clientChannel.close();
                        } else {
                            waitingQueue.add(clientChannel);
                            processNextClient();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void processNextClient() {
        if (!isProcessing && !waitingQueue.isEmpty()) {
            isProcessing = true;
            SocketChannel clientChannel = waitingQueue.poll();
            new Thread(() -> {
                try {
                    Thread.sleep(PROCESSING_TIME);
                    String response = "ECO_Procesado";
                    ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                    clientChannel.write(buffer);
                    System.out.println("Respuesta enviada a: " + clientChannel.getRemoteAddress());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    isProcessing = false;
                    processNextClient();
                }
            }).start();
        }
    }
}

package localdomain.localhost.domain;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: bubbu
 * Date: 20/12/13
 * Time: 9:58
 * To change this template use File | Settings | File Templates.
 */

@WebServlet(value = "/rabbitmq/client")
public class RabitMQClient extends HttpServlet {

    private final static String QUEUE_NAME = "felix3";

    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("felix066");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [***] Waiting for messages. To exit press CTRL+C");

        //channel.basicQos(1);
        
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //autoACK
        channel.basicConsume(QUEUE_NAME, false, consumer);

        //while (true) {
        	//for (int i=0; i<5; i++){
 
            try {
            	System.out.println(" [***] Waiting 1 ***************");
            	 QueueingConsumer.Delivery delivery = consumer.nextDelivery(100);
            	 if(delivery!=null)
            	 {	 
            	 
	            	 System.out.println(" [***] Waiting 2****************************************");
	            	 String message = new String(delivery.getBody());
	               
	            	 System.out.println(" [x] Received '" + message + "'");   
	            	 
	                 System.out.println(" [x] Done new" );
	
	                 channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	            	 
	                 request.setAttribute("message", message);
	            	 request.getRequestDispatcher("/index.jsp").forward(request, response);
	                 
	            	  channel.close();
	                  connection.close();
	                  //request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	                  //request.getRequestDispatcher("/index.jsp").forward(request, response);
	                  //response.sendRedirect(request.getContextPath() +"/indexServlet");
	              	//}
            	 }else{
            		 String message = "NO NEW MESSAGE ON THE QUEUE";
            		 request.setAttribute("message", message);
	            	 request.getRequestDispatcher("/index.jsp").forward(request, response);
	                 
	            	  channel.close();
	                  connection.close();
            		 
            	 }
              	
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
          
        //}

    }
    
    
}

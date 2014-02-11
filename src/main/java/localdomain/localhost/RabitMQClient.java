/*
 * Copyright 2010-2014, the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package localdomain.localhost;

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

@WebServlet(value = "/rabbitmq/client")
public class RabitMQClient extends HttpServlet {

    private final static String QUEUE_NAME = "myqueue";

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
        
        QueueingConsumer consumer = new QueueingConsumer(channel);
        boolean autoACK = false;
        channel.basicConsume(QUEUE_NAME, false, consumer);
 
        try {
        	System.out.println(" [*] Waiting 100ms for a message");
        	 QueueingConsumer.Delivery delivery = consumer.nextDelivery(100);
        	 if(delivery!=null)
        	 {	        	 
            	 System.out.println(" [*] Waiting");
            	 String message = new String(delivery.getBody());
               
            	 System.out.println(" [x] Received '" + message + "'");   
           
                 channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            	 
                 request.setAttribute("message", message);
            	 request.getRequestDispatcher("/index.jsp").forward(request, response);
                 
				 channel.close();
				 connection.close();
        	 }else{
        		 String message = "NO NEW MESSAGES ON THE QUEUE";
        		 request.setAttribute("message", message);
            	 request.getRequestDispatcher("/index.jsp").forward(request, response);
				 
				 channel.close();
				 connection.close();    		 
        	 }
          	
        } catch (Exception e) {
            e.printStackTrace();  
        }
         
    } 
    
}

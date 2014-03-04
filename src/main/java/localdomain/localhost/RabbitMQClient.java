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
public class RabbitMQClient extends HttpServlet {

	private final static String QUEUE_NAME = "myqueue";

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ConnectionFactory factory = new ConnectionFactory();
			String messages = new String();
			
			String uri = System.getProperty("CLOUDAMQP_URL");
			factory.setUri(uri);
			
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, true, false, false, null);

			QueueingConsumer consumer = new QueueingConsumer(channel);

			boolean autoACK = false;
			channel.basicConsume(QUEUE_NAME, autoACK, consumer);

			System.out.println(" [*] Waiting 100ms for a message");
			QueueingConsumer.Delivery delivery = consumer.nextDelivery(100);
					
			while(delivery != null) {
				String message = new String(delivery.getBody());
		
				System.out.println(" [x] Received '" + message + "'");
		
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				
				messages = message + " <br/> " + messages;	
				delivery = consumer.nextDelivery(100);
			} 
				request.setAttribute("messages", messages);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
	
				channel.close();
				connection.close();

		} catch (Exception e) {
			request.setAttribute("throwable", e);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
	}

}

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
import com.rabbitmq.client.MessageProperties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@WebServlet(value = "/rabbitmq/server")
public class RabbitMQServer extends HttpServlet {

	private final static String QUEUE_NAME = "myqueue";

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			String message = request.getParameter("message");
			
			ConnectionFactory factory = new ConnectionFactory();
			String uri = System.getProperty("CLOUDAMQP_URL");
			factory.setUri(uri);		
			
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			boolean durable = true;
			channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");

			channel.close();
			connection.close();

			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} catch (Exception e) {
			request.setAttribute("throwable", e.toString());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

}

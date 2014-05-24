// Copyright (c) 2014 Richard Long & HexBeerium
//
// Released under the MIT license ( http://opensource.org/licenses/MIT )
//

package jsonbroker.java.samples.websocket;

import jsonbroker.library.client.http.web_socket.TextWebSocketBuilder;
import jsonbroker.library.common.http.web_socket.TextWebSocket;
import jsonbroker.library.common.log.Log;

public class Client {
	
	
	private static Log log = Log.getLog(Client.class);
	
	static boolean USE_WEBSOCKET_ORG = false;
	

	public static void main( String args[] ) {
		
		log.enteredMethod();
		

		String host = "127.0.0.1";
		int port = 8080;
		String path = "/_dynamic_/open/echo";
		

		if( USE_WEBSOCKET_ORG ) {
			
			host = "echo.websocket.org";
			port = 80;
			path = "/?encoding=text";
		}
		
		TextWebSocket webSocketConnection = TextWebSocketBuilder.build( host, port, path );
		
		
		webSocketConnection.sendTextFrame( "hello web-sockets" );
		String response = webSocketConnection.recieveTextFrame();
		log.debug( response, "response" );
		
		webSocketConnection.sendCloseFrame();

		log.info( "done" );
	}

}

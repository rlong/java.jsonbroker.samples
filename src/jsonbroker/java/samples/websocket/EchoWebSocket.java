// Copyright (c) 2014 Richard Long & HexBeerium
//
// Released under the MIT license ( http://opensource.org/licenses/MIT )
//

package jsonbroker.java.samples.websocket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import jsonbroker.library.common.http.web_socket.TextWebSocket;
import jsonbroker.library.common.log.Log;
import jsonbroker.library.server.http.ConnectionHandler;
import jsonbroker.library.server.http.ConnectionHandler.Delegate;

public class EchoWebSocket implements ConnectionHandler.Delegate {
	
	private static Log log = Log.getLog(EchoWebSocket.class);

	TextWebSocket _echoWebSocket;
	Socket _socket;

	@Override
	public Delegate processRequest(Socket socket, InputStream inputStream,
			OutputStream outputStream) {
		
		if( _socket != socket ) {
			_echoWebSocket = new TextWebSocket( socket ); 
		}
		
		String line = _echoWebSocket.recieveTextFrame();
		
		log.debug( line, "line" );
		
		if( null == line ) {
			return null;
		}
		
		_echoWebSocket.sendTextFrame( line );
		
		return this;
	}

}

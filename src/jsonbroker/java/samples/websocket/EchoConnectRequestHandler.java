// Copyright (c) 2014 Richard Long & HexBeerium
//
// Released under the MIT license ( http://opensource.org/licenses/MIT )
//

package jsonbroker.java.samples.websocket;

import jsonbroker.library.common.http.HttpStatus;
import jsonbroker.library.common.http.web_socket.WebSocketUtilities;
import jsonbroker.library.common.log.Log;
import jsonbroker.library.server.http.HttpErrorHelper;
import jsonbroker.library.server.http.HttpRequest;
import jsonbroker.library.server.http.HttpResponse;
import jsonbroker.library.server.http.RequestHandler;

public class EchoConnectRequestHandler implements RequestHandler {

	private static Log log = Log.getLog(EchoConnectRequestHandler.class);

	@Override
	public String getProcessorUri() {
		return "/echo";
	}


	@Override
	public HttpResponse processRequest(HttpRequest request) {
		
		
		String upgrade = request.getHttpHeader( "upgrade" );
		log.debug( upgrade );
		if( null == upgrade ) { 
			log.error( "null == upgrade" );
			throw HttpErrorHelper.badRequest400FromOriginator( this );
		}
		
		String secWebSocketKey = request.getHttpHeader( "sec-websocket-key" );
		log.debug( secWebSocketKey );
		if( null == secWebSocketKey ) { 
			log.error( "null == secWebSocketKey" );
			throw HttpErrorHelper.badRequest400FromOriginator( this );
		}
		
		HttpResponse answer = new HttpResponse( HttpStatus.SWITCHING_PROTOCOLS_101 );
		String secWebSocketAccept = WebSocketUtilities.buildSecWebSocketAccept( secWebSocketKey );
		
		answer.putHeader( "Connection" , "Upgrade" );
		answer.putHeader( "Sec-WebSocket-Accept" , secWebSocketAccept );
		answer.putHeader( "Upgrade" , "websocket");
		
		answer.setConnectionDelegate( new EchoWebSocket() ); 
		
		return answer;
	}

}

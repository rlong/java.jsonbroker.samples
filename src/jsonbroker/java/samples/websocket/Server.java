// Copyright (c) 2014 Richard Long & HexBeerium
//
// Released under the MIT license ( http://opensource.org/licenses/MIT )
//

package jsonbroker.java.samples.websocket;

import jsonbroker.library.common.log.Log;
import jsonbroker.library.common.work.WorkManager;
import jsonbroker.library.server.http.RequestHandler;
import jsonbroker.library.server.http.WebServer;
import jsonbroker.library.server.http.reqest_handler.FileRequestHandler;
import jsonbroker.library.server.http.reqest_handler.OpenRequestHandler;
import jsonbroker.library.server.http.reqest_handler.RootRequestHandler;

public class Server {

	private static Log log = Log.getLog(Server.class);
	
	private static final int HOST_PORT = 8080;

	
	private static RequestHandler buildRootRequestHandler() { 
		
		RootRequestHandler answer;
		{
			String rootFolder = "assets";
			FileRequestHandler fileProcessor = new FileRequestHandler(rootFolder);
			
			answer = new RootRequestHandler( fileProcessor );
		}

		{
			log.info("setting up '/_dynamic_/open/'");
			OpenRequestHandler openProcessor;
			{
				// '/_dynamic_/open'
				openProcessor = new OpenRequestHandler(); 

				// '/_dynamic_/open/echo'
				openProcessor.addRequestHandler( new EchoConnectRequestHandler() );

			}
			
			answer.addRequestHandler( openProcessor );
		}

		return answer;
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		log.info( "setting up work manager ... ");
		WorkManager.start();

		
		RequestHandler requestHandler = buildRootRequestHandler();

		log.info( "starting web server ... ");
		
		WebServer webserver = new WebServer( HOST_PORT, requestHandler);
		webserver.start();

		log.info( "http://localhost:"+HOST_PORT+"/index.html");
		log.info( "http://localhost:"+HOST_PORT+"/websocket/index.html");

	}
}

package jsonbroker.samples.bare_server;

import jsonbroker.library.common.exception.BaseException;
import jsonbroker.library.common.log.Log;
import jsonbroker.library.common.work.WorkManager;
import jsonbroker.library.server.broker.ServicesRegistery;
import jsonbroker.library.server.http.RequestHandler;
import jsonbroker.library.server.http.WebServer;
import jsonbroker.library.server.http.reqest_handler.FileRequestHandler;
import jsonbroker.library.server.http.reqest_handler.OpenRequestHandler;
import jsonbroker.library.server.http.reqest_handler.RootRequestHandler;
import jsonbroker.library.server.http.reqest_handler.ServicesRequestHandler;
import jsonbroker.library.service.test.TestService;

public class BareApplicationServer {
	
	
	private static final Log log = Log.getLog( BareApplicationServer.class );

	private static OpenRequestHandler buildOpenProcessor() {
		
		log.info( "setting up '/_dynamic_/open' ... ");

		ServicesRegistery servicesRegistery = new ServicesRegistery();
		servicesRegistery.addService( new TestService() );

		OpenRequestHandler answer = new OpenRequestHandler();
		
		ServicesRequestHandler servicesProcessor = new ServicesRequestHandler(servicesRegistery);
		answer.addRequestHandler( servicesProcessor ); 
		
		return answer;
		
	}
	
	
	private static RootRequestHandler buildRootProcessor( ) {
		
		RequestHandler httpProcessor;
		
		
		String rootFolder = "S:\\project.jsonbroker\\git\\jsonbroker-javascript";
		log.info( rootFolder, "rootFolder" );
		httpProcessor = new FileRequestHandler(rootFolder);

		RootRequestHandler answer = new RootRequestHandler(httpProcessor);
		answer.addRequestHandler( buildOpenProcessor() );

		
		return answer;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
        try {
        	
    		log.info("starting server" );
    		WorkManager.start();
    		
    		RootRequestHandler rootProcessor;
    		{
    			rootProcessor = buildRootProcessor();
    			OpenRequestHandler openRequestHandler = buildOpenProcessor();
        		rootProcessor.addRequestHandler( openRequestHandler );
    		}
    		log.info( "starting up web server... ");
    		WebServer webserver = new WebServer( rootProcessor );
    		webserver.start();
    		log.info( "web server is running");

        	
        }catch( BaseException be ) {
        	log.error( be );
        }
		
	}

}

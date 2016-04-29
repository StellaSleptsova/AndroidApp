var net = require('net');
var HOST ='192.168.1.223';
//var HOST ='172.20.165.3';
var PORT = 3000;

function start(db, route, handle){
	function clientSocket(sock){
		function handlerData(data){
			route(data, handle, sock, db);
		}
		console.log("CONNECTED: "+sock.remoteAddress+" : "+sock.remotePort);
		
		sock.on('data', handlerData);
		sock.on('close', function(data) {
			console.log('CLOSED: ' + sock.remoteAddress +' '+ sock.remotePort);
		});
		sock.on('error', function(data) {
			sock.destroy();
		});
		sock.setTimeout(10000);
		sock.on('timeout', function(data) {
            sock.destroy();
		});
	}
	
	var server = net.createServer(clientSocket);
	
	server.listen(PORT, HOST);
	console.log("Server listening on " + HOST + " : " + PORT);
}
 exports.start=start;

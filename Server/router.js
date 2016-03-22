function route(data, handle, sock, db){
	console.log(data.toString());
	var data = data.toString().substr(2);
	var dataJson = JSON.parse(data);
	if(typeof handle[dataJson.req]==='function'){
		handle[dataJson.req](sock, db, dataJson);
	}else{
		console.log("No request handler found for "+ data);
		sock.write("error");
		sock.destroy();
	}
}

exports.route = route;
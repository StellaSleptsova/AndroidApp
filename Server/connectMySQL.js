var mysql = require('mysql');

function connectDataBase(){
	var db=mysql.createConnection({
		host:'127.0.0.1',
		port:'3306',
		user:'root',
		password:'cexrfcexrf',
		database:'db_app',
	});
	
	db.connect();
	console.log("DataBase connected");
	return(db);
}

exports.connectDataBase=connectDataBase;
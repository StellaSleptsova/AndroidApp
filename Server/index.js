var dataBase = require("./connectMySQL");
var server = require("./server");
var router = require("./router");
var requestHandlers = require("./requestHandlers");

var handle = {};
handle["entry"] = requestHandlers.entry;
handle["listGroup"] = requestHandlers.listGroup;
handle["listStudent"] = requestHandlers.listStudent;
handle["listProblems"] = requestHandlers.listProblems;
handle["addNewGroup"] = requestHandlers.addNewGroup;
handle["deleteGroup"] = requestHandlers.deleteGroup;
handle["deleteProblem"] = requestHandlers.deleteProblem;
handle["deleteStudent"] = requestHandlers.deleteStudent;
handle["getTextProblem"] = requestHandlers.getTextProblem;
handle["addNewProblem"] = requestHandlers.addNewProblem;
handle["addNewStudent"] = requestHandlers.addNewStudent;

var db = dataBase.connectDataBase();
server.start(db, router.route, handle);
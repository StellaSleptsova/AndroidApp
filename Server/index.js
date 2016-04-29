var dataBase = require("./connectMySQL");
var server = require("./server");
var router = require("./router");
var requestHandlers = require("./requestHandlers");

var handle = {};
handle["entry"] = requestHandlers.entry;
handle["registration"] = requestHandlers.registration;
handle["listGroup"] = requestHandlers.listGroup;
handle["listTasks"] = requestHandlers.listTasks;
handle["addNewGroup"] = requestHandlers.addNewGroup;
handle["deleteGroup"] = requestHandlers.deleteGroup;
handle["deleteTask"] = requestHandlers.deleteTask;
handle["deleteStudent"] = requestHandlers.deleteStudent;
handle["getTextTask"] = requestHandlers.getTextTask;
handle["addNewTask"] = requestHandlers.addNewTask;
handle["addNewStudent"] = requestHandlers.addNewStudent;
handle["list_of_students"] = requestHandlers.list_of_students;
handle["getListStudents_forGroup"] = requestHandlers.getListStudents_forGroup;
handle["getListTasks_forGroup"] = requestHandlers.getListTasks_forGroup;

var db = dataBase.connectDataBase();
server.start(db, router.route, handle);
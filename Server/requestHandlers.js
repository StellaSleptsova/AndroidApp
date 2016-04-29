function sockWriteAndDestroy(sock, data){
	sock.write(JSON.stringify(data));
	sock.destroy();
}

function sendError(sock,error,strError){
	console.log("ErrorMySql_"+strError);
	console.log(error.toString());
	var data_from_db={
		answer: "error",
	};
	sockWriteAndDestroy(sock,data_from_db);
}

function entry(sock, db, dataJson){
	console.log("entry");
	var login=dataJson.dataUser.login;
	var password=dataJson.dataUser.password;
	var user=dataJson.dataUser.user;
	var str_req;
	if(parseInt(user)==1){
		str_req='select id_tr as id,login_teacher as login,password_teacher as password,f_name_teacher as fname,s_name_teacher as sname,institution FROM Teachers WHERE login_teacher= "'+login+'"';
	}
	else {
		str_req='select id_st as id ,login_student as login,password_student as password,f_name_student as fname,s_name_student as sname, institution FROM Students WHERE login_student="'+login+'"';
	}
	db.query(str_req,function(error,result,fields){
		if(error){
			sendError(sock,error,"entry");
			return;
		}
		if(result[0]!=undefined){
			if(result[0].password==password){
				console.log("ok");
				var data_from_db={
					answerUser: "ok",
					idUser: result[0].id.toString(),
					login: result[0].login.toString(),
					fname: result[0].fname.toString(),
					sname: result[0].sname.toString(),
					institution:result[0].institution.toString(),
				};
			}
			else{
				console.log("incorrect password");
				var data_from_db={
					answerUser: "incorrect"
				};
			}
		}
		else{
			console.log( "incorrect login");
			var data_from_db={
				answerUser: "incorrect"
			};
		}
		sockWriteAndDestroy(sock, data_from_db);
	});
}

function registration(sock, db, dataJson){
	console.log("registration");
	var fname=dataJson.dataUser.fname.toString();
	var sname=dataJson.dataUser.sname.toString();
	var institution=dataJson.dataUser.institution.toString();
	var login=dataJson.dataUser.login.toString();
	var pass=dataJson.dataUser.pass.toString();
	var user=dataJson.dataUser.user;
	var data_from_db;
	var str_req;
	if(parseInt(user)==1){
		str_req='INSERT INTO Teachers(login_teacher,password_teacher,f_name_teacher,s_name_teacher,institution) values("'+login+'","'+pass+'","'+fname+'","'+sname+'","'+institution+'")';
	}
	else {
		str_req='INSERT INTO Students(login_student,password_student,f_name_student,s_name_student,institution) values("'+login+'","'+pass+'","'+fname+'","'+sname+'","'+institution+'")';
	}
	db.query(str_req,function(error,result,fields){
		if(error){
			 if(error.toString().substring(21,36)=="Duplicate entry"){
				data_from_db={
					answer:"incorrect"
				};
				sockWriteAndDestroy(sock,data_from_db);
			 }else{
				 sendError(sock,error,"ErrorMySql_Registration");
			 }
			return;
		}
		data_from_db={
			answer: "ok"
		};
		sockWriteAndDestroy(sock, data_from_db);
	});
}

function list_of_students(sock, db, dataJson){
	var id_teacher=dataJson.dataReq.idTeacher;
	var data_from_db;
	var arrInfoStudents = new Array();
	var oneStudent;
	console.log(dataJson.toString());
	db.query('select Students.id_st as id, Students.f_name_student as fname, Students.s_name_student as sname, Students.institution from Students join conn_st_tr  where conn_st_tr.id_tr="'+id_teacher+'" and Students.id_st=conn_st_tr.id_st;', function(error,result,fields){
		if(error){
			sendError(sock,error,"list_of_students");
			return;
		}
		if(result[0]!=undefined){
			for(var i=0; i<result.length;i++){
				oneStudent={
					idStudent: result[i].id,
					fnameStudent: result[i].fname,
					snameStudent: result[i].sname,
					institution: result[i].institution
				};
			    arrInfoStudents[i]=oneStudent;
			}
			data_from_db={
					answer: "yesStudents",
					infoStudents: arrInfoStudents,
				};
		}
		else{
			data_from_db={
				answer: "noStudents"
			};
		}
		console.log(data_from_db.toString());
		sockWriteAndDestroy(sock,data_from_db);
	});
}
function listGroup(sock, db, dataJson){
	var idTeacher=dataJson.dataUser.id;
	var arrInfoGroup=new Array();
	var oneGroup;
	var data_from_db;
	db.query('select id_gr, name_group, institution, ts, st from groups join view_count_st_and_ts_in_gr as v on v.id=groups.id_gr where groups.id_tr="'+parseInt(idTeacher)+'"',function(error,result,fields){
		if(error){
			sendError(sock,error,"listGroup");
			return;
		}
		if(result[0]!=undefined){
			for(var i=0; i<result.length;i++){
				oneGroup={
					idGroup: result[i].id_gr,
					nameGroup: result[i].name_group,
					institution: result[i].institution,
					countStudents: result[i].st,
					countProblems: result[i].ts,
				};
			    arrInfoGroup[i]=oneGroup;
			}
			data_from_db={
				answer: "YesGroups",
				infoGroup: arrInfoGroup,
			};
			console.log(data_from_db.toString());
		}
		else{
			data_from_db={
				answer: "noGroup"
			};
		}
		sockWriteAndDestroy(sock, data_from_db);	
	});
}

function listTasks(sock, db, dataJson){
	var idTeacher=dataJson.dataReq.idTeacher;
	var arrInfoProblems= new Array();
	var data_from_db;
	db.query('select id_ts,name_task,date_task FROM tasks WHERE id_tr="'+parseInt(idTeacher)+'"',function(error,result,fields){
		if(error){
			sendError(sock,error,"listTasks");
			return;
		}
		if(result[0]!=undefined){
			for(var i=0;i<result.length;i++){
				var oneProblem={
					idTask: result[i].id_ts.toString(),
					nameTask: result[i].name_task.toString(),
					dateTask: result[i].date_task.toString(),
				}											 
				arrInfoProblems[i]=oneProblem;
			}
			data_from_db={
				answer: "yesTasks",
				infoTasks: arrInfoProblems
			}
		}
		else{
			data_from_db={
				answer: "noTasks"
			}
		}
		sockWriteAndDestroy(sock,data_from_db);	
	});
}

function addNewGroup(sock, db, dataJson){
	var nameNewGroup=dataJson.dataReq.nameNewGroup.toString();
	var institution=dataJson.dataReq.institution.toString();
	var idTeacher=dataJson.dataReq.idTeacher;
	var data_from_db;
	db.query('INSERT INTO groups(name_group,institution,id_tr) VALUES("'+nameNewGroup+'","'+institution+'","'+parseInt(idTeacher)+'")',function(error,result,fields){
		if(error){
			sendError(sock,error,"addNewGroup");
			return;
		}
		data_from_db={
			answer: "newGroupAdded",
		}
		sockWriteAndDestroy(sock,data_from_db);	
	});
}

function deleteGroup(sock, db, dataJson){
	var idDeleteGroup=dataJson.dataReq.idObject;
	var data_from_db;
	db.query('DELETE FROM  conn_st_gr WHERE id_gr="'+parseInt(idDeleteGroup,10)+'"',function(errorDeleteSG,resultDeleteSG,fieldsDeleteSG){
		if(errorDeleteSG){
			sendError(sock,errorDeleteSG,"deleteGroup_student");
			return;
		}
		db.query('DELETE FROM conn_ts_gr WHERE id_gr="'+parseInt(idDeleteGroup,10)+'"',function(errorDeletePG,resultDeletePG,fieldsDeletePG){
			if(errorDeletePG){
				sendError(sock,errorDeletePG,"deleteGroup_task");
				return;
			}
			db.query('DELETE FROM groups WHERE id_gr="'+parseInt(idDeleteGroup,10)+'"',function(errorDeleteGroup,resultDeleteGroup,fieldsDeleteGroup){
				if(errorDeleteGroup){
					sendError(sock,errorDeleteGroup,"deleteGroup");
					return;
				}
				data_from_db={
					answer: "groupDeleted",
				}
				sockWriteAndDestroy(sock,data_from_db);	
			});
		});
	});
}

function deleteTask(sock, db, dataJson){
	var idDeleteTask=dataJson.dataReq.idObject;
	var data_from_db;
	db.query('DELETE FROM  conn_ts_gr WHERE id_ts="'+parseInt(idDeleteTask,10)+'"',function(error,result,fields){
		if(error){
			sendError(sock,error,"deleteTask_conn_ts_gr");
			return;
		}
		db.query('DELETE FROM tasks WHERE id_ts="'+parseInt(idDeleteTask,10)+'"',function(errorDeleteTask,resultDeleteTask,fieldsDeleteTask){
			if(errorDeleteTask){
				sendError(sock,errorDeleteTask,"deleteTask");
				return;
			}
			data_from_db={
				answer: "taskDeleted",
			}
			sockWriteAndDestroy(sock,data_from_db);	
		});
	});
}

function deleteStudent(sock, db, dataJson){
	var idStudent=dataJson.dataReq.idObject;
	var idTeacher=dataJson.dataReq.idTeacher;
	var data_from_db;
	db.query('DELETE FROM  conn_st_tr WHERE id_st="'+parseInt(idStudent,10)+'" AND id_tr="'+parseInt(idTeacher,10)+'"',function(error,result,fields){
		if(error){
			sendError(sock,error,"deleteStudent_st");
			return;
		}
		console.log(idStudent);
		console.log(idTeacher);
		db.query('DELETE FROM  Students WHERE id_st="'+parseInt(idStudent,10)+'" AND login_student is NULL',function(errorSt,resultSt,fieldsSt){
		if(errorSt){
			sendError(sock,errorSt,"deleteStudent");
			return;
		}
	    data_from_db={
			answer: "studentDeleted",
		}
		sockWriteAndDestroy(sock,data_from_db);
		});
	});
}

function getTextTask(sock, db, dataJson){
	var idTask=dataJson.dataReq.idTask;
	var data_from_db;
	db.query('SELECT text_task FROM tasks WHERE id_ts="'+parseInt(idTask)+'"',function(error,result,fields){
		if(error){
			sendError(sock,error,"getTextTask");
			return;
		}
		if(result!=undefined){
			var dataTask={
				textTask: result[0].text_task.toString(),
			}
			data_from_db={
				answer: "ok",
				dataTask: dataTask,
			}
		}
		else{
			data_from_db={
				answer: "error",
			}
		}
		sockWriteAndDestroy(sock,data_from_db);
	});
}

function addNewTask(sock, db, dataJson){
	var nameNewTask=dataJson.dataReq.nameNewTask.toString();
	var textNewTask=dataJson.dataReq.textNewTask.toString();
	var dateNewTask=dataJson.dataReq.dateNewTask.toString();
	var idTeacher=dataJson.dataReq.idTeacher;
	db.query('INSERT INTO tasks(name_task,date_task,text_task,id_tr) VALUES("'+nameNewTask+'","'+dateNewTask+'","'+textNewTask+'","'+parseInt(idTeacher)+'")',function(error,result,fields){
		if(error){
			sendError(sock,error,"addNewTask");
			return;
		}
		var data_req={
			answer: "newTaskAdded",
		}	
		sockWriteAndDestroy(sock, data_req);
	});
}

function addNewStudent(sock, db, dataJson){
	var fnameNewStudent=dataJson.dataReq.fnameNewStudent.toString();
	var snameNewStudent=dataJson.dataReq.snameNewStudent.toString();
	var institution=dataJson.dataReq.institution.toString();
	var idTeacher=dataJson.dataReq.idTeacher;
	var data_req;
	db.query('INSERT INTO Students(login_student, password_student, f_name_student,s_name_student, institution) VALUES(null,null,"'+fnameNewStudent+'","'+snameNewStudent+'","'+institution+'")',function(error,result,fields){
		if(error){
			sendError(sock,error,"ErrorMySql_add_new_student");
			return;
		}
		db.query('INSERT INTO conn_st_tr(id_st,id_tr) VALUES((SELECT MAX(id_st) FROM Students),"'+parseInt(idTeacher,10)+'")',function(errorST,resultST,fieldsST){
			if(errorST){
				sendError(sock,errorST,"ErrorMySql_add_new_student_insert_into_connTrSt");
				return;
			}
			data_req={
				answer: "newStudentAdded",
			}
			sockWriteAndDestroy(sock, data_req);
		});
	});
}

function getListStudents_forGroup(sock, db, dataJson){
	var idGroup=dataJson.dataReq.idGroup;
	var arrInfoStudent=new Array();
	var data_from_db;
	db.query('SELECT students.id_st as id_st, f_name_student,s_name_student,count_solvedOfTasks, count_unsolvedOfTasks from view_count_markOfTask as a join students on students.id_st=a.id_st where a.id_gr="'+parseInt(idGroup)+'"',function(error,result,fields){
		if(error){
			sendError(sock,error,"ErrorMySql_getListStudents_forGroup");
			return;
		}	
		if(result[0]!=undefined){
			for(var i=0;i<result.length;i++){
				var oneStudent={
					idStudent: result[i].id_st.toString(),
					fnameStudent: result[i].f_name_student.toString(),
					snameStudent: result[i].s_name_student.toString(),
					countSolvedTask: 0,
					countUnsolvedTask: 0,
				}
				if(result[i].count_solvedOfTasks!=null ){
					oneStudent.countSolvedTask=result[i].count_solvedOfTasks.toString()
				}
				if(result[i].count_unsolvedOfTasks!=null){
					oneStudent.countUnsolvedTask=result[i].count_unsolvedOfTasks.toString()
				}
				arrInfoStudent[i]=oneStudent;
			}
			data_from_db={
				answer: "ok",
				dataList: arrInfoStudent,
			}
		}
		else{
			data_from_db={
				answer: "EMPTY"
			}
		}
		console.log(data_from_db);
		sockWriteAndDestroy(sock,data_from_db);	
		
	});
}

function getListTasks_forGroup(sock, db, dataJson){
	var idGroup=dataJson.dataReq.idGroup;
	var arrInfoTask=new Array();
	var data_from_db;
	db.query('SELECT id_ts, name_task FROM tasks WHERE id_ts IN (SELECT id_ts FROM conn_ts_gr WHERE id_gr="'+parseInt(idGroup)+'")',function(error,result,fields){
		if(error){
			sendError(sock,error,"ErrorMySql_getListTask_forGroup");
			return;
		}
		if(result[0]!=undefined){
			for(var i=0;i<result.length;i++){
				var oneTask={
					idTask: result[i].id_ts.toString(),
					nameTask: result[i].name_task.toString(),
				}											 
				arrInfoTask[i]=oneTask;
			}
			data_from_db={
				answer: "ok",
				dataList: arrInfoTask,
			}
		}
		else{
			data_from_db={
				answer: "EMPTY"
			}
		}
		sockWriteAndDestroy(sock,data_from_db);	
	});
}

exports.entry = entry;
exports.registration=registration;

exports.listGroup = listGroup;
exports.listTasks = listTasks;
exports.list_of_students=list_of_students;

exports.addNewGroup = addNewGroup;
exports.addNewTask = addNewTask;
exports.addNewStudent = addNewStudent;

exports.deleteGroup = deleteGroup;
exports.deleteTask = deleteTask;
exports.deleteStudent = deleteStudent;

exports.getTextTask= getTextTask;
exports.getListStudents_forGroup= getListStudents_forGroup;
exports.getListTasks_forGroup= getListTasks_forGroup;
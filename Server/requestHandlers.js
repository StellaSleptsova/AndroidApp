function entry(sock, db, dataJson){
	console.log("entry");
	var login=dataJson.dataUser.login;
	var password=dataJson.dataUser.password;
	db.query('select id_teacher,password_teacher,first_name_teacher,second_name_teacher FROM teachers WHERE login_teacher= "'+login+'"',function(error,result,fields){
		if(error){
			console.log("ErrorMySql_entry");
		}
		if(result[0]!=undefined){
			if(result[0].password_teacher==password){
				console.log("ok");
				var data_from_db={
					answerUser: "ok",
					idUser: result[0].id_teacher.toString(),
					namef: result[0].first_name_teacher.toString(),
					names: result[0].second_name_teacher.toString(),
				};
				var data_from_db=JSON.stringify(data_from_db);
				sock.write(data_from_db);
				sock.destroy();
			}
			else{
				console.log("incorrect password");
				var data_from_db={
					answerUser: "incorrect password"
				};
				var data_from_db=JSON.stringify(data_from_db);
				sock.write(data_from_db);
				sock.destroy();
			}
		}
		else{
			console.log( "incorrect login");
			var data_from_db={
				answerUser: "incorrect login"
			};
			var data_from_db=JSON.stringify(data_from_db);
			sock.write(data_from_db);
			sock.destroy();
		}
	});
}

function listGroup(sock, db, dataJson){
	console.log("list group");
	var idTeacher=dataJson.dataUser.id;
	var arrInfoGroup=new Array();
	var oneGroup;
	db.query('select id_group, name_group, count(id_s)  as id_s from groups left join studentsandgroups on groups.id_group=studentsandgroups.id_g where groups.id_t="'+parseInt(idTeacher,10)+'" group by id_group order by id_group',function(error,result,fields){
		if(error){
			console.log("ErrorMySql_listGroup");
		}
		if(result[0]!=undefined){
			for(var i=0; i<result.length;i++){
				oneGroup={
					idGroup: result[i].id_group,
					nameGroup: result[i].name_group,
					countStudents: result[i].id_s,
					countProblems:0
				};
			    arrInfoGroup[i]=oneGroup;
				console.log(arrInfoGroup[i]);
			}
			db.query('select  count(id_p) as id_p from groups left join problemsandgroups on groups.id_group=problemsandgroups.id_g where groups.id_t="'+parseInt(idTeacher,10)+'" group by id_group order by id_group',function(errorProblems,resultProblems,fieldsProblems){
				if(errorProblems){
					console.log("ErrorMySql_listGroup_countProblems");
				}
				for(var j=0; j<resultProblems.length;j++){
				    arrInfoGroup[j].countProblems=resultProblems[j].id_p;
				}
				var data_from_db={
					answer: "YesGroups",
					infoGroup: arrInfoGroup,
				};
               	data_from_db=JSON.stringify(data_from_db);
				console.log(data_from_db);
				sock.write(data_from_db);
				sock.destroy();
			});		
		}
		else{
			var data_from_db={
				answer: "noGroup"
			};
			data_from_db=JSON.stringify(data_from_db);
			console.log(data_from_db);
			sock.write(data_from_db);
			sock.destroy();
		}
	});
}

function listStudent(sock, db, dataJson){
	var idGr=dataJson.dataReq.idGroup;
	var arrInfoStudents= new Array();
	db.query('select id_student, first_name_student,second_name_student FROM studentsandgroups INNER JOIN students ON students.id_student=studentsandgroups.id_s WHERE studentsandgroups.id_g="'+parseInt(idGr)+'"',function(error,result,fields){
		if(error){
			console.log("ErrorMySql_listStudent");
		}
		if(result!=undefined){
			console.log(result);
			for(var i=0;i<result.length;i++){
				var oneStudent={
					idStudent: result[i].id_student.toString(),
					nameStudent: result[i].first_name_student.toString()+" "+result[i].second_name_student.toString(),
				}
                console.log(result[i].id_student);
                arrInfoStudents[i]=oneStudent;
				console.log(arrInfoStudents[i]);
			}
			var data_req={
				answer: "yesStudents",
				infoStudents: arrInfoStudents
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();
		}
		else{
			console.log("noStudents");
			var data_req={
				answer: "error",
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();
		}
	});
}

function listProblems(sock, db, dataJson){
	var idGr=dataJson.dataReq.idGroup;
	var arrInfoProblems= new Array();
	db.query('select id_problem,name_problem,SUBSTRING(text_problem,1,36) AS text FROM problemsandgroups INNER JOIN problems ON problems.id_problem=problemsandgroups.id_p WHERE problemsandgroups.id_g="'+parseInt(idGr)+'"',function(error,result,fields){
		if(error){
			console.log("ErrorMySql_113");
		}
		if(result!=undefined){
			for(var i=0;i<result.length;i++){
				var oneProblem={
					idProblem: result[i].id_problem.toString(),
					nameProblem: result[i].name_problem.toString(),
					textProblem:result[i].text.toString()
				}											 
				arrInfoProblems[i]=oneProblem;
			}
			var data_req={
				answer: "yesProblems",
				infoProblems: arrInfoProblems
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();
		}
		else{
			var data_req={
				answer: "noProblems"
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();
		}
	});
}

function addNewGroup(sock, db, dataJson){
	var nameNewGroup=dataJson.dataReq.nameNewGroup.toString();
	var idTeacher=dataJson.dataReq.idTeacher;
	db.query('INSERT INTO groups(name_group,id_t) VALUES("'+nameNewGroup+'","'+parseInt(idTeacher)+'")',function(error,result,fields){
		if(error){
			console.log("ErrorMySql_173");
			var data_req={
				answer: "error",
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();	
		}
		var data_req={
			answer: "newGroupAdded",
		}
		data_req=JSON.stringify(data_req);
		console.log(data_req);
		sock.write(data_req);
		sock.destroy();	
	});
}

function deleteGroup(sock, db, dataJson){
	var idDeleteGroup=dataJson.dataReq.idObject.toString();
	db.query('DELETE FROM  studentsandgroups WHERE id_g="'+parseInt(idDeleteGroup,10)+'"',function(errorDeleteSG,resultDeleteSG,fieldsDeleteSG){
		if(errorDeleteSG){
			console.log("ErrorMySql_223");
			var data_req={
				answer: "error",
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();
		}
		db.query('DELETE FROM problemsandgroups WHERE id_g="'+parseInt(idDeleteGroup,10)+'"',function(errorDeletePG,resultDeletePG,fieldsDeletePG){
			if(errorDeletePG){
				console.log("ErrorMySql_252");
				var data_req={
					answer: "error",
				}
				data_req=JSON.stringify(data_req);
				console.log(data_req);
				sock.write(data_req);
				sock.destroy();
			}
			db.query('DELETE FROM groups WHERE id_group="'+parseInt(idDeleteGroup,10)+'"',function(errorDeleteGroup,resultDeleteGroup,fieldsDeleteGroup){
				if(errorDeleteGroup){
					console.log(errorDeleteGroup);
					var data_req={
						answer: "error",
					}
					data_req=JSON.stringify(data_req);
					console.log(data_req);
					sock.write(data_req);
					sock.destroy();
				}
				var data_req={
					answer: "groupDeleted",
				}
				data_req=JSON.stringify(data_req);
				console.log(data_req);
				sock.write(data_req);
				sock.destroy();
			});
		});
	});
}

function deleteProblem(sock, db, dataJson){
	var idDeleteGroup=dataJson.dataReq.idGroup.toString();
	var idDeleteProblem=dataJson.dataReq.idObject.toString();
	db.query('DELETE FROM  problemsandgroups WHERE id_p="'+parseInt(idDeleteProblem,10)+'" AND id_g="'+parseInt(idDeleteGroup,10)+'"',function(errorDeletePG,resultDeletePG,fieldsDeletePG){
		if(errorDeletePG){
			console.log(errorDeletePG);
		}
		var data_req={
			answer: "problemDeleted",
		}
		data_req=JSON.stringify(data_req);
		console.log(data_req);
		sock.write(data_req);
		sock.destroy();
	});
}

function deleteStudent(sock, db, dataJson){
	var idDeleteStudent=dataJson.dataReq.idObject.toString();
	var idDeleteGroup=dataJson.dataReq.idGroup.toString();
	db.query('DELETE FROM  studentsandgroups WHERE id_s="'+parseInt(idDeleteStudent,10)+'" AND id_g="'+parseInt(idDeleteGroup,10)+'"',function(errorDeleteSG,resultDeleteSG,fieldsDeleteSG){
		if(errorDeleteSG){
			console.log(errorDeleteSG);
		}
		var data_req={
			answer: "studentDeleted",
		}
		data_req=JSON.stringify(data_req);
		console.log(data_req);
		sock.write(data_req);
		sock.destroy();
	});
}

function getTextProblem(sock, db, dataJson){
	var idProblem=dataJson.dataReq.idProblem.toString();
	db.query('SELECT name_problem, text_problem FROM problems WHERE id_problem="'+parseInt(idProblem)+'"',function(error,result,fields){
		if(error){
			console.log("ErrorMySql_282");
		}
		if(result!=undefined){
			var dataProblem={
				nameProblem: result[0].name_problem.toString(),
				textProblem: result[0].text_problem.toString(),
			}
			var data_req={
				answer: "ok",
				dataProblem: dataProblem,
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();
		}
		else{
			var data_req={
				answer: "error",
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();
		}
	});
}

function addNewProblem(sock, db, dataJson){
	var nameNewProblem=dataJson.dataReq.nameNewProblem.toString();
	var textNewProblem=dataJson.dataReq.textNewProblem.toString();
	var idGroup=dataJson.dataReq.idGroup;
	db.query('INSERT INTO problems(name_problem,text_problem) VALUES("'+nameNewProblem+'","'+textNewProblem+'")',function(error,result,fields){
		if(error){
			console.log("ErrorMySql_281");
			var data_req={
				answer: "error",
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();	
		}
		db.query('INSERT INTO problemsandgroups(id_p,id_g) VALUES((SELECT MAX(id_problem) FROM problems),"'+parseInt(idGroup,10)+'")',function(errorPG,resultPG,fieldsPG){
			 if(errorPG){
				console.log("ErrorMySql_292");
				var data_req={
					answer: "error",
				}
				data_req=JSON.stringify(data_req);
				console.log(data_req);
				sock.write(data_req);
				sock.destroy();	
			}
			var data_req={
				answer: "newProblemAdded",
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();	
		});
	});
}

function addNewStudent(sock, db, dataJson){
	var fnameNewStudent=dataJson.dataReq.fnameNewStudent.toString();
	var snameNewStudent=dataJson.dataReq.snameNewStudent.toString();
	var idGroup=dataJson.dataReq.idGroup;
	db.query('INSERT INTO students(login_student,password_student, first_name_student,second_name_student) VALUES("login","pass","'+fnameNewStudent+'","'+snameNewStudent+'")',function(error,result,fields){
		if(error){
			console.log("ErrorMySql_317");
			var data_req={
				answer: "error",
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();	
		}
		db.query('INSERT INTO studentsandgroups(id_s,id_g) VALUES((SELECT MAX(id_student) FROM students),"'+parseInt(idGroup,10)+'")',function(errorSG,resultSG,fieldsSG){
			if(errorSG){
				console.log("ErrorMySql_328");
				var data_req={
					answer: "error",
				}
				data_req=JSON.stringify(data_req);
				console.log(data_req);
				sock.write(data_req);
				sock.destroy();	
			}
			var data_req={
				answer: "newStudentAdded",
			}
			data_req=JSON.stringify(data_req);
			console.log(data_req);
			sock.write(data_req);
			sock.destroy();	
		});
	});
}

exports.entry = entry;
exports.listGroup = listGroup;
exports.listStudent = listStudent;
exports.listProblems = listProblems;
exports.addNewGroup = addNewGroup;
exports.deleteGroup = deleteGroup;
exports.deleteProblem = deleteProblem;
exports.deleteStudent = deleteStudent;
exports.getTextProblem = getTextProblem;
exports.addNewProblem = addNewProblem;
exports.addNewStudent = addNewStudent;
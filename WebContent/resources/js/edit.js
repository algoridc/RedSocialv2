var tab=2;
$( document ).ready(function() {
	$(function() {
		//CONTROL DE ACCESO//////////////////////////////////////////////////////

		var _name = sessionStorage.getItem("name");
		var _email = sessionStorage.getItem("email");
		var _status = sessionStorage.getItem("status"); 
		//alert("wall.js SESSION ln10--> name: "+_name+" email: "+_email+" status: "+_status);
		if(_status != "ok"){
			//ERROR DE ESTADO
			alert("No ha iniciado sesion");
			location.href ="index.html";
		}
		////////////////////////////////////////////////////////////////////////
		
	    $('#login-form-link').click(function(e) {
	    	$("#login-form").delay(100).fadeIn(100);
	 		$("#register-form").fadeOut(100);
			$('#register-form-link').removeClass('active');
			$(this).addClass('active');
			e.preventDefault();
		});
		$('#register-form-link').click(function(e) {
			$("#register-form").delay(100).fadeIn(100);
	 		$("#login-form").fadeOut(100);
			$('#login-form-link').removeClass('active');
			$(this).addClass('active');
			e.preventDefault();
			
		});
		//Relleno los datos///////////////////////////////////////////////////
		document.getElementById("email-register").value = _email;
		document.getElementById("email").value = _email;
		document.getElementById("username").value = _name;
		
		//impido modificar el correo//////////////////////////////////////////
		document.getElementById("email").disabled = true;
		

	});
	
	$('#register-submit').attr('disabled','disabled');
	
	$( "#confirm-password" ).keyup(function() {
		 
		var password1 = $("#password-register").val();
		var password2 = $("#confirm-password").val();

	    if((password1 === password2) && password2!='') {
	    	$("#status").css("color","green");
	    	$("#password-register").css('border-color', 'green');
	    	$("#confirm-password").css('border-color', 'green');
	    	$("#status").text("Contraseña correcta");
	    	$('#register-submit').removeAttr('disabled');
	    }
	    else {
	    	$("#status").css("color","red");
	    	$('#register-submit').attr('disabled','disabled');
	    	$("#password-register").css('border-color', 'red');
	    	$("#confirm-password").css('border-color', 'red');
	    	$("#status").text("No coinciden las contraseñas ");  
	    };
	});
	
	$( "#password-register" ).keyup(function() {
		$('.error-list').show();
		var password1 = $("#password-register").val();
		var password2 = $("#confirm-password").val();
		
	    if((password1 === password2) && password2!='') {
	    	$("#status").css("color","green");
	    	$("#password-register").css('border-color', 'green');
	    	$("#confirm-password").css('border-color', 'green');
	    	$("#status").text("Contraseña correcta");
	    	$('#register-submit').removeAttr('disabled');
	    }
	    else {
	    	$("#status").css("color","red");
	    	$('#register-submit').attr('disabled','disabled');
	    	$("#password-register").css('border-color', 'red');
	    	$("#confirm-password").css('border-color', 'red');
	    	$("#status").text("No coinciden las contraseñas 2");  
	    };
	});
	
	$(document).keypress(function (e){
		if(e.which == 13){
			if (tab==1){
				var email = $('#email').val();
				var pwd = $('#password').val();
				$.post( "delete.do",{ email: email, password: pwd}, function( data ) {
					var json = JSON.parse(data);
					if(json.status=='ok'){
						//CIERRO SESION
						sessionStorage.clear();
						location.href ="index.html";
					}else{
						alert(json.message);
					}
				});
			}else if(tab==2){
				var email = $('#email-register').val();
				var username = $('#username').val();
				var password = $('#password-register').val();
				var password2 = $('#confirm-password').val();
				
				if (email=='' || username==''){
						 alert("No puedes dejar vacio el usuario o la contraseña");
				}else{
						 $.post( "edit.do",
									 { email: email, password: password , username:username, 'confirm-password': password2}, 
									 
								function( data ) {
									var json = JSON.parse(data);
									if(json.status=='ok'){
										//GUARDAR EL NOMBRE Y EMAIL EN LOCALSTORAGE
										sessionStorage.setItem("name", json.name);
										sessionStorage.setItem("email", json.mail);
										sessionStorage.setItem("status", json.status);
										// REDIRECCIÓN AL MURO.
										location.href ="wall.jsp";
										
									}else{
										alert(json.message);
									}
						});
				}
		}else{
						 $("#email-register").css('border-color', 'red');
						 $("#error-email").css("color","red");
						 $("#error-email").text("Correo electrónico en uso");
		}
		
		}
	});
	
	//MODIFICARUSUARIO//////////////////////////////////////////////////////////////////////////////////////
	$('#register-submit').click(function(){
		
		var email = $('#email-register').val();
		var username = $('#username').val();
		var oldpassword = $('#old-password').val();
		var password = $('#password-register').val();
		var password2 = $('#confirm-password').val();
				 
		if (email=='' || username==''){
					 alert("No puedes dejar vacio el usuario o la contraseña");
		}else{
					 $.post( "edit.do",
							 { email: email, oldpassword:oldpassword , password: password , username:username, 'confirm-password': password2}, 
							 
						function( data ) {
							var json = JSON.parse(data);
							if(json.status=='ok'){
								
								//GUARDAR EL NOMBRE Y EMAIL EN LOCALSTORAGE
								
								sessionStorage.setItem("name", json.name);
								sessionStorage.setItem("email", json.mail);
								sessionStorage.setItem("status", json.status);
								// REDIRECCIÓN AL MURO.
								location.href ="wall.jsp";
								
							}else{
							   	 $("#email-register").css('border-color', 'red');
								 $("#error-email").css("color","red");
								 $("#error-email").text("Correo electrónico en uso");
								alert(json.message);
							}
						});
			}


	})
	
	$('#password').keyup(function(){
		$('.error-list').hide();
	})
	// ELIMINAR USUARIO///////////////////////////////////////////////////////////////////////////////
	$('#delete-submit').click(function(){
		var email = $('#email').val();
		var pwd = $('#password').val();
		$.post( "delete.do",{ email: email, password: pwd}, function( data ) {
			var json = JSON.parse(data);
			if(json.status=='ok'){
				
				sessionStorage.clear();
				// REDIRECCIÓN AL MURO.
				location.href ="index.html";
			}else{
				alert(json.message);
			}
		});
	})
	
});

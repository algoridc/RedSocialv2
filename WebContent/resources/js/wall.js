var arrayIdPublicaciones = new Array();
$( document ).ready(function() {
		
	$(function() {

		var _name = sessionStorage.getItem("name");
		var _email = sessionStorage.getItem("email");
		var _status = sessionStorage.getItem("status"); 
		//alert("wall.js SESSION ln10--> name: "+_name+" email: "+_email+" status: "+_status);
		if(_status != "ok"){
			//ERROR DE ESTADO
			alert("No ha iniciado sesion");
			location.href ="index.html";
		}
		//$('#menuNombre').val();
		document.getElementById("menuNombre").innerHTML = _name;
		document.getElementById("infoUser").innerHTML = "Bienvenido "+_name+"!!";
		
	});
	$('#logOut').click(function(){
		//borro la sesion
		sessionStorage.clear();
		//vuelvo al inicio
		location.href ="index.html";
		
	});
	$('#menuNombre').click(function(){
		location.href ="edit.jsp";
		
	});
	$('#menuConfiguracion').click(function(){
		location.href ="edit.jsp";
		
	});
	
	
	$('#sendForm').on('click', function(e){
		$('#edit-form').submit();
	});
		
	cargarDatosInput();
	cargarPublicaciones();
});

function cargarDatosInput(){
	
	var nombre = sessionStorage.getItem("name");
	var email = sessionStorage.getItem("email");
	$('#nombre_hidden').val(nombre);
	$('#email_hidden').val(email);
}

function cargarPublicaciones(){
	
	$.post( "publicaciones.do", function( data ) {
		var json = JSON.parse(data);
		for(i=0;i<json.length;i++){
			arrayIdPublicaciones.push(json[i].idPublicacion);
			var html = "<div class='col-md-4 col-md-offset-4'>";
			html += "<div class='panel panel-default panel-google-plus'>"; 
            html += "<div class='dropdown'>";
            if (sessionStorage.getItem("email")==json[i].email) {
            html +=  "<span class='dropdown-toggle' data-toggle='dropdown'>";
            html += "<span class='glyphicon glyphicon-chevron-down'></span>";
            html += "</span>";
            html += "<ul class='dropdown-menu' role='menu'>";
            html += "<li role='presentation'><a href='#' onclick='showModal("+i+")'><span class='glyphicon glyphicon-edit'></span> Editar</a></li>";
            html += "<li role='presentation'><a href='#' onclick='eliminarPublicacion("+i+");' ><span class='glyphicon glyphicon-trash'></span> Eliminar</a></li>";
            html += "</ul>";
            }
            html += "</div>";
            html += "<div class='panel-heading'>";
            html += "<h3>"+json[i].nombre+"</h3>";
            html += "<h5>"+json[i].email+"</h5>";
            html += "<h5><span>publicado el </spa><span>"+json[i].fecha+"</span> </h5>";
            html += "</div>";
            html += "<div class='panel-body'>";
            html += "<p id='mensaje-"+i+"'>"+json[i].mensaje+"</p>";
            html += "<div class='col-md-12' id='respuestas-"+json[i].idPublicacion+"'></div>";
            html += "</div>";
        	html += "<div class='panel-footer'>";
        	html += "<button type='button' id='numberLike-"+json[i].idPublicacion+"'  onclick='darLike("+i+");' class=' btn btn-default '><span class='glyphicon glyphicon-thumbs-up'></span></button>";
        	html += "<div class='input-placeholder'>Añadir comentario...</div>";
        	html += "<input type='hidden' id='id-publicacion-"+i+"' value='"+json[i].idPublicacion+"'>";
        	html += "</div>";
        	html += "<div class='panel-google-plus-comment'>";
        	html += "<div class='panel-google-plus-textarea'>";
        	html += "<textarea id='respuesta-"+i+"' rows='6'></textarea>";
        	html += "<button type='reset' class='btn btn-default'>Cancelar</button>";
        	html += "<button type='button' onclick='sendRespuesta("+i+");' class='btn btn-success'>Responder</button>";	
        	html += "</div>";
        	html += "<div class='clearfix'></div>";
        	html += "</div>";
            html += "</div>";
            html += "</div>";
			$('#div_publicaciones').append(html);
		}
		   $('.panel-google-plus > .panel-footer > .input-placeholder, .panel-google-plus > .panel-google-plus-comment > .panel-google-plus-textarea > button[type="reset"]').on('click', function(event) {
		        var $panel = $(this).closest('.panel-google-plus');
		            $comment = $panel.find('.panel-google-plus-comment');
		            
		        $comment.find('.btn:first-child').addClass('disabled');
		        $comment.find('textarea').val('');
		        
		        $panel.toggleClass('panel-google-plus-show-comment');
		        
		        if ($panel.hasClass('panel-google-plus-show-comment')) {
		            $comment.find('textarea').focus();
		        }
		   });
		   $('.panel-google-plus-comment > .panel-google-plus-textarea > textarea').on('keyup', function(event) {
		        var $comment = $(this).closest('.panel-google-plus-comment');
		        
		        $comment.find('button[type="submit"]').addClass('disabled');
		        if ($(this).val().length >= 1) {
		            $comment.find('button[type="submit"]').removeClass('disabled');
		        }
		   });
		   cargarLikes();
		   cargarRespuestas();
	});
	
	
}

function eliminarPublicacion(id){
	
	var id = $('#id-publicacion-'+id).val();
	$.post( "deletepubli.do",{ id: id}, function( ) {
		location.href ="wall.jsp";
	});
}

function showModal(id){
	
	var texto = $('#mensaje-'+id).text();
	var id = $('#id-publicacion-'+id).val();
	var nombre = sessionStorage.getItem("name");
	var email = sessionStorage.getItem("email");
	$('#editModal').modal('show');
	//Inicializamos los valores a vacio.
	$('#edit-mensaje').text("");
	$('#edit-name').text("");
	$('#edit-email').text("");
	$('#edit-id').text("");
	// Añadimos los nuevos valores.
	$('#edit-mensaje').text(texto);
	$('#edit-name').val(nombre);
	$('#edit-email').val(email);
	$('#edit-id').val(id);
}

function darLike(id){

	var idPublicacion = $('#id-publicacion-'+id).val();
	var emailUsuario=sessionStorage.getItem("email");
	
	$.post( "checklike.do",{ idPublicacion:idPublicacion, emailUsuario:emailUsuario}, function(data) {
		 var json = JSON.parse(data);
		 if (json.status=='ok'){
			 $.post( "deletelike.do",{ idPublicacion:idPublicacion, emailUsuario:emailUsuario}, function( ) {
					location.href ="wall.jsp";
				});
		 }else{
				$.post( "darlike.do",{ id:idPublicacion, email:emailUsuario}, function( ) {
				location.href ="wall.jsp";
			});
		 }
	});
	


}

function sendRespuesta(id){
	
	var nombre = sessionStorage.getItem("name");
	var email = sessionStorage.getItem("email");
	var mensaje = $('#respuesta-'+id).val();
	var idPublicacion = $('#id-publicacion-'+id).val();
	
	$.post( "responder.do",
	  { idPublicacion: idPublicacion,
		nombre:nombre,
		email:email,
		mensaje:mensaje
	  }, function( ) {
		location.href ="wall.jsp";
	});
}

function cargarRespuestas(){
	
	for(i=0;i<arrayIdPublicaciones.length;i++){
		var html = "";
		$.post( "respuestas.do",
				  { idPublicacion: arrayIdPublicaciones[i],
				  }, function(data) {
					 var json = JSON.parse(data);
					for (j=0;j<json.length;j++){
						
						 html += "<div class='panel panel-default'>";
						 html += "<div class='panel-heading'><p>"+json[j].nombre+" respondió el <span style='float:right;'>"+json[j].fecha+"</span></p></div>";
						 html += "<div class='panel-body'>"+json[j].mensaje+"</div>";
						 html += "</div>";
						 $('#respuestas-'+json[j].idPublicacion).html(html);
						
					}		
		});
	}
}

function cargarLikes(){
	
	for(i=0;i<arrayIdPublicaciones.length;i++){
		var html = "";
		$.post( "likes.do",
				  { idPublicacion: arrayIdPublicaciones[i],
				  }, function(data) {
					 
					 var json = JSON.parse(data);
						 if (json.length>0){
							 $('#numberLike-'+json[0].idPublicacion).append(" ("+json.length+")"); 
						 }
					for (i=0;i<json.length;i++){
						if (sessionStorage.getItem("email")==json[i].emailUsuario){
							 $('#numberLike-'+json[i].idPublicacion).css('border-color','blue'); 
						}
					}
		});
	}
}
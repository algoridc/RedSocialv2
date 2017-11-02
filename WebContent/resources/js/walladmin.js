$( document ).ready(function() {
	$(function() {

		var _name = sessionStorage.getItem("name");
		var _email = sessionStorage.getItem("email");
		var _status = sessionStorage.getItem("status"); 
		//alert("wall.js SESSION ln10--> name: "+_name+" email: "+_email+" status: "+_status);

		
	});
	
	
	$('#sendForm').on('click', function(e){
		$('#edit-form').submit();
	});
	//arreglo	
	//cargarDatosInput();
	cargarUsuarios();
});

function cargarDatosInput(){
	
	var nombre = sessionStorage.getItem("name");
	var email = sessionStorage.getItem("email");
	$('#nombre_hidden').val(nombre);
	$('#email_hidden').val(email);
}

function cargarUsuarios(){
	
	$.post( "usuarios.do", function( data ) {
		var json = JSON.parse(data);
		for(i=0;i<json.length;i++){
			var html = "<div class='col-md-4 col-md-offset-4'>";
			html += "<div class='panel panel-default panel-google-plus'>"; 
            html += "<div class='dropdown'>";
            html +=  "<span class='dropdown-toggle' data-toggle='dropdown'>";
            html += "</span>";
            html += "</ul>";
            html += "</div>";
            html += "<div class='panel-heading'>"; 
            html += "<h3><span>Nombre: </span><span> <br> </br><input type='text' id='edit-nombre-"+i+"'value='"+json[i].nombre+"'></span></h5>";
            html += "<h5><span>password: </span><span><input type='text' id='edit-pwd-"+i+   "'value='"+json[i].pwd+"'></span></h5>";
            html += "<h5><span>direccion: </span><span><input type='text' id='edit-email-"+i+ "'value='"+json[i].email+"'></span></h5>";
            html += "</div>";
            html += "<div class='panel-footer'>";
            html += "<div class='col-md-5 col-md-offset-5'>"
            html += "<button type='button' onclick='eliminarUsuario("+i+");' class='btn btn-default '> <span class='glyphicon glyphicon-trash'></span></button>";
            html += "<button type='button' onclick='EditarUsuario("+i+");' class='btn btn-default'> <span class='glyphicon glyphicon-pencil'></span></button>";
            html += "</div>";
            html += "<input type='hidden' id='id-usuario-"+i+"' value='"+json[i].idUsuario+"'>";
            html += "</div>";
            html += "</div>";
            html += "</div>";
			$('#div_publicaciones').append(html);	
		}
	});
}

function eliminarUsuario(id){
	
	 var id = $('#id-usuario-'+id).val();
	$.post( "deleteuser.do",{ id: id}, function( ) {
		location.href ="walladmin.jsp";
	});
}
function EditarUsuario(id){
	var nombre = $('#edit-nombre-'+id).val();
	var email = $('#edit-email-'+id).val();
	var pwd = $('#edit-pwd-'+id).val();
	var id = $('#id-usuario-'+id).val();
	
	$.post("editarusu.do",{id: id ,nombre: nombre,email: email, pwd: pwd}, function(){
		location.href="walladmin.jsp";
	});
}





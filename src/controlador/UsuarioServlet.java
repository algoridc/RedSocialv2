package controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import auxiliares.SendMail;
import auxiliares.Utilidades;
import modelo.WallSupport;
import modelo.Usuario;
import persistencia.DAOUsuario;

@Controller
public class UsuarioServlet {
 
@Autowired
private DAOUsuario servicioDAOUsuario;


@RequestMapping("deleteuser.do")
public void deleteuser(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String id_usuario = request.getParameter("id");
	 DAOUsuario.delete(id_usuario);

}

@RequestMapping("usuarios.do")
public void usuarios(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 ArrayList<Usuario> result = DAOUsuario.selectAll();
	 String json = new Gson().toJson(result);
	 response.getWriter().print(json);
}

 @RequestMapping("registro.do")
 public void registro(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String name = request.getParameter("username");
	 String email = request.getParameter("email");
	 String password = request.getParameter("password");
	 
	 Usuario user = new Usuario(name, email, password);
	 Usuario usuario = DAOUsuario.insertUserConPWD(user, password);
	 
	 JSONObject json = new JSONObject();
	 if (usuario!=null) {
		 WallSupport.getWallSupport().setUser(usuario);
		 json.put("status", "ok");
		 json.put("name", usuario.getNombre());
		 json.put("email", usuario.getemail());
	 }else {
		 json.put("status", "ko");
		 json.put("message", "No se ha podido loguear");
	 }
	
	response.getWriter().print(json);
	 
 }
 /*
 @RequestMapping("registro.do")
 public void registro(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String name = request.getParameter("nombre");
	 String email = request.getParameter("email");
	 String password = request.getParameter("pwd");
	 System.out.println("nombre:"+name+"email:"+email+"pass:"+password);
	 Usuario user = new Usuario(name, email, password);
	 Usuario usuario = DAOUsuario.insertUserConPWD(user, password);
	 
	 JSONObject json = new JSONObject();
	 if (usuario!=null) {
		 json.put("status", "ok");
		 json.put("name", usuario.getNombre());
		 json.put("email", usuario.getemail());
	 }else {
		 json.put("status", "ko");
		 json.put("message", "No se ha podido loguear");
	 }
	 // si eres administrador ejecuta el de abajo//
	 
	 
	 response.getWriter().print(json);
	 
 }
 */
 @RequestMapping("registroadmin.do")
 public void registroadmin(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String name = request.getParameter("nombre");
	 String email = request.getParameter("email");
	 String password = request.getParameter("pwd");
	 System.out.println("nombre:"+name+"email:"+email+"pass:"+password);
	 Usuario user = new Usuario(name, email, password);
	 Usuario usuario = DAOUsuario.insertUserConPWD(user, password);
	
	 JSONObject json = new JSONObject();
	 if (usuario!=null) {
		 WallSupport.getWallSupport().setUser(usuario);
		 json.put("status", "ok");
		 json.put("name", usuario.getNombre());
		 json.put("email", usuario.getemail());
	 }else {
		 json.put("status", "ko");
		 json.put("message", "No se ha podido loguear");
	 }
	 
	 
	 response.getWriter().println("<script type=\"text/javascript\">");
	 response.getWriter().println("location='walladmin.jsp';");
	 response.getWriter().println("</script>");
	//lo quito para admin
	 //response.getWriter().print(json);
	 
 }
 
 @RequestMapping("consultar.do")
 public void consultar(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 String email = request.getParameter("email");
	 String result ="";
	 result=DAOUsuario.selectEmail(email);
	 response.getWriter().write(result); 

 }
 
 @RequestMapping("login.do")
 public void login(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
	 String email = request.getParameter("email");
	 String pwd = request.getParameter("password");
	 Usuario result=DAOUsuario.select(email,pwd); 
	 
	 
	JSONObject json = new JSONObject();
	 if (result!=null) {
		 WallSupport.getWallSupport().setUser(result);
		 json.put("status", "ok");
		 json.put("name", result.getNombre());
		 json.put("email", result.getemail());
	 }else {
		 json.put("status", "ko");
		 json.put("message", "No se ha podido loguear");
	 }
	
	response.getWriter().print(json);
 }
 
 @RequestMapping("forgotpassword.do")
 public void forgotpassword(HttpServletRequest request,HttpServletResponse response) throws JSONException, Exception {
	
	 String email = request.getParameter("email");
	 String result ="";
	 result=DAOUsuario.selectEmail(email);
	 
	 if (result=="ko") {
		
			Usuario user = DAOUsuario.selectSinPWD(email);
			SendMail send = new SendMail();
			send.sendMail(user.getemail(), Utilidades.Desencriptar(user.getPwd()));
			response.sendRedirect("index.html");
	 }

 }

 @RequestMapping("edit.do")
 public void edit(HttpServletRequest request,HttpServletResponse response) throws IOException {
	
	 JSONObject json = new JSONObject();
	 WallSupport soporte = WallSupport.getWallSupport();
	 String name = request.getParameter("username");
	 String email = request.getParameter("email");
	 String oldpassword = request.getParameter("oldpassword");
	 String password = request.getParameter("password");
	 
	// control de errores
	 boolean flag = true;
	 String msj =" Modificaciï¿½n correcta";
	 
	 //encripto la contraseï¿½a antigua
	 String pwdencriptada=Utilidades.Encriptar(oldpassword);
	 
	 //saco el usuario del soporte
	 Usuario user = soporte.getUser();
	 
	 //control de erroes de coincidencia entre passwords
	 if(!user.getPwd().equals(pwdencriptada)) {
		 flag = false;
		 msj ="Error en la contraseï¿½a antigua";
	 }
	 
	 
	//si la antigua contraseï¿½a encriptada coincide con la que habia...
	 if(flag) {
		 //actualizo la informacion
		 user.setNombre(name);
		 user.setemail(email);
		 user.setPwd( Utilidades.Encriptar(password) );
		 //acualizo el usuario del soporte
		 soporte.setUser(user);
		 
		//actualizo la bbdd
		 if(!soporte.updateUser()) {
			 flag = false;
			 msj ="Error al actualizar la base de datos";
		 }
	 }
		 
	 //si no ha habido errores
	 if (flag) {
			 json.put("status", "ok");
			 json.put("name", soporte.getUser().getNombre());
			 json.put("email", soporte.getUser().getemail());
		 
	 }else {
		 json.put("status", "ko");
		 json.put("message", msj);
	 }
	
	response.getWriter().print(json);
	 
 }
 
 @RequestMapping("delete.do")
 public void delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
	 String email = request.getParameter("email");
	 String pwd = request.getParameter("password");
	 JSONObject json = new JSONObject();

	 //busco al usuario en la bbdd
	 Usuario result=DAOUsuario.select(email,pwd); 
	 
	 json.put("status", "ko");
	 json.put("message", "Usuario o contraseï¿½a erroneas");
	 
	 if (result!=null) {
		 if (WallSupport.getWallSupport().deleteUser()) {
			 json.put("status", "ok");
			 json.put("message", "Eliminado correctamente");
			 }
		 else
			 json.put("message", "No se ha podido eliminar de la base de datos");
		 
	 }
	
	response.getWriter().print(json);
 }
 @RequestMapping("editarusu.do")
 public void editarusu(HttpServletRequest request,HttpServletResponse response) throws Exception {
	 
	 
	 
	 String nombre = request.getParameter("nombre");
	 String email = request.getParameter("email");
	 String id_usuario = request.getParameter("id");
	 String pwd= Utilidades.Encriptar(request.getParameter("pwd"));
	 
	// Montamos la fecha actual para saber cuando se hizo la publicaci�n.
	 
	/* idusuario primer argumento*/
     Usuario usuario = new Usuario( id_usuario,nombre, email,pwd);
     
     DAOUsuario.update(usuario);
     
	 response.getWriter().println("<script type=\"text/javascript\">");
	 response.getWriter().println("location='walladmin.jsp';");
	 response.getWriter().println("</script>");
     
 }
 
}

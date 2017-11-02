package persistencia;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import auxiliares.Utilidades;
import persistencia.DAOUsuario;
import persistencia.MongoBroker;
import modelo.Usuario;

@Component
public class DAOUsuario {
	
	public static Usuario insertUserConPWD(Usuario usuario, String pwd) {
		
		
		Document bso=new Document();
		
		bso.put("nombre", usuario.getNombre());
		String pwdencriptada=Utilidades.Encriptar(pwd);
		bso.append("pwd", pwdencriptada);
		bso.append("email", usuario.getemail());
		
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>usuarios=broker.getCollection("Usuarios");
		usuarios.insertOne(bso);
		
		Usuario user=null;
		try {
			user = DAOUsuario.select(usuario.getemail(),pwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return user;
		
	}
	
	public static void UpdateUserPWD(Usuario usuario, String pwd) throws Exception {
		/*BsonDocument bso1=new BsonDocument();
		bso1.append("nombre", new BsonString(usuario.getNombre()));
		
		MongoBroker broker1= MongoBroker.get();
		MongoCollection<BsonDocument>usuarios=broker1.getCollection("Usuarios");
		usuarios.deleteOne(bso1);
		
		BsonDocument bso2=new BsonDocument();
		bso2.append("nombre", new BsonString(usuario.getNombre()));
		String pwdencriptada=Utilidades.Encriptar(pwd);
		bso2.append("pwd", new BsonString(pwdencriptada));
		
	
		usuarios.insertOne(bso2);
		*/
	}

	public static Usuario select(String email,String pwd) throws Exception {
		Usuario result = null;
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> usuarios=broker.getCollection("Usuarios");
		Document criterio=new Document();
		criterio.append("email", email);
		
		FindIterable<Document> resultado=usuarios.find(criterio);
		Document usuario=resultado.first();
		
		if (usuario!=null) {
			String desEncriptada = Utilidades.Desencriptar(usuario.getString("pwd"));
			if (pwd.equals(desEncriptada)) {
				result = new Usuario (usuario.getString("nombre"), usuario.getString("email"), usuario.getString("pwd"));
			}
		}
		
		return result;
	}
	
	public static Usuario selectSinPWD(String email) throws Exception {
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> usuarios=broker.getCollection("Usuarios");
		Document criterio=new Document();
		criterio.append("email", email);
		
		FindIterable<Document> resultado=usuarios.find(criterio);
		Document usuario=resultado.first();
		Usuario result=null;
		if (usuario!=null) {
			result = new Usuario (usuario.getString("nombre"), usuario.getString("email"), usuario.getString("pwd"));
		}
		
		return result;
		
	}
	
	public static String selectEmail(String email) {
		String result = "";
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> usuarios=broker.getCollection("Usuarios");
		Document criterio=new Document();
		criterio.append("email", email);
		
		FindIterable<Document> resultado=usuarios.find(criterio);
		Document usuario=resultado.first();
		
		if (usuario==null) {
			result="ok";
		}else {
			result="ko";
		}
		
		return result;
	}
	
	public static void delete(String id) {
		/*Document bso=new Document();
		bso.append("nombre", usuario.getNombre());
		
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>usuarios=broker.getCollection("Usuarios");
		usuarios.deleteOne(bso);*/
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>usuarios=broker.getCollection("Usuarios");
		usuarios.deleteOne(new Document("_id", new ObjectId(id)));
		
	}
public static ArrayList<Usuario> selectAll () {
		
		ArrayList<Usuario> result = new ArrayList<Usuario>();
		MongoBroker broker = MongoBroker.get();
		MongoCollection<Document> usuarios=broker.getCollection("Usuarios");
		FindIterable<Document> it = usuarios.find();
		MongoCursor<Document> cursor = it.iterator();
	
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			ObjectId id = (ObjectId)doc.get( "_id" );
			Usuario usu = new Usuario(id.toString(),doc.getString("nombre"), doc.getString("email"), doc.getString("pwd"));
			result.add(usu);
		}

		return result;
	}
public static ObjectId insert(Usuario usuario) {
	Document doc=new Document();
	doc.append("nombre", usuario.getNombre());
	doc.append("email", usuario.getemail());
	doc.append("password", usuario.getPwd());
	
	
	MongoBroker broker= MongoBroker.get();
	MongoCollection<Document>usuarios=broker.getCollection("Usuarios");
	usuarios.insertOne(doc);
	
	 ObjectId id = (ObjectId)doc.get( "_id" );
	
	 return id;
}
/*de git*/
public static void update (Usuario usuario) throws Exception {
	
	Document filter = new Document("_id", new ObjectId(usuario.getid()));
	Document newValue = new Document();
	newValue.append("nombre", usuario.getNombre());
	newValue.append("email", usuario.getemail());
	newValue.append("password", usuario.getPwd());
	Document updateOperationDocument = new Document("$set", newValue);
	
	MongoBroker broker= MongoBroker.get();
	MongoCollection<Document>usuarios=broker.getCollection("Usuarios");
	usuarios.updateOne(filter, updateOperationDocument);
	
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**Usuario select(Usuario user)
 * @param user
 * @return
 * Este metodo selecciona en funcion del mail
 */
public static Usuario selectAlberto(Usuario user) {
	
	Usuario result=null;
	MongoBroker broker = MongoBroker.get();
	MongoCollection<Document> usuarios=broker.getCollection("Usuarios");
	Document criterio=new Document();
	criterio.append("email", user.getemail());
	
	FindIterable<Document> resultado=usuarios.find(criterio);
	Document usuario=resultado.first();

	if (usuario!=null) {
		result = new Usuario (usuario.getString("nombre"), usuario.getString("email"), usuario.getString("pwd"));
	}
	
	return result;
	
}
public static Usuario insertAlberto(Usuario usuario) {
	
	Document bso=new Document();
	
	bso.put("nombre", usuario.getNombre());
	bso.append("pwd", usuario.getPwd());
	bso.append("email", usuario.getemail());
	
	MongoBroker broker= MongoBroker.get();
	MongoCollection<Document>usuarios=broker.getCollection("Usuarios");
	usuarios.insertOne(bso);
	
	return DAOUsuario.selectAlberto(usuario);
	
}
/**boolean update(Usuario useuario)
 * @param usuario
 * @return
 * Este metodo actualiza en funcion del mail
 */	

public static boolean updateAlberto(Usuario usuario) {
	return deleteAlberto(usuario) && insertAlberto(usuario)!=null;
}
/**boolean delete(Usuario useuario)
 * @param usuario
 * @return
 * Este metodo elimina en funcion del mail
 */
public static boolean deleteAlberto(Usuario usuario) {
	try {
		Document bso=new Document();
		bso.append("email", usuario.getemail());
	
		MongoBroker broker= MongoBroker.get();
		MongoCollection<Document>usuarios=broker.getCollection("Usuarios");
		usuarios.deleteOne(bso);
		return true;
	
	}catch(Exception e) {
		return false;
	}
	
}


}



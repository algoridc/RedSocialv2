package modelo;

import persistencia.DAOUsuario;

public class WallSupport {
	
	private static WallSupport _instancia = null;
	private Usuario _user = null;
	
	private WallSupport() {}
	public static WallSupport getWallSupport() {
		if(_instancia == null)
			_instancia = new WallSupport();
		return _instancia;
	}
	
	public boolean isUser() {
		if (_user!=null)
			return true;
		return false;
	}
	public Usuario getUser() {
		return _user;
	}
	public void setUser(Usuario user) {
		this._user = user;
	}
	
	public boolean updateUser() {
		return _user.update();
		
	}
	public boolean deleteUser() {
		
		if(_user.delete()) {
			_user = null;
			return true;
		}
		return false;
	}
	
	//public boolean selectUser() {}
	
	//public String getPublicMesseges() {}
	//public String getPrivateMessages() {}
	
	//public boolean createMenssage() {}
	//public boolean updateMessage() {}
	//public boolean deleteMessage() {}
	
	//public boolean like() {}
	//public boolean unlike() {}
	
	
	

}

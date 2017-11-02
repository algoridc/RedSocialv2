package modelo;

import persistencia.DAOUsuario;

public class Usuario {
	private String idUsuario;
	private String nombre;
	private String pwd;
	private String email;
	
	
	public Usuario() {
		
	}
	
	public Usuario(String idUsuario,String nombre, String email, String pwd) {
		this.idUsuario=idUsuario;
		this.nombre=nombre;
		this.email=email;
		this.pwd=pwd;
	}
	

	public Usuario(String nombre, String email, String pwd) {
		
		this.nombre=nombre;
		this.email=email;
		this.pwd=pwd;
	}

	public Usuario(String nombre) {
		setNombre(nombre);
	}
	
	public String getid() {
		return idUsuario;
	}
	
	public void setid(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getemail() {
		return email;
	}

	public void setemail(String direccion) {
		this.email = direccion;
	}
	
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	//Metodos DAO//////////////////////////////////////////////////////////
		public Usuario select() {
			return DAOUsuario.selectAlberto(this);
		}
		public Usuario insert() {
			return DAOUsuario.insertAlberto(this);
			
		}
		public boolean update() {
			return DAOUsuario.updateAlberto(this);
		}
		public boolean delete() {
			return DAOUsuario.deleteAlberto(this);
			
		}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", pwd=" + pwd + ", email=" + email + "]";
	}


	
	
	
	
}

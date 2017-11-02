<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="resources/js/edit.js"></script>
	<script type="text/javascript" src="resources/js/password.js"></script>
	<title>Your Website</title>
</head>

<body>
	<div class="container">
        <div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-6">
								<a href="#" class="active" id="login-form-link">Eliminar</a>
							</div>
							<div class="col-xs-6">
								<a href="#" id="register-form-link">Modificar</a>
							</div>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form id="login-form" method="post" role="form" style="display: block;">
									<div class="form-group">
										<input type="text" name="email" id="email" tabindex="1" class="form-control" placeholder="Correo Electronico" value="">
									</div>
									<div class="form-group">
										<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Contraseña">
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<button type="button" name="delete-submit" id="delete-submit" tabindex="4" class="form-control btn btn-danger">Eliminar</button>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-lg-12">
												<div class="text-center">
													<a href="forgot-password.html" tabindex="5" class="forgot-password">Recordar Contraseña</a>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
							<div class="col-lg-12 register-form">
								<form id="register-form" method="post" role="register" style="display: none;">
									<div class="form-group">
										<input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Nombre" value="" required>
									</div>
									<div class="form-group">
										<input type="email" name="email" id="email-register" tabindex="1" class="form-control" placeholder="Correo Electrónico" value="" required>
										<div id="error-email">
									
										</div>
									</div>
									<div class="form-group">
										<input type="password" name="old-password" id="old-password" tabindex="2" class="form-control" placeholder="Contraseña" required>
								
									</div>
									<div class="form-group">
										<input type="password" name="password-register" id="password-register" tabindex="2" class="form-control" placeholder="Nueva contraseña" required>
										<div class="pwstrength_viewport_progress"></div>
									</div>
									<div class="form-group">
										<input type="password" name="confirm-password" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirmar contraseña" required>
									</div>
									<div id="status">
									
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="button" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Registrar">
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>

</html>
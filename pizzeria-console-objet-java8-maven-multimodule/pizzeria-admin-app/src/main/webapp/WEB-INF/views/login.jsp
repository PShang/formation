<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<title>Connexion</title>
</head>
<body>
	<div class="container">
		<h1>Connexion</h1>
		<form class="form-horizontal" method="post">
			<fieldset>
				<div class="form-group">
					<label class="col-md-4 control-label" for="email">Email</label>
					<div class="col-md-4">
						<input id="email" name="email" type="email" placeholder="Email" class="form-control input-md" required autofocus>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="pass">Mot de passe</label>
					<div class="col-md-4">
						<input id="pass" name="pass" type="password" placeholder="Mot de passe" class="form-control input-md" required>

					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="login"></label>
					<div class="col-md-4">
						<button id="login" name="login" class="btn btn-success">Connexion</button>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</body>
</html>
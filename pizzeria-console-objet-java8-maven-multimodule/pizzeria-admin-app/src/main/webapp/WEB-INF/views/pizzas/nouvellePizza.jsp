<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="fr.pizzeria.model.CategoriePizza"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<title>Ajouter pizza</title>
</head>
<body>
	<div class="container">
		<h1>Nouvelle pizza</h1>
		<form class="form-horizontal" method="post">
			<fieldset>
				<div class="form-group">
					<label class="col-md-4 control-label" for="code">Code</label>
					<div class="col-md-4">
						<input id="code" name="code" type="text" placeholder="Code de la pizza" class="form-control input-md" required autofocus>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="nom">Nom</label>
					<div class="col-md-4">
						<input id="nom" name="nom" type="text" placeholder="Nom de la pizza" class="form-control input-md" required>

					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="categorie">Categorie</label>
					<div class="col-md-4">
						<c:forEach var="c" items="${cats}">
							<div class="radio">
								<label for="${c}"> <input type="radio" name="categorie" id="${c}" required <c:if test="${pizza.categorie.equals(c)}">checked</c:if> value="${c}">${c.libelle}</label>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="prix">Prix (€)</label>
					<div class="col-md-4">
						<input id="prix" name="prix" type="number" min="0" step="0.01" placeholder="Le prix de la pizza" class="form-control input-md" required>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="urlImage">URL Image</label>
					<div class="col-md-4">
						<input id="urlImage" name="urlImage" type="url" placeholder="URL de l'image" class="form-control input-md">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="valider"></label>
					<div class="col-md-4">
						<button id="valider" name="valider" class="btn btn-success">Valider</button>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</body>
</html>
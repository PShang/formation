<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="fr.pizzeria.model.Pizza"%>
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
<script type="text/javascript">
	function deletePizza(code) {
		$.ajax({
			type : "DELETE",
			url : '<c:url value="/api/pizzas/delete/" />' + code,
			success : function() {
				$("#pizza-" + code).remove();
			},
			error : function(msg) {
			}
		});
	}
</script>
<title>Pizzas</title>
</head>
<body>
	<div class="container">
		<h1>Liste des pizzas</h1>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Id.</th>
					<th>Code</th>
					<th>Nom</th>
					<th>Categorie</th>
					<th>Prix</th>
					<th>Image</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="pizzas" value="${list}" />
				<c:forEach var="p" items="${pizzas}">
					<tr id="pizza-${p.code}">
						<td>${p.id}</td>
						<td>${p.code}</td>
						<td>${p.nom}</td>
						<td>${p.categorie.libelle}</td>
						<td><fmt:formatNumber type="currency" value="${p.prix}" /></td>
						<td><img class="img-rounded" height="50" src="${p.urlImage}" alt="${p.nom}" title="${p.nom}" /></td>
						<td><a class="btn btn-danger" href="#" onclick="deletePizza('${p.code}')">Supprimer</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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
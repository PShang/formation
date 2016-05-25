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
			url : '<c:url value="/pizzas/edit?code=" />' + code,
			success : function() {
				$("#pizza-" + code).remove();
			},
			error : function(msg) {
			}
		});
	}
</script>
<title>Lister pizzas</title>
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
						<td>${p.prix}&nbsp;€</td>
						<td><img class="img-rounded" height="50" src="${p.urlImage}" alt="${p.nom}" title="${p.nom}" /></td>
						<td><a class="btn btn-primary" href="<c:url value="/pizzas/edit?code=${p.code}" />">Editer</a></td>
						<td><a class="btn btn-danger" href="#" onclick="deletePizza('${p.code}')">Supprimer</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<a class="btn btn-success" href="<c:url value="/pizzas/new" />">Ajouter une pizza</a>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="fr.pizzeria.model.Pizza"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
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
				<%
					List<Pizza> pizzas = ((List<Pizza>) request.getAttribute("list"));
					for (Pizza p : pizzas) {
				%>
				<tr>
					<td><%=p.getId()%></td>
					<td><%=p.getCode()%></td>
					<td><%=p.getNom()%></td>
					<td><%=p.getCategorie().getLibelle()%></td>
					<td><%=p.getPrix()%> €</td>
					<td><img class="img-rounded" height="50"
						src="<%=p.getUrlImage()%>" alt="<%=p.getNom()%>"
						title="<%=p.getNom()%>" /></td>
					<td><a class="btn btn-primary"
						href="<%=request.getContextPath()%>/pizzas/edit?code=<%=p.getCode()%>">Editer</a></td>
					<td><a class="btn btn-danger"
						href="<%=request.getContextPath()%>/pizzas/delete?code=<%=p.getCode()%>">Supprimer</a></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>
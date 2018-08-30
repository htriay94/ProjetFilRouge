<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../Template/head.jsp" />
</head>
<body id="page-top">
	<c:if test="${ !sessionScope.connected }">
		<c:redirect url="login" />
	</c:if>
	<jsp:include page="../Template/header.jsp" />
	<div id="wrapper">
		<jsp:include page="../Template/menu.jsp" />
		<div id="content-wrapper">
		<div class="container-fluid">

			<!-- Page Content -->
			<div class="card mb-3">
				<div class="card-header">
					<h3>
						<i class="fas fa-table"></i> Liste des Cours de
						<c:out value="${nomMatiere}" />
						<c:if test="${ sessionScope.user_auth.statut == 'admin'}">
								<a href="edit-matiere?idMat=${ idMatiere }" class="btn btn-primary">Edit</a>
								<a href="remove-matiere?idMat=${ idMatiere }" class="confirmation"><i class="fa fa-times" aria-hidden="true"></i></a>
						</c:if>
						<c:if test="${ sessionScope.user_auth.statut == 'admin' || sessionScope.user_auth.statut == 'formateur' }">
						<div style="float:right;">
							<a href="add-cours?idMat=${ idMatiere }&nomMat=${nomMatiere}" class="btn btn-primary" >Créer un cours</a>
						</div>
						</c:if>
					</h3>
					
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
							<thead class="thead-dark">
								<tr>
									<th scope="col">Nom</th>
									<th scope="col">Auteur</th>
									<th scope="col">Date</th>
									<th scope="col">Support</th>
									<th scope="col">Groupe</th>
									<c:if test="${ sessionScope.user_auth.statut == 'admin' }">
									<th scope="col">Supprimer</th>										
									</c:if>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th>Nom</th>
									<th>Date</th>
									<th>Support</th>
								</tr>
							</tfoot>
							<tbody>
								<c:forEach items="${ listeCours }" var="cours" varStatus="status">
									<tr>
										<td> <a href="cours?idC=${cours.idCours}">${cours.nomCours}</a></td>
										<td>${cours.user.prenomUser} ${cours.user.nomUser}</td>
										<td><fmt:formatDate type="date" dateStyle="short" value="${cours.dateCours}" /></td>
										<td>${cours.support}</td>
										<td>${cours.groupe.nomGroupe}</td>
										<c:if test="${ sessionScope.user_auth.statut == 'admin' }">
											<td> <a href="remove-cours?idC=${cours.idCours}&idMat=${ idMatiere }" class="confirmation"><i class="fa fa-times" aria-hidden="true"></i></a> </td>
										</c:if>
									</tr>
									<br>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="card-footer small text-muted">Updated now</div>
			</div>

		</div>
		<!-- /.container-fluid -->
		</div>

		<jsp:include page="../Template/footer.jsp" />
	</div>

	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>
	<jsp:include page="../Template/scripts.jsp" />
</body>
</html>
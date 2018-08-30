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
						<i class="fas fa-table"></i> Liste des groupes
						<div style="float:right;">
							<a href="add-groupe" class="btn btn-primary" >Créer un groupe</a>
						</div>
					</h3>
					
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
							<thead class="thead-dark">
								<tr>
									<th scope="col">Nom</th>
									<th scope="col">Modifier</th>
									<th scope="col">Supprimer</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${ groupes }" var="groupe" varStatus="status">
									<tr>
										<td> <a href="groupe?idGrp=${groupe.idGroupe}">${groupe.nomGroupe}</a></td>
										<td> <a href="edit-groupe?idGrp=${groupe.idGroupe}">${groupe.nomGroupe}</a></td>
										<td> <a href="remove-groupe?idGrp=${groupe.idGroupe}" class="confirmation"><i class="fa fa-times" aria-hidden="true"></i></a> </td>
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
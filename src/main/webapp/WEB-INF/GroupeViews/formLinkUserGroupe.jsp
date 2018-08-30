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
				<div class="card-header"><h3> Lier utilisateur au groupe ${ sessionScope.groupe.nomGroupe } </h3></div>
	        <div class="card-body">
	          <form method="post" action=${ action }>
	            <div class="form-group">
	            	<label for="idUsers">Login</label>
                  	<select name="idUsers" multiple class="form-control" id="idUsers">
	                  	<c:forEach items="${ users }" var="user" varStatus="status">
	                  		<option value="${ user.idUser }"><c:out value="${ user.nomUser } ${ user.prenomUser } (${ user.identifiant })"></c:out></option>
	                  	</c:forEach>
                  	</select>
	            </div>
	            <p style="color: red;">${ inputError }</p>
	            <p style="color: red;">${ nomIncorrect }</p>
	            <button class="btn btn-primary" type="submit" name="submit" id="submit">Lier</button>
	          </form>
	        </div>
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
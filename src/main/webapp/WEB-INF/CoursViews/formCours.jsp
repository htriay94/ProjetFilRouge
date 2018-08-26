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
				<div class="card-header"><h3><i class="fas fa-fw fa-book"></i>${param.nomMat} : ${ type } un cours</h3></div>
	        <div class="card-body">
	          <form method="post" action=${ action }>
	          <input type="hidden" name="idMat" id="idMat" value="${ param.idMat }">
	            <div class="form-group">
	                  <div class="form-label-group">
		                <input type="text" id="nom" name="nom" class="form-control" placeholder="Nom" value="${ sessionScope.cours.nomCours }${ newNom }" required="required">
		                <label for="nom">Nom</label>
		              </div>
	            </div>
	            <div class="form-group">
	                  <div class="form-label-group">
		                <input type="file" id="support" name="support" class="form-control" placeholder="Support">
		                <label for="support">Support</label>
		              </div>
	            </div>
	            <p style="color: red;">${ inputError }</p>
	            <p style="color: red;">${ nomIncorrect }</p>
	            <button class="btn btn-primary btn-block" type="submit" name="submit" id="submit">${ type }</button>
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
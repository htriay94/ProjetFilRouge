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
				<div class="card-header"><h3><i class="fas fa-fw fa-book"></i>${ sessionScope.cours.matiere.nomMatiere }${ param.nomMat } / ${ type } un cours : ${ sessionScope.cours.nomCours }</h3></div>
	        <div class="card-body">
	          <form method="post" action=${ action } enctype="multipart/form-data">
	            <div class="form-group">
	                  <div class="form-label-group">
		                <input type="text" id="nom" name="nom" class="form-control" placeholder="Nom" value="${ sessionScope.cours.nomCours }${ newNom }" required="required">
		                <label for="nom">Nom</label>
		              </div>
	            </div>
	            <div class="form-group">
	                  <div class="form-label-group">
		                <input type="${ date }" id="dateCours" name="dateCours" class="form-control" value="<fmt:formatDate pattern="yyyy-MM-dd"
												value="${sessionScope.cours.dateCours}"/>" placeholder="Date" required="required">
		                <label for="dateCours">Date</label>
		              </div>
	            </div>
	            <div class="form-group">
	            	<label for="idGroupe">Groupe</label>
                  	<select name="idGroupe" class="form-control" id="idGroupe">
	                  	<c:forEach items="${ groupes }" var="groupe" varStatus="status">
	                  		<option value="${ groupe.idGroupe }">${ groupe.nomGroupe }</option>
	                  	</c:forEach>
                  	</select>
	            </div>
	            <c:if test="${ action != 'edit-cours' }">
	            <div class="input-group mb-3">
				  <div class="custom-file">
				    <input type="file" class="file" id="support" name="support" aria-describedby="support">
				    <label class="custom-file-label" for="support">Support</label>
				  </div>
				</div>
				</c:if>
	            <p style="color: red;">${ inputError }</p>
	            <p style="color: red;">${ nomIncorrect }</p>
	            <button class="btn btn-primary" type="submit" name="submit" id="submit">${ type }</button>
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
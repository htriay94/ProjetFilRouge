<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../Template/head.jsp" />
	</head>
	<body class="bg-dark">
		<c:if test="${ !empty sessionScope.AlertSuccessMsg  }" >
			<div id="AlertSuccess" style="position: absolute;top:10%;left: 70%;right: 20px;" class="alert alert-success alert-dismissible fade show" role="alert">
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			  <p style="text-align: center;"><c:out value="${ AlertSuccessMsg }"></c:out></p>
			  <% session.removeAttribute("AlertSuccessMsg"); %>
			</div>
		</c:if>
		
		<div class="container">
	      <div class="card card-login mx-auto mt-5">
	        <div class="card-header">Connexion</div>
	        <div class="card-body">
			  <form method="post" action="login">
	            <div class="form-group">
	              <div class="form-label-group">
	                <input type="text" id="login" name="login" value="${ login }" class="form-control" placeholder="Login" required="required" autofocus="autofocus">
	                <label for="login">Login</label>
	              </div>
	            </div>
	            <div class="form-group">
	              <div class="form-label-group">
	                <input type="password" id="mdp" name="mdp" class="form-control" placeholder="Mot de passe" required="required">
	                <label for="mdp">Mot de passe</label>
	              </div>
	            </div>
	            <hr>
	            <c:if test="${ !empty msgLog  }" >
	            <div class="alert alert-danger" role="alert">
	            	<c:out value="${ msgLog }"></c:out>
	            	<c:remove var = "msgLog"/>
	            </div>
	            </c:if>
	            <hr>
	            <button class="btn btn-primary btn-block" type="submit" id="submit">Connexion</button>
	          </form>
	          <div class="text-center">
	            <a class="d-block medium mt-3" href="register">Créer un compte</a>
	          </div>
	        </div>
	      </div>
	    </div>
		<jsp:include page="../Template/scripts.jsp" />
	</body>
</html>
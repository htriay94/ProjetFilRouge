<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../Template/head.jsp" />
	</head>
	<!-- Desactivation de l'input login lors de l'édition -->
	<c:if test="${ sessionScope.connected }" >
		<c:set var="disabledInput" value="disabled" scope="request"></c:set>
	</c:if>
	<body class="bg-dark">
	    <div class="container">
	      <div class="card card-register mx-auto mt-5">
	        <div class="card-header">${ type } un compte</div>
	        <div class="card-body">
	          <form method="post" action=${ action }>
	          	 <div class="form-group">
	            	<div class="form-row">
	                	<div class="col-md-6">
		                  	<div class="form-label-group">
				                <input ${ disabledInput } type="text" name="login" id="login" class="form-control" placeholder="Login" required="required" value="${ user_auth.identifiant }${ newLogin }" autofocus="autofocus" >
				                <label for="login">Login</label>
		              		</div>
	                	</div>
	                	<div class="col-md-6">
		                  	<div class="form-label-group">
				                <span id="validationLogin"></span>
		              		</div>
	                	</div>
	              	</div>
	            </div>
	            <div class="form-group">
	            		<label for="statut">Statut :</label>
						<select class="form-control" id="statut" name="statut" required="required">
						      <option>stagiaire</option>
						      <option>formateur</option>
						</select>		                
						
               	</div>
	            <div class="form-group">
	              <div class="form-row">
	                <div class="col-md-6">
	                  <div class="form-label-group">
	                    <input type="password" id="mdp" name="mdp" class="form-control" placeholder="Mot de passe" required="required">
	                    <label for="mdp">Mot de passe</label>
	                  </div>
	                </div>
	                <div class="col-md-6">
	                  <div class="form-label-group">
	                    <input type="password" id="confirmMdp" name="confirmMdp" class="form-control" placeholder="Confirmation du mot de passe" required="required">
	                    <label for="confirmMdp">Confirmation du mot de passe</label>
	                  </div>
	                </div>
	              </div>
	            </div>
	            <div class="form-group">
	              <div class="form-row">
	                <div class="col-md-6">
	                  <div class="form-label-group">
	                    <input type="text" id="nom" name="nom" class="form-control" placeholder="Nom" value="${ user_auth.nomUser }${ newNom }" required="required">
	                    <label for="nom">Nom</label>
	                  </div>
	                </div>
	                <div class="col-md-6">
	                  <div class="form-label-group">
	                    <input type="text" id="prenom" name="prenom" class="form-control" placeholder="Prénom" value="${ user_auth.prenomUser }${ newPrenom }" required="required" autofocus="autofocus">
	                    <label for="prenom">Prénom</label>
	                  </div>
	                </div> 
	            </div>
	            </div>
	            <div class="form-group">
	              <div class="form-row">
	                <div class="col-md-6">
	                  <div class="form-label-group">
		                <input type="email" id="email" name="email" class="form-control" placeholder="Email" value="${ user_auth.email }${ newEmail }" required="required">
		                <label for="email">Email</label>
		              </div>
		            </div>
		            <div class="col-md-6">
	                  <div class="form-label-group">
		                <span id="validationEmail"></span>
		              </div>
		            </div>
		           </div>
	            </div>
	            <div class="form-group">
	              <div class="form-label-group">
	                <input type="number" id="tel" name="tel" class="form-control" placeholder="Téléphone" value="${ user_auth.telephone }${ newTel }" required="required">
	                <label for="tel">Téléphone</label>
	              </div>
	            </div>
	          
		        <p style="color: red;">${ nomIncorrect }</p>
		        <p style="color: red;">${ prenomIncorrect }</p>
		        <p style="color: red;">${ mdpError }</p>
	            <button class="btn btn-primary btn-block" type="submit" name="submit" id="submit">${ type }</button>
	          </form>
	          <!-- <div class="alert alert-danger alert-dismissible fade show" role="alert">
	          	<span id="validationEmail"></span>
	          	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				  <span aria-hidden="true">&times;</span>
				</button>
	          </div> -->
	        </div>
	        <c:if test="${ !sessionScope.connected }" >
		        <div class="text-center">
		          <a class="d-block medium mt-3" href="login">Login</a>
		        </div>
	        </c:if>
	      </div>
	    </div>
	    <jsp:include page="../Template/scripts.jsp" />
	</body>
	
</html>
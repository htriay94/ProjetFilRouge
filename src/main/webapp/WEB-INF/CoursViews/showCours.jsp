<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../Template/head.jsp" />
</head>
<body id="page-top">
	<c:set var = "path_uploads" scope = "request" value = "C:/Users/Stagiaire/Documents/JEE/ProjetFilRouge/src/main/webapp/uploads/${ sessionScope.cours.support }"></c:set>
	<%-- <c:out value = "${ path_uploads }"/> --%>
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
				<div class="card-header"><h3>
					<i class="fas fa-fw fa-book"></i> 
					${ sessionScope.cours.matiere.nomMatiere } / ${ sessionScope.cours.nomCours } par ${ sessionScope.cours.user.prenomUser } ${ sessionScope.cours.user.nomUser }
					<c:if test="${ sessionScope.user_auth.statut == 'admin' || (sessionScope.user_auth.statut == 'formateur' && sessionScope.user_auth.idUser == sessionScope.cours.user.idUser) }">
					<div style="float:right;">
						<a href="edit-cours" class="btn btn-primary">Modifier</a>
					</div>
					</c:if>
				</h3></div>
		        <div class="card-body">
		        	<%-- <object data="${path_uploads}" type="application/pdf" width="500" height="300"/> --%>
		          <h3 style="margin:1em;"><a href="display-pdf" target="_blank"> Télécharger le cours <i class="fa fa-download" aria-hidden="true"></i>
		          </a></h3>
		          <!-- Section Commentaires -->
					  <div class="row">
					    <div class="col-sm-12">
					        <div class="comment-tabs">
					            <ul class="nav nav-tabs">
								  <li class="nav-item">
								    <a class="nav-link active" href="#comments-logout" role="tab" data-toggle="tab">Commentaires ${ sessionScope.cours.commentaires.size() }</a>
								  </li>
								  <li class="nav-item">
								    <a class="nav-link" href="#add-comment" role="tab" data-toggle="tab">Commenter</a>
								</ul>           
					            <div class="tab-content">
					                <div class="tab-pane active" id="comments-logout">                
					                    <ul class="media-list">
					                    <c:forEach items="${ sessionScope.cours.commentaires }" var="commentaire" varStatus="status">
					                      <li class="media">
					                        <div class="media-body">
					                          <div class="well well-lg">
					                              <h4 class="media-heading reviews"> <c:out value="${ commentaire.user.nomUser } ${ commentaire.user.prenomUser } (${ commentaire.user.identifiant })"></c:out> </h4>
					                              <hr>
					                              <ul class="media-date text-uppercase reviews list-inline">
					                                <li><fmt:formatDate type="date" dateStyle="short" value="${ commentaire.dateCommentaire }" /></li>
					                              </ul>
					                              <p class="media-comment">
					                                <c:out value="${ commentaire.contenuCommentaire }"></c:out>
					                              </p>
					                          </div>              
					                        </div>
					                      </li>
					                      </c:forEach>
					                      <c:if test = "${ empty sessionScope.cours.commentaires }">
					                       <b>Aucun commentaire</b>
					                      </c:if>
					                    </ul> 
					                </div>
					                <div class="tab-pane" id="add-comment">
					                    <form action="add-comment" method="post" class="form-horizontal" id="commentForm" role="form"> 
					                        <div class="form-group">
					                            <label for="content" class="col-sm-2 control-label">Comment</label>
					                            <div class="col-sm-10">
					                              <textarea class="form-control" name="content" id="content" rows="5"></textarea>
					                            </div>
					                        </div>
					                        <p style="color: red;">${ inputError }</p>
					                        <p style="color: red;">${ contentIncorrect }</p>
					                        <div class="form-group">
					                            <div class="col-sm-offset-2 col-sm-10">                    
					                                <button class="btn btn-success" type="submit" ><span class="glyphicon glyphicon-send"></span> Commenter</button>
					                            </div>
					                        </div>            
					                    </form>
					                </div>
					                
					            </div>
					        </div>
						</div>
					  </div>
		          <!-- end Section Commentaires -->
	            </div>
            </div>
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
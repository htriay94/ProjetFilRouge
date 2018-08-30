<!-- Sidebar -->
<ul class="sidebar navbar-nav">
	<li class="nav-item active">
		<a class="nav-link" href="home">
			<i class="fas fa-fw fa-home"></i> <span>Accueil</span>
		</a>
	</li>
	<c:if test="${ !empty sessionScope.user_auth}">
	<li class="nav-item"><a class="nav-link" href="calendar"> <i
			class="fas fa-fw fa-calendar"></i> <span>Calendrier</span></a>
	</li>
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 
			<i class="fas fa-fw fa-book"></i> 
			<span>Cours</span>
		</a>
		<div class="dropdown-menu listes-matieres" aria-labelledby="pagesDropdown">
			<c:if test="${ sessionScope.user_auth.statut == 'admin' }">
				<a class="dropdown-item" href="add-matiere">Ajouter matiere</a>
				<div class="dropdown-divider"></div>
			</c:if>
		</div>
	</li> 
	<c:if test="${ sessionScope.user_auth.statut == 'admin' }">
	<li class="nav-item"><a class="nav-link" href="liste-groupes"> <i class="fa fa-users" aria-hidden="true"></i>
 		<span>Groupes</span></a>
	</li>
	</c:if>
	</c:if>
</ul>
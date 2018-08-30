<nav class="navbar navbar-expand navbar-dark bg-dark static-top">
	<a class="navbar-brand mr-1" href="home">Elsilio</a>
	<button class="btn btn-link btn-sm text-white order-1 order-sm-0"
		id="sidebarToggle" href="home">
		<i class="fas fa-bars"></i>
	</button>
	<form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
          <div class="input-group-append">
            <button class="btn btn-primary" type="button">
              <i class="fas fa-search"></i>
            </button>
          </div>
        </div>
      </form>
	<!-- Navbar -->
	<ul class="navbar-nav ml-auto ml-md-0">
		<li class="nav-item dropdown no-arrow mx-1"><a
			class="nav-link dropdown-toggle" href="#" id="alertsDropdown"
			role="button" data-toggle="dropdown" aria-haspopup="true"
			aria-expanded="false"> <i class="fas fa-bell fa-fw"></i> <span
				class="badge badge-danger">9+</span>
		</a>
			<div class="dropdown-menu dropdown-menu-right"
				aria-labelledby="alertsDropdown">
				<a class="dropdown-item" href="#">Action</a> <a
					class="dropdown-item" href="#">Another action</a>
				<div class="dropdown-divider"></div>
				<a class="dropdown-item" href="#">Something else here</a>
			</div>
		</li>
		<li class="nav-item dropdown no-arrow mx-1"><a
			class="nav-link dropdown-toggle" href="#" id="messagesDropdown"
			role="button" data-toggle="dropdown" aria-haspopup="true"
			aria-expanded="false"> <i class="fas fa-envelope fa-fw"></i> <span
				class="badge badge-danger">7</span>
		</a>
			<div class="dropdown-menu dropdown-menu-right"
				aria-labelledby="messagesDropdown">
				<a class="dropdown-item" href="#">Action</a> <a
					class="dropdown-item" href="#">Another action</a>
				<div class="dropdown-divider"></div>
				<a class="dropdown-item" href="#">Something else here</a>
			</div>
		</li>
		<li class="nav-item dropdown no-arrow">
			<a
				class="nav-link dropdown-toggle" href="#" id="userDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <i class="fas fa-user-circle fa-fw"></i>
			</a>
			<c:if test="${ sessionScope.connected }" >
				<div class="dropdown-menu dropdown-menu-right"
					aria-labelledby="userDropdown">
					<a class="dropdown-item" ><b><c:out value="${ sessionScope.msgMenu }"></c:out></b></a>
					<c:if test="${ !empty sessionScope.user_auth.groupe }" >
					<a class="dropdown-item" ><c:out value="${ sessionScope.user_auth.groupe.nomGroupe }"></c:out></a>
					</c:if>
					<c:if test="${ empty sessionScope.user_auth.groupe }" >
					<a class="dropdown-item" >Admin</a>
					</c:if>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item" href="edit-user">Modifier profil</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">Deconnexion</a>
				</div>
			</c:if>	
			<c:if test="${ !sessionScope.connected }" >
				<div class="dropdown-menu dropdown-menu-right"
					aria-labelledby="userDropdown">
					<a class="dropdown-item" href="login">Connexion</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item" href="register">Créer un compte</a>
				</div>
			</c:if>	
		</li>
	</ul>
</nav>
<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Voulez-vous quitter?</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">Cliquez sur Se déconnecter si vous souhaitez vous déconnecter</div>
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">Annuler</button>
				<a class="btn btn-primary" href="logout">Se déconnecter</a>
			</div>
		</div>
	</div>
</div>
<c:if test="${ !empty sessionScope.AlertSuccessMsg  }" >
	<div id="AlertSuccess" style="position: absolute;top:10%;left: 75%;right: 10px;" class="alert alert-success alert-dismissible fade show" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
	    <span aria-hidden="true">&times;</span>
	  </button>
	  <p style="text-align: center;"><c:out value="${ AlertSuccessMsg }"></c:out></p>
	  <% session.removeAttribute("AlertSuccessMsg"); %>
	</div>
</c:if>
<c:if test="${ !empty sessionScope.AlertErrorMsg  }" >
	<div id="AlertError" style="position: absolute;top:10%;left: 75%;right: 10px;" class="alert alert-danger alert-dismissible fade show" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
	    <span aria-hidden="true">&times;</span>
	  </button>
	  <p style="text-align: center;"><c:out value="${ AlertErrorMsg }"></c:out></p>
	  <% session.removeAttribute("AlertErrorMsg"); %>
	</div>
</c:if>
<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin.min.js"></script>
<script src="js/demo/datatables-demo.js"></script>
<script src="js/loadMatieres.js"></script>
<script src="js/auth.js"></script>

<script type="text/javascript">
$('.confirmation').on('click', function () {
    return confirm('Etes-vous sur(e)?');
});
</script>

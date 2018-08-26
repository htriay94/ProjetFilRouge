$( function() {
	function findDoublonEmail(){
		var email = $("#email").val();
		$('#validationEmail').load('CheckDoublonValueUser', {value: email, column: "email"}, function(res) {
				//$("#validationEmail").html(res);
				if($('#emailDispo').css('color') == 'rgb(0, 128, 0)')
					$('#submit').prop("disabled",false);
				else
					$('#submit').prop("disabled",true);
		});
	}
	function findDoublonLogin(){
		var login = $("#login").val();
		$('#validationLogin').load('CheckDoublonValueUser', {value: login, column: "login"}, function(res) {
				//$("#validationLogin").html(res);
				if($('#loginDispo').css('color') == 'rgb(0, 128, 0)')
					$('#submit').prop("disabled",false);
				else
					$('#submit').prop("disabled",true);
		});
	}
	$('#email').keyup(findDoublonEmail);
	$('#login').keyup(findDoublonLogin);
	
});
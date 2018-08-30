$( function() {
	function showMatieresInMenu(){
		console.log('showMatieresInMenu');
		$.ajax({
	 		type: "GET",
	 		url: "GetListMatieresServlet",
	 		data : {},
	 		dataType: "xml",
	 		success: function(matieresXML) {
	 			//console.log('showMatieresInMenu AJAX');
				/* Creation des sous menu Cours > <<Matieres>>*/
	 			var first = true;
	 			$(matieresXML).find('matiere').each(   
                    function()
                    {
                       var id = $(this).attr('id');
                       var nom = $(this).find('nom').text();
                       //console.log('showMatieresInMenu AJAX matiere '+id+" "+nom);
                       var divider = "<div class=\"dropdown-divider\"></div>";
                       var linkMatiere = "<a class=\"dropdown-item\" href=\"liste-cours?id="+id+"&nom="+nom+"\">"+nom+"</a>";
                       if(!first){
                    	   $(".listes-matieres").append(divider);
                       }
                       first = false;
                       $(".listes-matieres").append(linkMatiere);
                     });
            },
	 		error: function(data){
	 	        
	 	    }
	 	});
	}
	showMatieresInMenu();
});
$(function() {
	$(".siteNavigation .active").removeClass("active");
	
    $("form button").button({

    });
    
    $(".button").button({
    	
    });
    
    $("#printButton").button({
    	icons: {
            primary: "ui-icon-print"
        },
        text: false
    });
    
    $.widget("ui.form",{

	    _init:function(){
	    	var object = this;
	    	var form = object.element;

	    	form.find(".date").datetimepicker();
	    	form.find("fieldset").addClass("ui-corner-all");
	    	form.find("input").addClass("ui-corner-all");
	    	form.find("textarea").addClass("ui-corner-all");
	    	form.find("select").addClass("ui-corner-all");
	    	
	    	form.addClass("ui-widget");
	    	
	    }
    });
    
    $("form").form();
});

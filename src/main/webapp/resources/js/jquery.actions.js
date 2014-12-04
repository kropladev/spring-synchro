$(document).ready(
	function(){
		var oTable = $('#example').dataTable( {
	        "bSortClasses": false
	    } );
	     
	    oTable.$('td').hover( function() {
	        var iCol = $('td', this.parentNode).index(this) % 4;
	        var el =$(this);
			el.parent().find('td').addClass('highlighted');
	        $('td:nth-child('+(iCol+1)+')', oTable.$('tr')).addClass( 'highlighted' );
	    }, function() {
	        oTable.$('td.highlighted').removeClass('highlighted');
	    } );
	    
		$( "input[type=submit]" ).button();
		 
	/*	draw_vertical_line(2, '70%', '#084B8A', '10%', 160);*/
	});

function draw_vertical_line(width, height, linecolor, xpos, ypos) {
    $('#line').css({'width':width, 'height':height, 'background-color':linecolor, 'left':xpos, 'top':ypos/*, 'position':'absolute'*/});
}
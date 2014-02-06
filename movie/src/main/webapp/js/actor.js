var actorTable;
$(document).ready(function () {
	$.ajax({
        type: "POST",
        url: "actors/getActors",
        success: function (data) {
            if (data.status == 1) {
            	actorTable = $('#actors').dataTable({
                    "bProcessing": true,
                    "aaData": data.actors,
                    "bJQueryUI": true,
                    "aoColumns": [ 
                      			/* Id */    				{ "bSearchable": false,
									  						  "bSortable" : false,
         			                 						  "bVisible":    false },
                      			/* Name */   	null
                      			]
                });
            	actorTable.fnSort([[1,'asc']]);
            	$('#actors').delegate('tr','click', function() {
                    if ($(this).hasClass('row_selected')) {
                        $("#actorName").val("");
                        $("#actorButtonLabel").html("Add Actor");
                        $("#actorButtonLabel").parent().attr("onclick", "createActor(this)");
                        $("#deleteActor").attr('disabled', 'disabled');
                        $(this).removeClass('row_selected');
                    } else {
                    	var data = actorTable.fnGetData(this);
                    	var aPos = actorTable.fnGetPosition(this);
                        $("#actorName").val(data[1]);
                        $("#actorButtonLabel").html("Update Actor");
                        $("#actorButtonLabel").parent().attr("onclick", "updateActor(this," + aPos +")");
                    	$("#deleteActor").attr("onclick", "confirmNotify('deleteAnActor', 'Are You Sure Delete This Actor?', 'Delete', 'Cancel', 'deleteActor', ['deleteAnActorConfirmButton', 'actors'])");
                        $("#deleteActor").removeAttr('disabled');
                        actorTable.$('tr.row_selected').removeClass('row_selected');
                        $(this).addClass('row_selected');
                    }
                });
            } else if (data.status == -1) {
                notify('actorTableErrorNotification', 'alert-danger', 'Can Not Read Actors!!!', 10000);
            }
        }
    });
});


function deleteActor(button, type) {
	var l = Ladda.create($("#" + button)[0]);
    l.start();
    var selectedRow = fnGetSelected(actorTable);
    var rowData = actorTable.fnGetData(selectedRow);
    var rowNum = actorTable.fnGetPosition(selectedRow);
	if (rowData != null && rowData[0] != null && rowData[0] > 0) {
		$.ajax({
	        type: "POST",
	        data: "id=" + rowData[0],
	        url: "actors/deleteActor",
	        success: function (data) {
	        	l.stop();
	            if (data.status == 1) {
	            	$("#actorName").val("");
	            	$("#actorButtonLabel").html("Add Actor");
                    $("#actorButtonLabel").parent().attr("onclick", "createActor(this)");
	            	actorTable.fnDeleteRow(rowNum);
	            	notify('deleteActorSuccessNotification', 'alert-info', 'Actor Successfully Deleted', 10000);
	            } else if (data.status < 1) {
	            	notify('deleteActorErrorNotification', 'alert-danger', 'Deleting Actor was Failed!!!', 10000);
	            }
	        }
	    });
	}
}

function createActor(button, aPos) {
	var l = Ladda.create(button);
    l.start();
    var name = $("#actorName").val();
    if (!name) {
    	notify('warnNoDataEntredNotification', 'alert-info', 'No Values Entred!!!', 10000);
	} else {
	    $.ajax({
	        type: "POST",
	        data: "name=" + name,
	        url:  "actors/createActor",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	actorTable.fnAddData(
	            				[data.id,
	            	             name]);
	            	$("#actorName").val("");
	            	$("#actorButtonLabel").html("Add Actor");
                    $("#actorButtonLabel").parent().attr("onclick", "createActor(this)");
	            	notify('addActorSuccessNotification', 'alert-info', 'Actor is successfully added.', 10000);
	            } else if (data.status < 1) {
	            	notify('addActorErrorNotification', 'alert-danger', 'Adding Actor was Failed!!!', 10000);
	            }
	        }
	    });
	}
}

function updateActor(button, aPos) {
	var l = Ladda.create(button);
    l.start();
    var rowData = actorTable.fnGetData(aPos);
    var name = $("#actorName").val();
    if (rowData[1] == name) {
    	notify('warnNoDataChangesNotification', 'alert-info', 'No Values Changed!!!', 10000);
	} else {
	    $.ajax({
	        type: "POST",
	        data: "id=" + rowData[0] + "&name=" + name,
	        url:  "actors/updateActor",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	actorTable.fnUpdate(
	            				[rowData[0],
	            	             name], aPos);
	            	notify('updateActorSuccessNotification', 'alert-info', 'Actor is successfully updated.', 10000);
	            } else if (data.status < 1) {
	            	notify('updateActorErrorNotification', 'alert-danger', 'Updating Actor was Failed!!!', 10000);
	            }
	        }
	    });
	}
}
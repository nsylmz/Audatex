var movieTable;
var actors;
var twinSelectContainer;
var movieActors;
$(document).ready(function () {
	$.ajax({
        type: "POST",
        url: "actors/getActors",
        success: function (data) {
        	if (data.status == 1) {
        		actors = data.actors;
        	} else if (data.status == -1) {
        		notify('actorRetrieveErrorNotification', 'alert-danger', 'Can Not Read Actors!!!', 10000);
        	}
        }
	});
	$.ajax({
        type: "POST",
        url: "movies/getMovies",
        success: function (data) {
            if (data.status == 1) {
            	movieTable = $('#movies').dataTable({
                    "bProcessing": true,
                    "aaData": data.movies,
                    "bJQueryUI": true,
                    "aoColumns": [ 
                      			/* Id */    				{ "bSearchable": false,
									  						  "bSortable" : false,
         			                 						  "bVisible":    false },
                      			/* Name */   	null
                      			]
                });
            	movieTable.fnSort([[1,'asc']]);
            	$('#movies').delegate('tr','click', function() {
                    if ($(this).hasClass('row_selected')) {
                        $("#movieName").val("");
                        $("#movieButtonLabel").html("Add Movie");
                        $("#movieButtonLabel").parent().attr("onclick", "createMovie(this)");
                        $("#deleteMovie").attr('disabled', 'disabled');
                        $(this).removeClass('row_selected');
                        
                        resetSelects(select_src, select_dst);
                        disableSelectContainer(twinSelectContainer);
                    } else {
                    	var data = movieTable.fnGetData(this);
                    	var aPos = movieTable.fnGetPosition(this);
                        $("#movieName").val(data[1]);
                        $("#movieButtonLabel").html("Update Movie");
                        $("#movieButtonLabel").parent().attr("onclick", "updateMovie(this," + aPos +")");
                    	$("#deleteMovie").attr("onclick", "confirmNotify('deleteAnMovie', 'Are You Sure Delete This Movie?', 'Delete', 'Cancel', 'deleteMovie', ['deleteAnMovieConfirmButton', 'movies'])");
                        $("#deleteMovie").removeAttr('disabled');
                        movieTable.$('tr.row_selected').removeClass('row_selected');
                        $(this).addClass('row_selected');
                        
                        $.ajax({
                            type: "POST",
                            data: "id=" + data[0],
                            url: "movies/getMovieActors",
                            success: function (data) {
                            	if (data.status == 1) {
                            		movieActors = data.movieActors;
                            		resetSelects(select_src, select_dst);
                                	enableSelectContainer(twinSelectContainer);
                                	var actorId;
                                	var elements = [];
                                	for (var i in movieActors) {
                                		actorId = movieActors[i][0];
                                		elements.push(twinSelectContainer.find('option[value=' + actorId +']')[0]);
            						}
                                	addElementsToDest(select_src, select_dst, $(elements));
                            	} else if (data.status == -1) {
                            		notify('actorRetrieveErrorNotification', 'alert-danger', 'Can Not Read Actors!!!', 10000);
                            	}
                            }
                    	});
                        $("#saveMovieActors").attr("onclick", "saveMovieActors('" + data[0] + "')");
                    }
                });
            	
            	twinSelectContainer = $('#movieActorSelect').tandemSelect();
            	twinSelectContainer.attr("disabled", "disabled");
            	var select_src = twinSelectContainer.find('.tandem-select-src-select');
            	var select_dst = twinSelectContainer.find('.tandem-select-dst-select');
            	var templateOpt = select_src.children().first().clone();
            	select_src.children().first().remove();
            	var newOpt;
            	var actor;
            	for (var i in actors) {
            		actor = actors[i];
            		newOpt = templateOpt.clone();
            		newOpt.val(actor[0]);
            		newOpt.text(actor[1]);
            		select_src.append(newOpt);
				}
            	disableSelectContainer(twinSelectContainer);
            } else if (data.status == -1) {
                notify('movieTableErrorNotification', 'alert-danger', 'Can Not Read Movies!!!', 10000);
            }
        }
    });
});

function saveMovieActors(movieId) {
    var newMovieActors = [];
    var removedMovieActors = [];
    var select_dst = twinSelectContainer.find('.tandem-select-dst-select');
    select_dst.children().each(function () {
    	var add = true;
    	for (var i in movieActors) {
    		if (movieActors[i][0] == $(this).val()) {
    			add = false;
			}
		}
    	if (add) {
    		newMovieActors.push($(this).val());
		}
    });
    for (var i in movieActors) {
    	if(select_dst.find('option[value=' + movieActors[i][0] +']').length == 0) {
    		removedMovieActors.push(movieActors[i][0]);
    	}
	}
    var newMovieActorIds;
    for (var i in newMovieActors) {
    	if (i != 0) {
    		newMovieActorIds = newMovieActorIds + "-" + newMovieActors[i];
		} else {
			newMovieActorIds = newMovieActors[i];
		}
	}
    var removedMovieActorIds;
    for (var i in removedMovieActors) {
    	if (i != 0) {
    		removedMovieActorIds = removedMovieActorIds + "-" + removedMovieActors[i];
		} else {
			removedMovieActorIds = removedMovieActors[i];
		}
	}
    $.ajax({
        type: "POST",
        data: "movieId=" + movieId + "&newMovieActorIds=" + newMovieActorIds + "&removedMovieActorIds=" + removedMovieActorIds,
        url: "movies/saveMovieActors",
        success: function (data) {
            if (data.status == 1) {
            	for (var i in removedMovieActors) {
            		movieActors.splice(movieActors.indexOf(removedMovieActors[i]), 1);
            	}
            	for (var i in newMovieActors) {
            		movieActors.push(newMovieActors[i]);
            	}
            	notify('saveSubCategoriesSuccessNotification', 'alert-info', 'Ana Kategori Başarı İle Güncellendi.', 10000);
            } else if (data.status < 1) {
            	notify('saveSubCategoriesErrorNotification', 'alert-danger', 'Ana Kategori Güncellenirken Hata Alındı!!!', 10000);
            }
        }
    });
}


function deleteMovie(button, type) {
	var l = Ladda.create($("#" + button)[0]);
    l.start();
    var selectedRow = fnGetSelected(movieTable);
    var rowData = movieTable.fnGetData(selectedRow);
    var rowNum = movieTable.fnGetPosition(selectedRow);
	if (rowData != null && rowData[0] != null && rowData[0] > 0) {
		$.ajax({
	        type: "POST",
	        data: "id=" + rowData[0],
	        url: "movies/deleteMovie",
	        success: function (data) {
	        	l.stop();
	            if (data.status == 1) {
	            	$("#movieName").val("");
	            	$("#movieButtonLabel").html("Add Movie");
                    $("#movieButtonLabel").parent().attr("onclick", "createMovie(this)");
	            	movieTable.fnDeleteRow(rowNum);
	            	notify('deleteMovieSuccessNotification', 'alert-info', 'Movie Successfully Deleted', 10000);
	            } else if (data.status < 1) {
	            	notify('deleteMovieErrorNotification', 'alert-danger', 'Deleting Movie was Failed!!!', 10000);
	            }
	        }
	    });
	}
}

function createMovie(button, aPos) {
	var l = Ladda.create(button);
    l.start();
    var name = $("#movieName").val();
    if (!name) {
    	notify('warnNoDataEntredNotification', 'alert-info', 'No Values Entred!!!', 10000);
	} else {
	    $.ajax({
	        type: "POST",
	        data: "name=" + name,
	        url:  "movies/createMovie",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	movieTable.fnAddData(
	            				[data.id,
	            	             name]);
	            	$("#movieName").val("");
	            	$("#movieButtonLabel").html("Add Movie");
                    $("#movieButtonLabel").parent().attr("onclick", "createMovie(this)");
	            	notify('addMovieSuccessNotification', 'alert-info', 'Movie is successfully added.', 10000);
	            } else if (data.status < 1) {
	            	notify('addMovieErrorNotification', 'alert-danger', 'Adding Movie was Failed!!!', 10000);
	            }
	        }
	    });
	}
}

function updateMovie(button, aPos) {
	var l = Ladda.create(button);
    l.start();
    var rowData = movieTable.fnGetData(aPos);
    var name = $("#movieName").val();
    if (rowData[1] == name) {
    	notify('warnNoDataChangesNotification', 'alert-info', 'No Values Changed!!!', 10000);
	} else {
	    $.ajax({
	        type: "POST",
	        data: "id=" + rowData[0] + "&name=" + name,
	        url:  "movies/updateMovie",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	movieTable.fnUpdate(
	            				[rowData[0],
	            	             name], aPos);
	            	notify('updateMovieSuccessNotification', 'alert-info', 'Movie is successfully updated.', 10000);
	            } else if (data.status < 1) {
	            	notify('updateMovieErrorNotification', 'alert-danger', 'Updating Movie was Failed!!!', 10000);
	            }
	        }
	    });
	}
}
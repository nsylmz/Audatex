<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="container movie-container">
	<div class="create-movie">
		<div class="create-movie-name-container">
			<div class="create-movie-name">
				<label>Movie Name</label> 
				<input id="movieName" type="text" placeholder="Please Enter An Movie Name">
			</div>
		</div>
		<div class="add-movie-button-container">
			<button class="movie-button ladda-button" data-style="expand-right"
				onclick="createMovie(this)">
				<span id="movieButtonLabel" class="ladda-label">Add Movie</span>
			</button>
			<button id="deleteMovie" class="movie-button ladda-button"
				data-style="expand-right" disabled="disabled"
				onclick="deleteMovie(this)">
				<span class="ladda-label">Delete Movie</span>
			</button>
		</div>
	</div>
	<table cellpadding="0" cellspacing="0" border="0" class="display"
		id="movies">
		<thead>
			<tr width="100%">
				<th width="0%">id</th>
				<th width="100%">Movie Name</th>
			</tr>
		</thead>
		<tbody></tbody>
		<tfoot>
			<tr>
				<th>id</th>
				<th>Movie Name</th>
			</tr>
		</tfoot>
	</table>

	<div class='tandem-select-container'>
		<div class='tandem-select-src-div'>
			Available Actor List <select multiple='multiple'
				class='tandem-select-src-select'>
				<option id="src-template-option" value=""></option>
			</select>
		</div>
		<div class='tandem-select-controls-div'>
			<br />
			<br />
			<br /> <input type="button" class="tandem-select-move-to-src"
				value="&nbsp;&lt;&nbsp;" /> &nbsp; &nbsp; <input type="button"
				class="tandem-select-move-to-dst" value="&nbsp;&gt;&nbsp;" /> <br />
			<br /> Search <br /> <input type="text" class="tandem-select-search-src"
				size="15" /> <br />
			<br /> <input id="saveMovieActors" type="button" value="Save">
		</div>
		<div class='tandem-select-dst-div'>
			Movie Actor List <select multiple='multiple'
				class='tandem-select-dst-select' id='movieActorSelect'
				name='movieActorSelect'>

			</select>
		</div>
	</div>
</div>

<script src="${pageContext.request.contextPath}/js/movie.js" type="text/javascript"></script>
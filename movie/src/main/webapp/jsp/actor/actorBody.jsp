<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container actors-container">
	<div class="create-actor">
		<div class="create-actor-name-container">
			<div class="create-actor-name">
				<label>Actor Name</label> 
				<input id="actorName" type="text" placeholder="Please Enter An Actor Name">
			</div>
		</div>
		<div class="add-actor-button-container">
			<button class="movie-button ladda-button" data-style="expand-right"
				onclick="createActor(this)">
				<span id="actorButtonLabel" class="ladda-label">Add Actor</span>
			</button>
			<button id="deleteActor" class="movie-button ladda-button"
				data-style="expand-right" disabled="disabled"
				onclick="deleteActor(this)">
				<span class="ladda-label">Delete Actor</span>
			</button>
		</div>
	</div>
	<table cellpadding="0" cellspacing="0" border="0" class="display"
		id="actors">
		<thead>
			<tr width="100%">
				<th width="0%">id</th>
				<th width="100%">Actor Name</th>
			</tr>
		</thead>
		<tbody></tbody>
		<tfoot>
			<tr>
				<th>id</th>
				<th>Actor Name</th>
			</tr>
		</tfoot>
	</table>
</div>

<script src="${pageContext.request.contextPath}/js/actor.js" type="text/javascript"></script>
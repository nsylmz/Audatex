<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<link rel="shortcut icon" href="docs-assets/ico/favicon.png">
		
		<title>Movie Application</title>
		<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/ladda.min.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/ladda-themeless.min.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/tandem_select.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
		
		<script src="${pageContext.request.contextPath}/js/jquery-2.0.3.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/holder.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/ladda/spin.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/ladda/ladda.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery-ui.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/ComboBox.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/tandem_select.js"></script>
		<script src="${pageContext.request.contextPath}/js/main.js" type="text/javascript"></script>
	</head>
	<body>
		<div id="notificationContainer" class="notification-container">
		    <div id="temp-notify" class="alert movie-notification">
		        <button type="button" class="close notification-close" aria-hidden="true"
		                onclick="closeNotification()">&times;</button>
		    </div>
			<div id="temp-confirm" class="alert confirm-notification">
			    <div class="confirm-text-container">
			    </div>
				<div id="temp-confirm" class="confirm-button-container">
					<button class="confirm-button btn btn-primary ladda-button" data-style="expand-right">
						<span class="ladda-label"></span>
					</button>
					<button class="cancel-button btn btn-primary ladda-button" data-style="expand-right">
						<span class="ladda-label"></span>
					</button>
				</div>
			</div>
		</div>

		<table class="body-table" border="0" cellspacing="0" cellpadding="0">
	        <tr class="body-table-tr">
	            <td class="body-table-td">
	            	<tiles:insertAttribute name="header"/>
	            </td>
	        </tr>
	        <tr class="body-table-tr">
	            <td class="body-table-td">
	            	<tiles:insertAttribute name="body"/>
	            </td>
	        </tr>
	        <tr class="body-table-tr">
	            <td class="body-table-td">
	            	<tiles:insertAttribute name="footer"/>
	            </td>
	        </tr>
	    </table>
	</body>
</html>
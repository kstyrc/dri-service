<%@page import="vphshare.driservice.notification.domain.Notification"%>
<%@page import="java.text.DateFormat"%>
<%@page import="vphshare.driservice.notification.NotificationHolder"%>
<%@page import="vphshare.driservice.notification.domain.ValidationStatus"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>DRI Notification Service</title>
    <style type="text/css">
        body { 
        	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif; 
        	font-size:0.8em;
        }
        #main {
        	width: 70%;
        	margin: 0px auto;
        }
        #header {
        	width: 70%;
        	margin: 0px auto;
        }
        #header h1 {
        	margin: 10px auto;
        }
        #report { 
        	border-collapse:collapse;
        	width:70%;
        	margin: 0px auto;
        }
        #report td, #report th {
        	font-size: 1em;
        	border-bottom: 1px solid #2E2E2E;
        	padding: 3px 7px 2px 7px;
        }
        #report th {
        	font-size: medium;
        	font-weight: bold;
        	text-align: left;
        	padding-top: 5px;
        	padding-bottom: 5px;
			background-color: #2E2E2E;
			color: #BCE27F;
        }
        #report tr.content td {
        	color: #000000;
        	background-color: #F4F4F4;
        }
        #report tr.row td {
        	color: #000000;
        	background-color: #D9D9D9;
        }
        #report tr.row:hover td {
        	
        	cursor: pointer;
        	background: #2E2E2E;
        	color: #BCE27F;
        }
        #report div.arrow { 
        	background:transparent url(img/arrows.png) no-repeat scroll 0px -16px; 
        	width:16px; 
        	height:16px; 
        	display:block;
        }
        #report img { 
        	float:right;
        }
        #report ul { 
        	margin:10px 0 10px 40px; 
        	padding:0px;
        }
        #entries {
        	width:100%;
        	margin: 0px auto;
        }
        #entries td, #entries th {
        	font-size: small;
        	border-bottom: 1px solid #2E2E2E;
        	padding: 3px 7px 2px 7px;
        }
        #entries th {
        	font-size: small;
        	font-weight: bold;
        	text-align: left;
        	padding-top: 2px;
        	padding-bottom: 2px;
			background-color: #D9D9D9;
			color: black;
        }
        #entries tr, #entries td {
        	color: #000000;
        	background-color: #D9D9D9;
        }
        #entries tr.valid-row td {
        	background-color: green;
        	color: #FFFFFF;
        	font-weight: bold;
        }
        #entries tr.invalid-row td {
        	background-color: #D00000;
        	color: #FFFFFF;
        	font-weight: bold;
        }
        #entries tr:hover td {
        	cursor: pointer;
        	background: #2E2E2E;
        	color: #BCE27F;
        }
    </style>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">  
        $(document).ready(function(){
            $("#report tr.row").addClass("odd");
            $("#report tr.content").hide();
            //$("#report tr:first-child").show();
            //$("#entries tr").removeClass("odd");
            //$("#entries tr").show();
            
            $("#report tr.odd").click(function(){
                $(this).next("tr").toggle();
                $(this).find(".arrow").toggleClass("up");
            });
            $("#report").jExpand();
        });
    </script>        
</head>
<body>
<%
	List<Notification> notifications = new NotificationHolder().getNotifications();
%>
	<div id="main">
		<div id="header">
			<h1>DRI Notification Service</h1>
	    </div>
	    
	    <table id="report">
	        <tr>
	            <th>Dataset name</th>
	            <th>Notification status</th>
	            <th>Execution time</th>
	            <th>Time scheduled</th>
	            <th></th>
	        </tr>
	        <% for (Notification notification : notifications) { %>
		        <tr class="row">
		            <td><%= notification.getDataset().getName() %></td>
		            <td><%= notification.getTitle() %></td>
		            <td><%= notification.getDuration() + "s" %></td>
		            <td><%= DateFormat.getInstance().format(notification.getDate()) %></td>
		            <td><div class="arrow"></div></td>
		        </tr>
		        <tr class="content">
		            <td colspan="5">
		            	<%= notification.getContent() %>
		            	<% if (notification.getEntryLogs() != null && notification.getEntryLogs().size() > 0) { %>
			            	<table id="entries">
			            		<tr>
			            			<th>Logical data identifier</th>
			            			<th>Integrity status</th>
			            		</tr>
			            		<% for (Entry<String, ValidationStatus> entry : notification.getEntryLogs().entrySet()) { %>
			            			<% if (entry.getValue().isValid()) { %>
			            				<tr class="valid-row">
			            			<% } else { %>
			            				<tr class="invalid-row">
			            			<% } %>
			            				<td> <%= entry.getKey().substring(0, Math.min(entry.getKey().length(), 50)) %> </td>
			            				<td> <%= entry.getValue().toString() %> </td>
			            			</tr>
			            		<% } %>
			            	</table>
			            <% } %>
		            </td>
		        </tr>
		    <% } %>
	    </table>
	</div>
</body>
</html>
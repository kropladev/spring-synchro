<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/includes.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="./resources/css/style.css" />
		<link rel="stylesheet" type="text/css" href="./resources/css/jquery-ui-1.10.4.custom.css" />
		<link rel="stylesheet" type="text/css" href="./resources/css/demo_table.css" />
		
		<script type="text/javascript" src="./resources/js/jquery-1.10.2.js"></script>
		<script type="text/javascript" src="./resources/js/jquery-ui-1.10.4.custom.js"></script>
		
		<script type="text/javascript" src="./resources/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="./resources/js/jquery.actions.js"></script>
		
		<title>Synchro config</title>
	</head>
	<body>
		<table class="header" cellpadding="0" cellspacing="0">
			<tr>
				<td id="nordeaLogo"><img src="./resources/img/toplogo.gif" align="right"></td>
				<td class="headerTitle">Bank Processes Support System</td>
			</tr>
		</table>
	
		<div id="container" class="container">
			<div id="menu" class="leftFloat">
				<h4>Menu</h4>
			</div>
			<div id="line" class="leftFloat"></div>
			<div id="main" class="leftFloat">
				<div id="propsEdit" class="leftFloat" >
					<h1>Synchro Properties data</h1>
					<fieldset class="panels"> <legend>Manage single property</legend>
					<form:form action="config.htm" method="POST" commandName="prop">
						<table class="noBorderTable">
							<tr>
								<td>Properties ID</td>
								<td><form:input path="id" /></td>
							</tr>
							<tr>
								<td>Properties name</td>
								<td><form:input path="name" /></td>
							</tr>
							<tr>
								<td>Properties value</td>
								<td><form:input path="value" /></td>
							</tr>
							<tr>
								<td>Properties description</td>
								<td><form:input path="descriptionString" /></td>
							</tr>
							
						</table>
		
						<div id="buttonset">
							<input type="submit" name="action" value="add" class="dataInput,buttonDefault" /> 
							<input type="submit" name="action" value="edit" class="dataInput,buttonDefault" />
							<input type="submit" name="action" value="delete" class="dataInput,buttonDefault" /> 
							<input type="submit" name="action" value="search" class="dataInput,buttonDefault" />
							<!-- <input type="submit" name="action" value="run" class="dataInput,buttonDefault" /> -->
						</div>
					</form:form>
					</fieldset>
				</div>
				
				<div id="manualRunSynchro" class="leftFloat">
				<fieldset  class="panels">
				 <legend>Manual run synchronisation process for country</legend>
					<form  action="runSynchro.htm" method="GET" >
	 					 Select a country: 
							<select name="countryId">
							  <option  value="1">Poland</option>
							  <option  value="2">Lithuania</option>
							   <option value="3">Latvia</option>
							   <option value="4">Estonia</option>
							</select>

	  					<input type="submit" class="dataInput,buttonDefault" id="manualRunSynchroSubmit" value="run" name="action"/>
					</form>
				</fieldset>
				
				</div>
				
				<div id="dirFileUpload" class="leftFloat">
				<fieldset  class="panels">
				 <legend>Upload BPM engine directory.xml file</legend>
					<form:form modelAttribute="uploadedFile" action="fileUpload.htm" method="POST" enctype="multipart/form-data">
	 					 Select a file: <input type="file" name="file">
	  					<input type="submit" class="dataInput,buttonDefault" id="dirFileSubmit" value="Upload"/>
					</form:form>
				</fieldset>
				<br />

				<div id="tableContainer" class="leftFloat">
				<fieldset  class="panels"><legend>List of all properties</legend>
					<table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
						<thead>
							<tr>
								<th>ID</th>
								<th>Name</th>
								<th>Value</th>
								<th>Description</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${propList}" var="prop" varStatus="counter">
								<tr>
									<td>${prop.id}</td>
									<td>${prop.name}</td>
									<td>${prop.value}</td>
									<td>${prop.descriptionString}</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>ID</th>
								<th>Name</th>
								<th>Value</th>
								<th>Description</th>
							</tr>
						</tfoot>
					</table>
					</fieldset>
					<br />

				</div>
			</div>
			</div>
		</div>
	</body>
</html>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<html>
	<head>
	<html:base/>
		<title>
			<bean:message key="appName"/>
		</title>
	</head>
	<body bgcolor="#ffffff" text="#000000" link="#023264" alink="#023264" vlink="#023264">
	<center>
	<div style='whidth:400; margin-left:auto; margin-right:auto;'>
		<h2>Cadastro de usuario</h2>
			Entre com seus dados:
		<html:form action="cadastrar.do" method="post" onsubmit="return validateCadastrarForm(this);">
			<table border="1" cellspacing="5">
				<tr>
					<td>
						<bean:message key="user.login"/>:
					</td>
					<td>
						<html:text property="login"/>
					</td>
				</tr>
				<tr>
					<td>
						<bean:message key="user.pass"/>:
					</td>
					<td>
						<html:password property="senha"/>
					</td>
				</tr>
				
				<tr>
					<td>
						<bean:message key="user.name"/>:
					</td>
					<td>
						<html:text property="nome"/>
					</td>
				</tr>
				<tr>
					<td>
						<bean:message key="user.card"/>:
					</td>
					<td>
						<html:text property="cartao"/>
					</td>
				</tr>
				<tr>
					<td>
						<bean:message key="user.email"/>:
					</td>
					<td>
						<html:text property="email"/>
					</td>
				</tr>
				<tr>
					<td>
						<bean:message key="user.fone"/>:
					</td>
					<td>
						<html:text property="telefone"/>
					</td>
				</tr>																
			</table>
				<html:submit/>
			<html:javascript formName="cadastrarForm"/>				
		</html:form>	
		<html:errors/>
	</div>	
	<center>
	</body>
</html>

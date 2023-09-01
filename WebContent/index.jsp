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
		<h2><bean:message key="appName"/></h2>
			<bean:message key="user.login_password" />
		<html:form action="login.do" method="post" onsubmit="return validateLoginForm(this);">
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
						<html:password property="password"/>
					</td>
				</tr>
			</table>
				<html:submit/>
				<html:javascript formName="loginForm"/>
		</html:form>	
			<bean:message key="link.cadastro"/>
		<html:errors/>
	</div>	
	<center>
	</body>
</html>

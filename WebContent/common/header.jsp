<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<table height="5%" width="100%">
  <tr colspam="2">
   <td>
	<a href="http://jakarta.apache.org">
	   <img src="${pageContext.request.contextPath}/images/jakarta-logo.gif" align="left" border="0">
	</a>
	  <img src="${pageContext.request.contextPath}/images/struts.gif" align="right" border="0">
	</td>
  </tr>
   <tr>
      <td aling="left">	
		  Usuario: ${Usuario.nome}
	  </td>
      <td aling="right">	
		<html:link action="LogOff"> Sair </html:link>
	  </td>
   </tr>
</table>
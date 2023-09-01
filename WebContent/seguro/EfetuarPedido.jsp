<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
	<div style='whidth:400; margin-left:auto; margin-right:auto;'>
		<h2>Efetuar Pedido</h2>
		<html:form action="EfetuarPedido.do" method="post" onsubmit="return validateEfetuarPedidoForm(this);">
			<table>
				<tr>
					<td>
						<bean:message key="pedido.rua"/>:
					</td>
					<td>
						<html:text property="rua"/>
					</td>
				</tr>
				<tr>
					<td>
						<bean:message key="pedido.numero"/>:
					</td>
					<td>
						<html:text property="numero"/>
					</td>
				</tr>
				<tr>
					<td>
						<bean:message key="pedido.cidade"/>:
					</td>
					<td>
						<html:text property="cidade"/>
					</td>
				</tr>														
				<tr>
					<td>
						<bean:message key="pedido.estado"/>:
					</td>
					<td>
						<html:text property="estado"/>
					</td>
				</tr>
				<tr>
					<td>
						<bean:message key="pedido.cep"/>:
					</td>
					<td>
						<html:text property="cep"/>
					</td>
				</tr>								
			</table>
			<html:submit/>
			<html:javascript formName="EfetuarPedidoForm"/>				
		</html:form>
		${erro}	
		<html:errors/>
	</div>	

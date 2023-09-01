<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
	<div style='whidth:400; margin-left:auto; margin-right:auto;'>
		<h2>Cadastro de Categoria</h2>
		<html:form action="CadastrarCategoria.do" method="post" onsubmit="return validateCadastrarCategoriaForm(this);">
			<table>
				<tr>
					<td>
						<bean:message key="categoria.nome"/>:
					</td>
					<td>
						<html:text property="nome"/>
					</td>
				</tr>
				<tr>
					<td>
						<bean:message key="categoria.descricao"/>:
					</td>
					<td>
						<html:text property="descricao"/>
					</td>
				</tr>
														
			</table>
			<html:submit/>
			<html:javascript formName="CadastrarCategoriaForm"/>				
		</html:form>	
		<html:errors/>
		<logic:present name="categorias">
			<table border="1"  height="50%" width="50%" cellspacing="5">
				<tr>
					<td align=center>Nome</td>
					<td align=center>Descricação</td>
				</tr>
				<logic:iterate id="bean" name="categorias">
				<tr >
					<td>
						<bean:write name="bean" property="nome"/>
					</td>
					<td >
						<bean:write name="bean" property="descricao"/>
					</td>
				</tr>
				</logic:iterate>
			</table>
		</logic:present>
	</div>	

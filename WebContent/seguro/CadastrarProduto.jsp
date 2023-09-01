<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<div style='whidth: 400; margin-left: auto; margin-right: auto;'>
	<h2>Cadastro de Produto</h2>
	<html:form action="CadastrarProduto.do" method="post"
		onsubmit="return validateCadastrarProdutoForm(this);">
		<table>
			<tr>
				<td><bean:message key="produto.nome" />:</td>
				<td><html:text property="nome" /></td>
			</tr>
			<tr>
				<td><bean:message key="produto.descricao" />:</td>
				<td><html:text property="descricao" /></td>
			</tr>
			<tr>
				<td><bean:message key="produto.cor" />:</td>
				<td><html:text property="cor" /></td>
			</tr>
			<tr>
				<td><bean:message key="produto.peso" />:</td>
				<td><html:text property="peso" /></td>
			</tr>
			<tr>
				<td><bean:message key="produto.preco" />:</td>
				<td><html:text property="preco" /></td>
			</tr>
		</table>
		<html:submit property="method" value="salvar" />
		<html:javascript formName="CadastrarProdutoForm" />
	</html:form>
	<html:errors />
	<logic:present name="produtos">
		<table border="1" height="50%" width="50%" cellspacing="2">
			<tr>
				<td align=center>Nome</td>
				<td align=center>Descricação</td>
				<td align=center>Cor</td>
				<td align=center>Ppreco</td>
				<td align=center>Peso</td>
			</tr>
			<logic:iterate id="bean" name="produtos">
				<tr>
					<td><bean:write name="bean" property="nome" /></td>
					<td><bean:write name="bean" property="descricao" /></td>
					<td style="background-color: #${bean.cor}"><bean:write
							name="bean" property="cor" /></td>
					<td><bean:write name="bean" property="preco" /></td>
					<td><bean:write name="bean" property="peso" /></td>
				</tr>
			</logic:iterate>
		</table>
	</logic:present>
</div>


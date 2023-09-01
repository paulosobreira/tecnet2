<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
	<div style='whidth:400; margin-left:auto; margin-right:auto;'>
		<h2>Cadastro de Produto/Categoria</h2>
		<html:form action="CadastrarProdutoCategoria.do" 
		method="post" onsubmit="return validateCadastrarProdutoCategoriaForm(this);">
			<table>
				<tr>
					<td>
						<bean:message key="produto.nome"/>:
					</td>
					<td>
                        <html:select property="produto"> 
						 	<html:options collection="produtos"
								property="id" labelProperty="nome"/> 
						</html:select>
					</td>
				</tr>
				<tr>
					<td>
						<bean:message key="categoria.nome"/>:
					</td>
					<td>
                        <html:select property="categoria"> 
						 	<html:options collection="categorias"
								property="id" labelProperty="nome"/> 
						</html:select>
					</td>
				</tr>
																
			</table>
			<html:submit property="method" value="salvar"/>
			<html:javascript formName="CadastrarProdutoForm"/>				
		</html:form>	
		<html:errors/>
		<logic:present name="produtoscategorias">
			<table border="1"  height="50%" width="50%" cellspacing="5">
				<tr>
					<td align=center>Produto</td>
					<td align=center>Categoria</td>
				</tr>
				<logic:iterate id="bean" name="produtoscategorias">
				<tr >
					<td>
						<bean:write name="bean" property="produto"/>
					</td>
					<td>
						<bean:write name="bean" property="categoria"/>
					</td>
				</tr>
				</logic:iterate>
			</table>
		</logic:present>
	</div>	

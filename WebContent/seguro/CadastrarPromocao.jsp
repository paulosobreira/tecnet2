<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
	<div style='whidth:400; margin-left:auto; margin-right:auto;'>
		<h2>Cadastro de Promoções</h2>
		<html:form action="CadastrarPromocao.do" 
		method="post" onsubmit="return validateCadastrarPromocaoForm(this);">
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
						<bean:message key="produto.percentual"/>:
					</td>
					<td>
                        <html:select property="percentual"> 
						 	<html:option value="5" > 5% </html:option>
						 	<html:option value="10" > 10% </html:option>
						 	<html:option value="20" > 20% </html:option>
						 	<html:option value="30" > 30% </html:option>
						 	<html:option value="40" > 40% </html:option>						 							 							 	
						</html:select>					
					</td>
				</tr>
																
			</table>
			<html:submit property="method" value="salvar"/>
			<html:javascript formName="CadastrarPromocaoForm"/>				
		</html:form>	
		<html:errors/>
		<logic:present name="promocoes">
			<table border="1"  height="50%" width="50%" cellspacing="5">
				<tr>
					<td align=center>Produto</td>
					<td align=center>Preco</td>
					<td align=center>Percentual desconto</td>
  					<td align=center>Preco com desconto</td>
					<td align=center>Remover</td>
				</tr>
				<logic:iterate id="bean" name="promocoes">
				<tr >
					<td>
						<bean:write name="bean" property="nome"/>
					</td>
					<td>
						<bean:write name="bean" property="preco"/>
					</td>					
					<td>
						<bean:write name="bean" property="percentual"/>%
					</td>
					<td>
						${bean.preco -(bean.preco*bean.percentual/100)}
					</td>					
					<td align="center">
					  <a href="CadastrarPromocao.do?method=remover&produtoId=${bean.id}">X</a>
					</td>						
				</tr>
				</logic:iterate>
			</table>
		</logic:present>
	</div>	

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
	<div style='whidth:400; margin-left:auto; margin-right:auto;'>
		<h2>Carrinho de Compras</h2>
		<html:form action="Carrinho.do" method="post">
		<logic:present name="lista_produtos">
			<table border="1"  height="50%" width="50%" cellspacing="5">
				<tr>
					<td align="center">Nome</td>
					<td align="center">Descricação</td>
					<td align="center">Cor</td>
					<td align="center">Peso</td>					
					<td align="center">Preco</td>
					<td align="center">Preco com Desconto</td>
					<td align="center">Quantidade</td>					
					<td align="center">Preco Total</td>										
					<td align="center">Remover</td>					
				</tr>
				<logic:iterate id="bean" name="lista_produtos">
				<tr >
					<td>
						<bean:write name="bean" property="nome"/>
					</td>
					<td>
						<bean:write name="bean" property="descricao"/>
					</td>
					<td style="background-color:#${bean.cor}">
						<bean:write name="bean" property="cor"/>
					</td>
					<td>
						<bean:write name="bean" property="peso"/>
					</td>
					<td>
						<bean:write name="bean" property="preco"/>
					</td>															
					<td>
				 		${bean.preco -(bean.preco*bean.percentual/100)}
					</td>						
					<td>
					   <input type="text" size="5" name="prod_${bean.produto_id}" value="${bean.qtde_produto}" />
					</td>
					<td>
						<bean:write name="bean" property="preco_total"/>
					</td>						
					<td align="center">
					  <a href="Carrinho.do?method=remover&produtoId=${bean.produto_id}">X</a>
					</td>															
				</tr>
				</logic:iterate>
			</table>
			<p>
			<b>Peso Total:</b> ${requestScope.pesoTotal} <b>Preco Total:</b> ${requestScope.precoTotal}
			</p>
		</logic:present>



		<html:submit property="method" value="atualizar"/>
		</html:form>	
		<html:errors/>
	</div>	

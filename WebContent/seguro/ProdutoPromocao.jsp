<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
	<div style='whidth:400; margin-left:auto; margin-right:auto;'>
		<h2>Produtos em promoção</h2>
		<logic:present name="produtos">
			<table border="1"  height="50%" width="50%" cellspacing="5">
				<tr>
					<td align="center">Nome</td>
					<td align="center">Descricação</td>
					<td align="center">Cor</td>
					<td align="center">Preco</td>
					<td align="center">Preco com desconto</td>
					<td align="center">Peso</td>					
					<td align="center">Adcionar Carrinho</td>					
				</tr>
				<logic:iterate id="bean" name="produtos">
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
						<bean:write name="bean" property="preco"/>
					</td>
					<td>
				 		${bean.preco -(bean.preco*bean.percentual/100)}
					</td>					
					<td>
						<bean:write name="bean" property="peso"/>
					</td>															
					<td align="center">
					  <a href="Carrinho.do?method=adicionar&produtoId=${bean.produto}">X</a>
					</td>															
					
				</tr>
				</logic:iterate>
			</table>
		</logic:present>
	</div>	

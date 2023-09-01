<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

	<div style='whidth:400; margin-left:auto; margin-right:auto;'>
		<h2>Listar Pedidos</h2>
		<logic:present name="pedidos">
			<table border="1"  height="50%" width="70%" cellspacing="2">
				<tr>
					<td align="center">Cliente</td>
					<td align="center">Estado</td>
					<td align="center">Cidade</td>
					<td align="center">Valor pedido</td>
				</tr>
				<logic:iterate id="bean" name="pedidos">
				<tr >
					<td>
						<bean:write name="bean" property="nome"/>
					</td>
					<td>
						<bean:write name="bean" property="estado"/>
					</td>
					<td>
						<bean:write name="bean" property="cidade"/>
					</td>
					<td>
						TODO
					</td>					
				</tr>
				<tr >
					<td colspan="4" align="right">
					
						<table border="1"  height="50%" width="90%" cellspacing="2">
							<tr>
								<td align="center">Nome</td>
								<td align="center">Peso</td>					
								<td align="center">Preco</td>
								<td align="center">Preco com Desconto</td>
								<td align="center">Quantidade</td>					
								<td align="center">Preco Total</td>										
							</tr>
						<logic:iterate id="innerBean" name="bean" property="listaProdutos">
						<tr >
							<td>
								<bean:write name="innerBean" property="nome"/>
							</td>	
							<td>
								<bean:write name="innerBean" property="peso"/>
							</td>																			
							<td>
								<bean:write name="innerBean" property="preco"/>
							</td>
							<td>
						 		${innerBean.preco -(innerBean.preco*innerBean.percentual/100)}
							</td>
							<td>
								<bean:write name="innerBean" property="qtde_produto"/>
							</td>							
							<td>
								<bean:write name="innerBean" property="preco_total"/>
							</td>																	
						</logic:iterate>					
						</table>
     				
					</td>	
				</tr>
				</logic:iterate>
			</table>
		</logic:present>
	</div>	

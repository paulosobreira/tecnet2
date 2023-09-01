<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<html>
  <head>
    <title><tiles:getAsString name="title"/></title>
    <link rel="STYLESHEET" type="text/css" href="seguro/cores/default.css">
  </head>

<body bgcolor="#ffffff" text="#000000" link="#023264" alink="#023264" vlink="#023264">
<table border="1"  height="100%" width="100%" cellspacing="3">
<tr height="15%">
  <td colspan="2"><tiles:insert attribute="header" /></td>
</tr>
<tr>
  <td width="15%" valign="top">
    <tiles:insert attribute='menu'/>
  </td>
  <td valign="top"  align="left">
    <tiles:insert attribute='body' />
  </td>
</tr>
<tr height="10%">
  <td colspan="2">
    <tiles:insert attribute="footer" />
  </td>
</tr>
</table>
</body>
</html>


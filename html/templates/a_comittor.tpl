<html>
<head>
  <link rel="stylesheet" type="text/css" href="../../css/header.css">
  <title>${repo_name}</title>
</head>
<body>
<#include "header.tpl">
 <div id="main_div">
  <h2 align='center'>${the_branch.name}</h2>
  <table style="width:100%" center>
    <tr>
      <th center>ID / Tag</th>
      <th center>Message</th>
      <th center>Date</th>
      <th center>Comittor</th>
    </tr>
    <#list the_branch.comitts as a_comitt>
    <tr>
      <td id="com_id">${a_comitt.id}${"<br>" + a_comitt.tag!""}</td>
      <td id="com_msg">${a_comitt.message!"No message"}</td>
      <td id="com_dat">${a_comitt.the_date?date}</td>
      <td id="com_nam">${a_comitt.the_commitor.name}</td>
    </tr>
    </#list>
  </table>
 </div>
</body>
</html>
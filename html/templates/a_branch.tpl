<html>
<head>
  <link rel="stylesheet" type="text/css" href="../../css/header.css">
  <title>${repo_name}</title>
</head>
<body>
<#include "header.tpl">
 <div id="main_div">
  <h2 align='center'>${the_branch.name}</h2>
  <#if the_comittors?has_content>
  <div style="overflow-x:auto;">
    <table>
     <tr>
      <#list the_comittors as a_comittor>
        <th>${a_comittor.name}</th>
      </#list>
     </tr>
     <tr>
      <#list comitts_of_comittors as a_number>
        <td><Strong>${a_number}</Strong><br>${ a_number / the_branch.num_comitts * 100}%</td>
      </#list>
     </tr>
    </table>
  </div>
    
    <table style="width:100%" center>
  <br><br>
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
  <#else>
   <p> No Comitts in this branch</p>
  </#if>
</body>
</html>
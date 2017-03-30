<html>
<head>
  <link rel="stylesheet" type="text/css" href="../css/header.css">
  <title>${repo_name}</title>
</head>
<body>
<#include "header.tpl">
 <div id="main_div">
  <table style="width:100%" center>
    <tr>
      <th>Name</th>
      <th>Date of Creation</th>
      <th>Date of last update</th>
      <th>% of total comitts</th>
    </tr>
    <#list branches as a_branch>
    <tr>
      <td><a href=branches/${a_branch.name?replace("/","_")}.html>${a_branch.name}</a></td>
      <td>${a_branch.d_creation?date}</td>
      <td>${a_branch.d_updated?date}</td>
      <td>${a_branch.num_comitts / total_comitts * 100} %</td>
    </tr>
    </#list>
  </table>
 </div>  
</body>
</html>
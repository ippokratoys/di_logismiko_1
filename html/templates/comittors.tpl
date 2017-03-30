<html>
<head>
  <link rel="stylesheet" type="text/css" href="../css/header.css">
  <title>${repo_name}</title>
</head>
<body>
 <#include "header.tpl">
 <div id="main_div">
   <h3> Total number of comittors : ${num_comittors}</h3>
  <table style="width:100%" center>
    <tr>
      <th>Name</th>
      <th>Number of Commits</th>
      <th>% of Commits (general)</th>
      <th>Per Day</th>
      <th>Per Week</th>
      <th>Per Month</th>
      <th>Lines added</th>
      <th>Lines deleted</th>
      <th>Files edited</th>
    </tr>
    <#list comittors as a_com>
    <tr>
      <td>${a_com.name}</td>
      <td>${a_com.num_of_commits}</td>
      <td>${a_com.num_of_commits / total_comitts * 100}%</td>
      <td>${a_com.per_day!"-"}</td>
      <td>${a_com.per_week!"-"}</td>
      <td>${a_com.per_month!"-"}</td>
      <td>${a_com.insertions}</td>
      <td>${a_com.deletes}</td>
      <td>${a_com.modified}</td>
    </tr>
    </#list>
  </table>
 </div>
</body>
</html>
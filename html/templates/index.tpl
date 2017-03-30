<html>
<head>
  <link rel="stylesheet" type="text/css" href="../css/header.css">
  <link rel="stylesheet" type="text/css" href="../css/index.css">
  <title>${repo_name}</title>
</head>
<body>
<#include "header.tpl">
 <div id="main_div">
  <p>
  <img src="../css/icons/file.png" alt="" style="float:left;width:65px;height:65px;">
  <div class="icon">Number of Files : ${num_of_file}<br>
  Number of Lines : ${num_of_lines}<br></div>
  </p>
  <p>
    <a href="branches.html">
      <img src="../css/icons/branch.png" alt="" style="float:left;width:65px;height:65px;">
    </a>
  <div class="icon">Number of Branches : ${num_of_branches}<br>
  Number of Comitts : ${num_of_commits}<br>
  Number of tags : ${num_of_tags}</div>
  </p>
 </div>
</body>
</html>
<div id="header_div">
<h1>Welcome  to ${repo_name!"A repo"}!</h1>
<h4>Plakias Apostolos - Poludoros Athanasios</h4>
<ul>
  <#if cur_page == "a_branch">
  <li><a href="../home.html">Home</a></li>
  <li><a href="../comittors.html">Comittors</a></li>
  <li><a class="current" href="../branches.html">Branches</a></li>
  <li><a href="../about.html">About</a></li>
  <#else>
  <li><a <#if cur_page == "home">class="current"</#if> href="home.html">Home</a></li>
  <li><a <#if cur_page == "comittors">class="current"</#if> href="comittors.html">Comittors</a></li>
  <li><a <#if cur_page == "branches">class="current"</#if> href="branches.html">Branches</a></li>
  <li><a <#if cur_page == "about">class="current"</#if> href="about.html">About</a></li>
  
  </#if>
</ul>
</div>
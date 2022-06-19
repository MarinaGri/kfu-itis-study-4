<#import 'macros/layout.ftl' as layout/>

<@layout.main 'page.title.articles' '/css/articles_style.css'>
    <div class="posts">
        <div class="article">
            <div class="title">${article.title}</div>
            <#outputformat 'plainText'>
                ${article.text}
            </#outputformat>
        </div>
    </div>
</@layout.main>

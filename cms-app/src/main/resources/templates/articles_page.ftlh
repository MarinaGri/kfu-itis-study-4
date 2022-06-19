<#import 'macros/layout.ftl' as layout/>
<#import 'macros/util.ftl' as util/>
<#import '/spring.ftl' as spring/>

<@layout.main 'page.title.articles' '/css/articles_style.css'>
    <div class="posts">
        <#list page.articles as article>
            <div class="post">
                <a href="<@spring.url '/${article.slug}'/>">
                    <div class="title">${article.title}</div>
                </a>
                <#if article.isForAdmins>
                    <img class="icon" data-toggle="tooltip" data-placement="top"
                         title="<@spring.message 'tooltip.for-admins'/>"
                         src="<@spring.url '/img/svg/lock-fill.svg'/>" alt="lock">
                </#if>
            </div>
        </#list>
    </div>
    <@util.pagination page.index page.totalPages/>
</@layout.main>

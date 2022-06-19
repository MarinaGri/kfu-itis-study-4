<#ftl encoding="UTF-8"/>

<#import '/spring.ftl' as spring/>
<#import 'util.ftl' as util/>

<#macro main title css listJs=[]>
    <html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="http-equiv" content="Content-type: text/html; charset=UTF-8">
        <@util.static css listJs/>
        <title><@spring.message title/></title>
    </head>
    <body>
    <div class="wrapper">
        <div class="header">
            <h2>CMS</h2>
            <div class="icon-group">
                <#if user?has_content>
                    <span class="name">${user.firstName + ' ' + user.lastName}</span>
                    <a href="<@spring.url '/logout'/>">
                        <img class="icon" src="<@spring.url '/img/svg/box-arrow-left.svg'/>" alt="profile">
                    </a>
                    <a href="<@spring.url '/articles/edit'/>">
                        <img class="icon" src="<@spring.url '/img/svg/pencil-fill.svg'/>" alt="feed">
                    </a>
                </#if>
                <a href="<@spring.url '/articles'/>">
                    <img class="icon" src="<@spring.url '/img/svg/card-heading.svg'/>" alt="feed">
                </a>
            </div>
        </div>
        <div class="main-row">
            <#nested>
        </div>
        <div class="footer">
            89172453479
            <div class="icon-group">
                <a href="https://github.com/MarinaGri">
                    <img class="icon" src="<@spring.url '/img/svg/github.svg'/>" alt="github">
                </a>
                <a href="https://t.me/idpriv">
                    <img class="icon" src="<@spring.url '/img/svg/telegram.svg'/>" alt="telegram">
                </a>
            </div>
        </div>
    </div>
    </body>
    </html>
</#macro>

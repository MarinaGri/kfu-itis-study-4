<#import '/spring.ftl' as spring/>

<#macro static css listJs=[]>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <script src="//cdn.ckeditor.com/4.18.0/standard/ckeditor.js"></script>
    <link rel="stylesheet" type="text/css" href="<@spring.url css/>"/>

    <script>ctx = "${springMacroRequestContext.contextPath}"</script>
    <#if listJs?has_content>
        <#list listJs as path>
            <script src="<@spring.url path/>" charset="UTF-8"></script>
        </#list>
    </#if>
</#macro>

<#macro pagination current total>
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-end">
            <li class="page-item <#if current <= 0> disabled </#if>">
                <a class="page-link" href="<@spring.url '/articles?page=${current-1}'/>">Previous</a>
            </li>
            <li class="page-item <#if (current >= total-1)> disabled </#if>">
                <a class="page-link" href="<@spring.url '/articles?page=${current+1}'/>">Next</a>
            </li>
        </ul>
    </nav>
</#macro>

<#import '/spring.ftl' as spring/>

<#macro input path code type required='' length=''>
    <@spring.bind path/>
    <div class="mb-3">
        <label class="form-label"><@spring.message code/></label>
        <#if (length?has_content)>
            <div class="count" id="${path}">0/${length}</div>
        </#if>
        <@spring.formInput path 'class="form-control" ${required}' type/>
        <@spring.showErrors ' ' 'form-text'/>
    </div>
</#macro>

<#macro checkbox path code required=''>
    <div class="mb-3 form-check">
    <@spring.formCheckbox path '${required} type="checkbox" class="form-check-input" id="exampleCheck1"'/>
        <label class="form-check-label" for="exampleCheck1"><@spring.message code/></label>
    </div>
</#macro>

<#macro textarea path code>
    <div class="mb-3">
        <label class="form-label"><b><@spring.message code/></b></label>
        <@spring.formTextarea path 'class="form-control" rows="2"'/>
    </div>
</#macro>

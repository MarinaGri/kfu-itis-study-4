<#import 'macros/layout.ftl' as layout/>
<#import 'macros/form.ftl' as form/>
<#import '/spring.ftl' as spring/>

<#assign jsPaths = ['/js/length_check.js', '/js/ckeditor.js']>

<@layout.main 'page.title.edit-page' '/css/articles_style.css' jsPaths>
    <div class="posts">
        <form method="POST" action="" class="post-form">
            <@form.input 'form.title' 'form.input.title' 'text' 'required' '255'/>
            <@form.textarea 'form.text' 'form.textarea.article'/>
            <@form.checkbox 'form.isForAdmins' 'form.checkbox.for-admins'/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button type="submit" id="btn" class="button"><@spring.message 'button.article'/></button>
            <#if slug?has_content>
                <div class="success"><@spring.message 'success.save.article'/>  ${slug}</div>
            </#if>
        </form>
    </div>
</@layout.main>

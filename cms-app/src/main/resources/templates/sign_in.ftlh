<#import 'macros/sublayout.ftl' as layout/>
<#import 'macros/form.ftl' as form/>
<#import '/spring.ftl' as spring/>

<@layout.entrance 'page.title.sign-in'>
    <form method="POST" action="">
        <@form.input 'form.email' 'form.input.email' 'email' 'required'/>
        <@form.input 'form.password' 'form.input.password' 'password' 'required'/>
        <#if error>
            <span class="form-text"><@spring.message 'error.sign-in'/></span>
        </#if>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button type="submit" class="button" name="sent"><@spring.message 'button.sign-in'/></button>
    </form>
    <a href="<@spring.url '/signUp'/>">
        <button class="button"><@spring.message 'button.sign-up'/></button>
    </a>
</@layout.entrance>

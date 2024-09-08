<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Авторизация</h1>
    <hr>
    <form action="/login" method="post">
        <div class="form-group">
            <label for="email">Электронная почта</label>
            <input type="email" class="form-control <#if error??>is-invalid</#if>" id="email" name="username" value="${email?if_exists}">
            <div class="invalid-feedback">
                Неверная электронная почта
            </div>
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" class="form-control <#if error??>is-invalid</#if>" id="password" name="password">
            <div class="invalid-feedback">
                Неверный пароль
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" style="width: 100%" class="btn btn-dark">Войти</button>
    </form>

    <hr>
    <#if error??>
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
    </#if>
    <h2>Нет аккаунта BUYSELL? <a href="/registration">Зарегистрируйтесь!</a></h2>
</@t.template>

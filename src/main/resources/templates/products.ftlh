<#import "blocks/template.ftlh" as t>
<#if user??>
    <@t.template user>
        <h1 style="text-align: center">Поиск Товаров</h1>
        <form action="/" method="get">
            <div class="form-row">
                <div class="form-group col-md-2">
                    <label for="searchCity">Город</label>
                    <select id="searchCity" class="form-control">
                        <option value="">Город не выбран</option>
                        <!-- список городов -->
                    </select>
                </div>
                <div class="form-group col-md-10">
                    <label for="searchWord">Поиск по обьявлениям</label>
                    <input type="text" name="searchWord"
                           <#if searchWord??>value="${searchWord}"</#if>
                           class="form-control" id="searchWord">
                </div>
                <button style="width: 100%" type="submit" class="btn btn-dark">Поиск</button>
            </div>
        </form>
        <hr>
        <div class="card-columns">
            <#list products as product>
                <a href="/product/${product.id}" class="product-link">
                    <div class="card text-white bg-dark my-3 product">
                        <img src="/images/${product.previewImageId}" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title">${product.title}</h5>
                            <p class="card-text">${product.price} $</p>
                        </div>
                        <div class="card-footer">
                            <#if product.city??>
                                ${product.city}
                            <#else>
                                Город не указан
                            </#if>
                        </div>
                    </div>
                </a>
            <#else>
                <h1 style="text-align: center">Не Найден</h1>
            </#list>
        </div>
    </@t.template>
<#else>
    <p>Пользователь не определён.</p>
</#if>

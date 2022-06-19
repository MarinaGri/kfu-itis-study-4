<#ftl encoding='UTF-8'>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Products Page</title>
</head>
<body>
<h1>Products Page</h1>
<form style="float: left; width: 30%; margin-left: 1rem" method="post">
    <div class="mb-3">
        <label for="name" class="form-label">Name</label>
        <input type="text" class="form-control" id="name" name="name">
    </div>
    <div class="mb-3">
        <label for="price" class="form-label">Price</label>
        <input type="text" class="form-control" id="price" name="price">
    </div>
    <button type="submit" class="btn btn-primary">Add to cart</button>
</form>
<div style="float: right; width: 40%; display: inline-grid">
    <h2>Cart</h2>
    <table style="float: right">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
        </tr>
        <#if products?has_content>
            <#list products as product>
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                </tr>
            </#list>
        </#if>
    </table>
</div>
</body>
</html>

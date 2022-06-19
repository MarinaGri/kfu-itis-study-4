# Shop Service

* `ProductsRepository` - общий интерфейс для репозиториев на продукты, содержащий CRUD
* `ProductsRepositoryJdbcTemplateImpl` - реализация на основе `NamedParameterJdbcTemplate` c `@Profile("jdbc_template")`
* `ProductsRepositoryJpaImpl` - реализация на основе `EntityManager` c `@Profile("entity_manager")`
* Профиль задается в переменной `SPRING_PROFILE`
* В `AccountsRepository` метод с `@Query`, запрос на JPQL

    Получение всех аккаунтов, у которых есть продукты в корзине

    Число дней скидки для этих продуктов равно параметру `dayNum`

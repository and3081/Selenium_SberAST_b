#### Тестирование выборки в Сбер-АСТ - Selenium, Java11, Junit5, Maven, Allure

## запуск всех тестов
mvn clean test

## построение отчета Allure
mvn allure:serve

## настройки в классе DataProvider
параметризация тестов

## настройки в проперти:
application.properties - настройки url и endpoints сайта<br>
browser.properties - настройки браузеров и веб-драйвера (описание в файле)<br>
listener.properties - настройки листенера скриншотов для Allure (описание в файле)


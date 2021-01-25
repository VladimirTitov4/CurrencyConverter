# CurrencyConverter
Приложение конвертер курсов валют. 

При запуске приложения идет получение актуальных курсов валют с сайта http://www.cbr.ru/scripts/XML_daily.asp 
Если полученные котировки не актуальны, то идет сохранение в базу данных Postgres. 

Вход на главный экран приложение выполняется через регистрацию и авторизацию. Пользователь пройдя авторизацию попадает на главный экран, где может выбрать из какой валюты и в какую будет конвертация. Указывает количество переводимых средств и нажимает кнопку "Конвертировать" Реализовано сохранение истории конвертаций и поиск по истории. 

Запуск приложения:
1. git clone https://github.com/VladimirTitov4/CurrencyConverter.git
2. cd CurrencyConverter
3. ./mvnw clean install -DskipTests
4. sudo docker-compose up -d
5. Открыть в браузере http://localhost:8080/

Использованные технологии:

- Java 11 
- Spring Boot 2.3.2
- Feign 11.0
- Maven 3.6.0
- Lombok 1.8.12
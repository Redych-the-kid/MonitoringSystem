# MonitoringSystem
Приложение для формаирования строки автоматизированной системы мониторинга
## Используемые технологии
* Java 19(язык программирования)
* Apache Commons Cli(парсинг аргументов коммандной строки)
* Log4J(логгирование)
* Maven(система сборки)
## Требования для запуска
* JRE для Java 19
## Использование
Запустите JAR, лежащий в папке build в репозитории, например:
```
java -jar MonitoringSystem.jar -t1 yesterday.txt -t2 today.txt -m message.txt
```
Формат файла таблицы должен выглядить так:
```
URL,код
```
Например:
```
ya.ru,200
mail.ru,404
github.com,500
```
## Логгирование
Приложение может выводить логи о своей работе вместе с результатом исполнения программы. Для этого необходимо указать в параметрах VM аргумент:
```
-DlogLevel=DEBUG
```
Или любой другой уровень Log4J

Пример полной комманды:
```
java -DlogLevel=DEBUG -jar MonitoringSystem.jar -t1 yesterday.txt -t2 today.txt -m message.txt
```

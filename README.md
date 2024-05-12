
# Телеграм бот "Мини - Банк"

##  Описание
*Данный бот выполняет функциональность "Мини-банка" и разработан в рамках практических задач   школы GPB-IT-FACTORY.*

## Содержание


Архитектура бота состоит **из трех** компонентов:

1. *frontend (telegram-bot на java/kotlin);*
2. *middle-слой (java/kotlin-сервис);*
3. *backend (java/kotlin-сервис).*

##  Технологии
:small_orange_diamond: Раздел находится в разработке и будет обогащен после  
получения ТЗ.

[JDK Development Kit](https://www.oracle.com/java/technologies/downloads/)
[Gradle](https://gradle.org/install/)
[Docker](https://www.docker.com/products/docker-desktop/)


## Установка

:small_orange_diamond: Раздел находится в разработке и будет обогащен после  
получения ТЗ.

## Использование
:small_orange_diamond: Раздел находится в разработке и будет обогащен после  
получения ТЗ.

## Разработка

###  Требования

Для установки и запуска проекта потребуется:
[JDK Development Kit](https://www.oracle.com/java/technologies/downloads/)

###  Установка зависимостей
:small_orange_diamond: Раздел находится в разработке и будет обогащен после  
получения ТЗ.  
$ npm i

## Тестирование

Тут будет информация, какие инструменты тестирования использованы в проекте и как их запускать. Например:

Наш проект покрыт юнит-тестами JUnit

## Контакты
По всем вопросам, касающихся данного проекта Вы можете обратиться по на почту **@gu_code@mail.ru**

## FAQ

Часто задаваемые вопросы и ответы на них будут представлены в этом блоке

## Диаграмма последовательности

Данная диаграмма описывает взаимодействие компонентов бота "Мини-банк".

```plantuml  
@startuml  
  
actor User  
participant "Client Device" as Client  
participant "Telegram Bot" as Bot  
participant "Server" as Server  
  
User -> Client: Interacts  
Client -> Bot: Sends message  
Bot -> Server: Processes message  
Server -> Bot: Returns response  
Bot -> Client: Sends response  
Client -> User: Receives response  
  
@enduml

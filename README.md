Работу выполнил Чубаренко Семён РИ-420940

Android приложение для управления книгами на Kotlin с использованием Jetpack Compose.

## Функционал

### Основные возможности:
- **Просмотр книг**: Список книг с загрузкой из сети
- **Избранное**: Добавление книг в избранное с офлайн доступом
- **Фильтрация**: Поиск по жанру, рейтингу и названию
- **Детальная информация**: Подробное описание каждой книги
- **Профиль пользователя**: Редактируемый профиль с аватаром
- **Работа с файлами**: Скачивание резюме, выбор фото из галереи/камеры

### Технические особенности:
- **Архитектура**: Clean Architecture + MVVM
- **Навигация**: Single Activity с Bottom Navigation
- **Сеть**: Retrofit с Moshi
- **Локальное хранилище**: Room для избранного, DataStore для профиля
- **DI**: Koin для управления зависимостями
- **UI**: Jetpack Compose + Material Design 3
- **Асинхронность**: Kotlin Coroutines + Flow

## Архитектура

Приложение разделено на три слоя:

### Presentation Layer
- Экранные композаблы (`@Composable` функции)
- ViewModels для управления состоянием
- Навигация через NavController

### Domain Layer
- UseCases (сценарии использования)
- Модели предметной области
- Интерфейсы репозиториев

### Data Layer
- Реализации репозиториев
- Room DAO для локального хранилища
- Retrofit API для сетевых запросов
- DataStore для хранения настроек

## Стек технологий

- **Язык**: Kotlin 100%
- **UI**: Jetpack Compose, Material Design 3
- **Архитектура**: MVVM, Clean Architecture
- **Навигация**: Navigation Component
- **Сеть**: Retrofit 2, Moshi
- **Локальное хранилище**: Room, DataStore
- **DI**: Koin
- **Асинхронность**: Coroutines, Flow, StateFlow
- **Разрешения**: Accompanist Permissions
- **Загрузка изображений**: Coil

## Структура проекта
booksapp/
├── app/
│ ├── src/
│ │ ├── main/
│ │ │ ├── java/com/example/booksapp/
│ │ │ │ ├── data/ # Data layer
│ │ │ │ │ ├── local/ # Room, DataStore
│ │ │ │ │ ├── remote/ # Retrofit, DTO
│ │ │ │ │ └── repository/ # Repository implementations
│ │ │ │ ├── domain/ # Domain layer
│ │ │ │ │ ├── model/ # Domain models
│ │ │ │ │ ├── repository/ # Repository interfaces
│ │ │ │ │ └── usecase/ # Use cases
│ │ │ │ ├── presentation/ # Presentation layer
│ │ │ │ │ ├── books/ # Books screen
│ │ │ │ │ ├── favorites/ # Favorites screen
│ │ │ │ │ ├── filters/ # Filters screen
│ │ │ │ │ ├── profile/ # Profile screens
│ │ │ │ │ ├── details/ # Book details
│ │ │ │ │ └── navigation/ # Navigation components
│ │ │ │ └── di/ # Dependency Injection
│ │ │ └── res/ # Resources
│ │ └── build.gradle.kts
├── gradle/
├── build.gradle.kts
└── settings.gradle.kts


## Запуск сервера
1. Установить зависимости:
pip install flask flask-cors

2. Запустить сервер
python server.py

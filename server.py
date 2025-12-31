from flask import Flask, jsonify, request
from flask_cors import CORS
import random
from datetime import datetime

app = Flask(__name__)
CORS(app)

# Генерация данных о книгах
def generate_books():
    genres = ["Фантастика", "Детектив", "Роман", "Научпоп", "История", "Биография"]
    authors = ["Айзек Азимов", "Артур Конан Дойл", "Лев Толстой", "Стивен Хокинг", "Юваль Ной Харари", "Уолтер Айзексон"]

    books = []
    for i in range(1, 51):
        book = {
            "id": i,
            "title": f"Книга {i}",
            "author": random.choice(authors),
            "genre": random.choice(genres),
            "year": random.randint(1950, 2023),
            "rating": round(random.uniform(3.0, 5.0), 1),
            "pages": random.randint(100, 800),
            "description": f"Это подробное описание книги {i}. Книга рассказывает об увлекательных приключениях и содержит глубокие мысли о жизни.",
            "price": random.randint(300, 2000),
            "isFavorite": False
        }
        books.append(book)
    return books

books_data = generate_books()

@app.route('/api/books', methods=['GET'])
def get_books():
    # Параметры фильтрации
    genre = request.args.get('genre')
    min_rating = request.args.get('min_rating')
    search = request.args.get('search')

    filtered_books = books_data.copy()

    # Применяем фильтры
    if genre and genre != "Все":
        filtered_books = [b for b in filtered_books if b['genre'] == genre]

    if min_rating:
        filtered_books = [b for b in filtered_books if b['rating'] >= float(min_rating)]

    if search:
        search_lower = search.lower()
        filtered_books = [
            b for b in filtered_books
            if search_lower in b['title'].lower() or
               search_lower in b['author'].lower()
        ]

    # Добавляем задержку для имитации сети
    import time
    time.sleep(0.5)

    return jsonify({
        "books": filtered_books,
        "total": len(filtered_books),
        "timestamp": datetime.now().isoformat()
    })

@app.route('/api/books/<int:book_id>', methods=['GET'])
def get_book(book_id):
    book = next((b for b in books_data if b['id'] == book_id), None)
    if book:
        return jsonify(book)
    return jsonify({"error": "Book not found"}), 404

@app.route('/api/genres', methods=['GET'])
def get_genres():
    genres = list(set([b['genre'] for b in books_data]))
    return jsonify({"genres": ["Все"] + genres})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin, map, switchMap } from 'rxjs';
import { environment } from '../environments/environment';
import {
  BookCollectionDto,
  BookReadDto,
  BookCreateDto
} from '../models/book.model';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private baseUrl = `${environment.apiUrl}/books`;

  constructor(private http: HttpClient) {}

  // pomocniczo – wszystkie książki (BookCollectionDto)
  getAllCollection(): Observable<BookCollectionDto[]> {
    return this.http.get<BookCollectionDto[]>(this.baseUrl);
  }

  // 7. szczegóły elementu
  getById(id: string): Observable<BookReadDto> {
    return this.http.get<BookReadDto>(`${this.baseUrl}/${id}`);
  }

  // 5. dodanie elementu
  create(dto: BookCreateDto): Observable<BookReadDto> {
    return this.http.post<BookReadDto>(this.baseUrl, dto);
  }

  // 6. edycja elementu – WYMAGA @PutMapping w backendzie (patrz sekcja 3)
  update(id: string, dto: BookCreateDto): Observable<BookReadDto> {
    return this.http.put<BookReadDto>(`${this.baseUrl}/${id}`, dto);
  }

  // 4. usuwanie elementu
  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // 4. lista elementów z danej kategorii
  // WERSJA 1 – zakładamy, że dodasz endpoint GET /api/books/by-genre/{genreId}
  getByGenre(genreId: string): Observable<BookCollectionDto[]> {
    return this.http.get<BookCollectionDto[]>(`${this.baseUrl}/by-genre/${genreId}`);
  }

  /*
  // WERSJA 2 (gdybyś NIE dodawał endpointu w backendzie):
  // - pobieramy getAllCollection()
  // - dla każdej książki robimy GET /api/books/{id} i filtrujemy po genreId
  // To jest bardziej "kombinowane", ale działa na samym tym co masz.
  getByGenre(genreId: string): Observable<BookReadDto[]> {
    return this.getAllCollection().pipe(
      switchMap((collection) =>
        forkJoin(collection.map((c) => this.getById(c.id)))
      ),
      map((books) => books.filter((b) => b.genreId === genreId))
    );
  }
  */
}

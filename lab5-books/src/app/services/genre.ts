import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../environments/environment';   // <-- TU ZMIANA
import {
GenreCollectionDto,
GenreReadDto,
GenreCreateDto
} from '../models/genre.model';                               // <-- też o katalog wyżej

@Injectable({
providedIn: 'root'
})
export class GenreService {
private baseUrl = `${environment.apiUrl}/genres`;

constructor(private http: HttpClient) {}

  // 1. lista kategorii
  getAll(): Observable<GenreCollectionDto[]> {
    return this.http.get<GenreCollectionDto[]>(this.baseUrl).pipe(
      catchError(err => {
        console.error('API error in getAll()', err);
        return throwError(() => err);
      })
    );
  }

  // 3 + 4. pobranie jednej kategorii (do edycji i szczegółów)
  getById(id: string): Observable<GenreReadDto> {
    return this.http.get<GenreReadDto>(`${this.baseUrl}/${id}`);
  }

  // 2. dodanie kategorii
  create(dto: GenreCreateDto): Observable<GenreReadDto> {
    return this.http.post<GenreReadDto>(this.baseUrl, dto);
  }

  // 3. edycja kategorii
  update(id: string, dto: GenreCreateDto): Observable<GenreReadDto> {
    return this.http.put<GenreReadDto>(`${this.baseUrl}/${id}`, dto);
  }

  // 1. usuwanie kategorii
  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}

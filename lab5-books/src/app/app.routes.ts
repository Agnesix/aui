import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GenreListComponent } from './components/genre-list/genre-list';
import { GenreFormComponent } from './components/genre-form/genre-form';
import { GenreDetailsComponent } from './components/genre-details/genre-details';
import { BookFormComponent } from './components/book-form/book-form';
import { BookDetailsComponent } from './components/book-details/book-details';

export const routes: Routes = [
  { path: '', redirectTo: 'genres', pathMatch: 'full' },

  // 1. lista kategorii
  { path: 'genres', component: GenreListComponent },

  // 2. dodawanie kategorii
  { path: 'genres/new', component: GenreFormComponent },

  // 3. edycja kategorii
  { path: 'genres/:id/edit', component: GenreFormComponent },

  // 4. szczegóły kategorii + lista elementów
  { path: 'genres/:id', component: GenreDetailsComponent },

  // 5. dodawanie elementu
  { path: 'genres/:genreId/books/new', component: BookFormComponent },

  // 6. edycja elementu
  { path: 'genres/:genreId/books/:bookId/edit', component: BookFormComponent },

  // 7. szczegóły elementu
  { path: 'genres/:genreId/books/:bookId', component: BookDetailsComponent },

  { path: '**', redirectTo: 'genres' }
];

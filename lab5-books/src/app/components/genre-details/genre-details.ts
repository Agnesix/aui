import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

import { GenreService } from '../../services/genre';
import { BookService } from '../../services/book';

import { GenreReadDto } from '../../models/genre.model';
import { BookCollectionDto } from '../../models/book.model';

@Component({
selector: 'app-genre-details',
standalone: true,
imports: [CommonModule],
templateUrl: './genre-details.html'
})
export class GenreDetailsComponent implements OnInit {

genreId!: string;
genre: GenreReadDto | null = null;

books: BookCollectionDto[] = [];

loadingGenre = false;
loadingBooks = false;
error: string | null = null;

constructor(
    private route: ActivatedRoute,
    private router: Router,
    private genreService: GenreService,
    private bookService: BookService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    console.log('[GenreDetails] route id =', id);

    if (!id) {
      this.error = 'No category id in route';
      return;
    }

    this.genreId = id;
    this.loadGenre();
    this.loadBooks();
  }

  private loadGenre(): void {
    this.loadingGenre = true;
    console.log('[GenreDetails] loadGenre start, id =', this.genreId);

    this.genreService.getById(this.genreId).subscribe({
      next: (g: GenreReadDto) => {
        console.log('[GenreDetails] genre from API =', g);
        this.genre = g;
        this.loadingGenre = false;
      },
      error: (err) => {
        console.error('[GenreDetails] error loading genre', err);
        this.error = 'Could not load category details';
        this.loadingGenre = false;
      }
    });
  }

  private loadBooks(): void {
    this.loadingBooks = true;
    console.log('[GenreDetails] loadBooks start, genreId =', this.genreId);

    this.bookService.getByGenre(this.genreId).subscribe({
      next: (data: BookCollectionDto[]) => {
        console.log('[GenreDetails] books from API =', data);
        this.books = data;
        this.loadingBooks = false;
      },
      error: (err) => {
        console.error('[GenreDetails] error loading books', err);
        this.loadingBooks = false;
      }
    });
  }

  onBack(): void {
    this.router.navigate(['/genres']);
  }

  onEditGenre(): void {
    this.router.navigate(['/genres', this.genreId, 'edit']);
  }

  onAddBook(): void {
    this.router.navigate(['/genres', this.genreId, 'books', 'new']);
  }

  onBookDetails(bookId: string): void {
    this.router.navigate(['/genres', this.genreId, 'books', bookId]);
  }

  onBookEdit(bookId: string): void {
    this.router.navigate(['/genres', this.genreId, 'books', bookId, 'edit']);
  }

  onBookDelete(bookId: string): void {
    if (!confirm('Delete this book?')) return;

    this.bookService.delete(bookId).subscribe({
      next: () => this.loadBooks(),
      error: () => alert('Error deleting book')
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../../services/book';
import { BookReadDto, BookCollectionDto } from '../../models/book.model';

@Component({
selector: 'app-book-details',
standalone: true,
imports: [CommonModule],
templateUrl: './book-details.html'
})
export class BookDetailsComponent implements OnInit {
genreId!: string;
bookId!: string;

loading = false;
error: string | null = null;
book: BookReadDto | null = null;

constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService
  ) {}

  ngOnInit(): void {
    // parametry z URL: /genres/:genreId/books/:bookId
    this.genreId = this.route.snapshot.paramMap.get('genreId')!;
    this.bookId = this.route.snapshot.paramMap.get('bookId')!;

    console.log('[BookDetails] genreId =', this.genreId);
    console.log('[BookDetails] bookId  =', this.bookId);

    this.loadBook();
  }

  private loadBook(): void {
    this.loading = true;
    this.error = null;
    this.book = null;

    console.log('[BookDetails] loadBook() – próbuję GET /api/books/{id}');

    this.bookService.getById(this.bookId).subscribe({
      next: (b) => {
        console.log('[BookDetails] odpowiedź z getById:', b);
        this.book = b;
        this.loading = false;
      },
      error: (err) => {
        console.warn(
          '[BookDetails] getById nie zadziałał, fallback na getByGenre:',
          err
        );
        // FALLBACK: jeśli nie ma /api/books/{id}, szukamy książki w liście z danego gatunku
        this.loadFromGenre();
      }
    });
  }

  // Fallback – korzysta z /api/books/by-genre/{genreId}
  private loadFromGenre(): void {
    console.log('[BookDetails] loadFromGenre() – GET /by-genre/{genreId}');

    this.bookService.getByGenre(this.genreId).subscribe({
      next: (books: BookCollectionDto[]) => {
        console.log('[BookDetails] books from by-genre:', books);
        const found = books.find((b) => b.id === this.bookId);

        if (found) {
          // budujemy BookReadDto z tego co mamy
          this.book = {
            id: found.id,
            title: found.title,
            author: '',             // backend na razie tego nie zwraca
            isbn: '',
            publicationYear: 0,
            genreId: this.genreId
          };
          this.error = null;
        } else {
          this.error = 'Book not found.';
          this.book = null;
        }

        this.loading = false;
      },
      error: (err) => {
        console.error('[BookDetails] błąd w getByGenre:', err);
        this.error = 'Could not load book details.';
        this.loading = false;
      }
    });
  }

  onBack(): void {
    this.router.navigate(['/genres', this.genreId]);
  }

  onEdit(): void {
    this.router.navigate([
      '/genres',
      this.genreId,
      'books',
      this.bookId,
      'edit'
    ]);
  }
}

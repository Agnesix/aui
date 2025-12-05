import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
FormBuilder,
Validators,
ReactiveFormsModule,
FormGroup
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../../services/book';
import { BookCreateDto, BookReadDto } from '../../models/book.model';

@Component({
selector: 'app-book-form',
standalone: true,
imports: [CommonModule, ReactiveFormsModule],
templateUrl: './book-form.html'
})
export class BookFormComponent implements OnInit {
mode: 'create' | 'edit' = 'create';

genreId!: string;
bookId: string | null = null;

saving = false;
error: string | null = null;

form!: FormGroup;

constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService
  ) {}

  ngOnInit(): void {
    // TU inicjujemy formularz – po tym jak fb jest już wstrzyknięty
    this.form = this.fb.group({
      title: ['', Validators.required],
      author: ['', Validators.required],
      isbn: ['', Validators.required],
      publicationYear: [new Date().getFullYear(), Validators.required]
    });

    this.genreId = this.route.snapshot.paramMap.get('genreId')!;
    this.bookId = this.route.snapshot.paramMap.get('bookId');

    if (this.bookId) {
      this.mode = 'edit';
      this.loadBook();
    }
  }

  private loadBook(): void {
    this.bookService.getById(this.bookId!).subscribe({
      next: (book: BookReadDto) => {
        this.form.patchValue({
          title: book.title,
          author: book.author,
          isbn: book.isbn,
          publicationYear: book.publicationYear
        });
      },
      error: () => {
        this.error = 'Could not load book for editing.';
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const v = this.form.value;
    const dto: BookCreateDto = {
      title: v.title!,
      author: v.author!,
      isbn: v.isbn!,
      publicationYear: Number(v.publicationYear),
      genreId: this.genreId
    };

    this.saving = true;
    this.error = null;

    const obs = this.bookId
      ? this.bookService.update(this.bookId!, dto)
      : this.bookService.create(dto);

    obs.subscribe({
      next: () => {
        this.saving = false;
        this.router.navigate(['/genres', this.genreId]);
      },
      error: () => {
        this.saving = false;
        this.error = 'Could not save book.';
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/genres', this.genreId]);
  }
}

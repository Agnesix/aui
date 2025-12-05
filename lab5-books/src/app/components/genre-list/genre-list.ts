import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { GenreService } from '../../services/genre';
import { GenreCollectionDto } from '../../models/genre.model';

@Component({
selector: 'app-genre-list',
standalone: true,
imports: [CommonModule],
templateUrl: './genre-list.html'
})
export class GenreListComponent implements OnInit {
genres: GenreCollectionDto[] = [];
loading = false;
error: string | null = null;

constructor(
    private genreService: GenreService,
    private router: Router
  ) {}

  ngOnInit(): void {
    console.log('🔥 GenreListComponent init');
    this.loadGenres();
  }

  loadGenres(): void {
    console.log('[GenreList] loadGenres start');
    this.loading = true;
    this.error = null;

    this.genreService.getAll().subscribe({
      next: (data: GenreCollectionDto[]) => {
        console.log('[GenreList] data from API:', data);
        this.genres = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('[GenreList] error from API:', err);
        this.error = 'Failed to load categories';
        this.loading = false;
      }
    });
  }

  onAdd(): void {
    this.router.navigate(['/genres/new']);
  }

  onEdit(id: string): void {
    this.router.navigate(['/genres', id, 'edit']);
  }

  onDetails(id: string): void {
    this.router.navigate(['/genres', id]);
  }

  onDelete(id: string): void {
    if (!confirm('Delete this category?')) return;

    this.genreService.delete(id).subscribe({
      next: () => this.loadGenres(),
      error: () => alert('Error deleting category')
    });
  }
}

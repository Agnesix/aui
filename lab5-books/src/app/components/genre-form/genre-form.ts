import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { GenreService } from '../../services/genre';
import { GenreCreateDto, GenreReadDto } from '../../models/genre.model';

@Component({
selector: 'app-genre-form',
standalone: true,
imports: [CommonModule, ReactiveFormsModule],
templateUrl: './genre-form.html'
})
export class GenreFormComponent implements OnInit {
form!: FormGroup;

isEdit = false;
genreId: string | null = null;

loading = false;
error: string | null = null;

constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private genreService: GenreService
  ) {}

  ngOnInit(): void {
    this.buildForm();

    this.genreId = this.route.snapshot.paramMap.get('id');
    this.isEdit = !!this.genreId;

    if (this.isEdit && this.genreId) {
      this.loadGenre(this.genreId);
    }
  }

  private buildForm(): void {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      popularity: [0, [Validators.required, Validators.min(0)]],
      description: ['']
    });
  }

  private loadGenre(id: string): void {
    this.loading = true;
    this.error = null;

    this.genreService.getById(id).subscribe({
      next: (genre: GenreReadDto) => {
        this.form.patchValue({
          name: genre.name,
          popularity: genre.popularity,
          description: genre.description
        });
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading genre', err);
        this.error = 'Could not load category data';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const dto: GenreCreateDto = this.form.value;

    this.loading = true;
    this.error = null;

    if (this.isEdit && this.genreId) {
      // UPDATE
      this.genreService.update(this.genreId, dto).subscribe({
        next: () => {
          this.loading = false;
          this.router.navigate(['/genres']);
        },
        error: (err) => {
          console.error('Error updating genre', err);
          this.error = 'Error while updating category';
          this.loading = false;
        }
      });
    } else {
      // CREATE
      this.genreService.create(dto).subscribe({
        next: () => {
          this.loading = false;
          this.router.navigate(['/genres']);
        },
        error: (err) => {
          console.error('Error creating genre', err);
          this.error = 'Error while creating category';
          this.loading = false;
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/genres']);
  }
}

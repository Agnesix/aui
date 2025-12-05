
export interface BookCollectionDto {
  id: string;
  title: string;
}

export interface BookReadDto {
  id: string;
  title: string;
  author: string;
  isbn: string;
  publicationYear: number;
  genreId: string;
}

export interface BookCreateDto {
  title: string;
  author: string;
  isbn: string;
  publicationYear: number;
  genreId: string;
}

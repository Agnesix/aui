
export interface GenreCollectionDto {
  id: string;
  name: string;
}

export interface GenreReadDto {
  id: string;
  name: string;
  popularity: number;
  description: string;
}

export interface GenreCreateDto {
  name: string;
  popularity: number;
  description: string;
}

export type Genre = GenreReadDto;

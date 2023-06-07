#include <stdio.h>
#include <stdlib.h>
#include "films.h"

#define THIS_YEAR 2023

typedef struct {
	int year;
	char* title;
} Film;

void read_file(char* file_path) {
	FILE * file = fopen(file_path, "r");

	Film latest_film = {0, ""};

	if (file) {
		printf("\nPrinting films from the last 20 years...\n");
		while (!feof(file)) {
			int film_year = 0;
			char film_title[500];
			fscanf(file, "%d %s\n", &film_year, film_title);

			if (THIS_YEAR - film_year <= 20) {
				print_film(film_year, film_title);
			}

			if (film_year > latest_film.year) {
				latest_film.year = film_year;
				latest_film.title = film_title;
			}
		}
		printf("\nPrinting latest film...\n");
		print_film(latest_film.year, latest_film.title);

		fclose(file);
	} else {
		printf("File not found");
		exit(-1);
	}
}

void print_film(int year, char* title) {
	if (THIS_YEAR - year <= 20) {
		printf("	-> Year: %d, Film: %s\n", year, title);
	}
}
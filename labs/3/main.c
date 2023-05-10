#include "main.h"

int main(int argc, char const *argv[])
{
	allocate_fruit();

	populate_tables(10, 5);

	return 0;
}


void allocate_fruit() {
	
	// Stack struct
	fruit_t stack_fruit = {
		.name = "Apple",
		.price = 0.65,
		.taste = 1
	};
	printf(
		"Fruit %s (£%.2f) is %s.\n", 
		stack_fruit.name, stack_fruit.price, taste_type(stack_fruit.taste)
	);

	// Heap struct
	fruit_t* heap_fruit = (fruit_t*) malloc(sizeof(fruit_t*));

	char* fruit_name = "Lemon";
	heap_fruit -> name = (char*) malloc(strlen(fruit_name) + 1);
	strcpy(heap_fruit -> name, fruit_name);

	heap_fruit -> price = 0.43;
	heap_fruit -> taste = 0;

	printf(
		"Fruit %s (£%.2f) is %s.\n", 
		heap_fruit -> name, heap_fruit -> price, taste_type(heap_fruit -> taste)
	);
	free(heap_fruit);
}


char* taste_type(taste_t taste) {
	switch(taste) {
		case sour:
			return "sour";
		case sweet:
			return "sweet";
		case bitter:
			return "bitter";
	}
}

void populate_tables(unsigned int a, unsigned int b) {

	int** table = (int**) malloc(sizeof(int*) * 1);

	for (unsigned int i = 0; i < a; i++) {
		int* row = (int*) malloc(sizeof(int) * b);

		for (unsigned int j = 0; j < b; j++) {
			row[j] = i * j;
		}
		table[i] = row;
	}

	printf("Table at [%u][%u] = %u\n", 3, 3, table[3][3]);
	printf("Table at [%u][%u] = %u\n", 9, 2, table[9][2]);
	printf("Table at [%u][%u] = %u\n", 6, 4, table[6][4]);
	free(table);
}
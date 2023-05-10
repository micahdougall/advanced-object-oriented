#include <stdio.h>
#include <stdlib.h>

typedef enum type {
	sour, sweet, bitter
} taste_t;


/**
 * Fruit - Stores data associated with a type of fruit.
 * @taste: Taste category, one of [0, 1, 2] to show [sour, sweet, bitter].
 * @price: Price of the fruit.
 * @name: Name of the fruit.
 * 
 * Simplified with typedef product_ext.
 */
typedef struct Fruit {
	taste_t taste;
	float price;
	char *name;
} fruit_t;

void allocate_fruit();
char * taste_type(taste_t taste);
void populate_tables(unsigned int a, unsigned int b);
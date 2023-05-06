#include <stdio.h>
#include <stdlib.h>
#include <string.h>
// #include "main.h"


typedef struct Product {
	unsigned int code;
	unsigned int stock;
	float price;
	float discount;
	char name[100];
	// char *name;
} product_t;


void print_product(product_t product) {
	printf(
		"%-10d %6d %9.2f %3.0f%% %s\n", 
		product.code, product.stock, product.price, 
		product.discount * 100, product.name
	);
	// printf("%s\n", product.name);
}

unsigned int count_records(char *file_name) {
	char *file_path = realpath(file_name, NULL);
	if (file_path != NULL) {

		FILE *file = fopen(file_path, "r");

		unsigned int record_count;
		fscanf(file, "%d\n", &record_count);
		return record_count;
	} else {
		printf("Invalid file path: %s\n", file_name);
		exit(-1);
	}
}

void read_file(char *file_name, int product_count, product_t *products) {
	char *file_path = realpath(file_name, NULL);

	if (file_path != NULL) {

		FILE *file = fopen(file_path, "r");

		unsigned int record_count;
		fscanf(file, "%d\n", &record_count);

		if (record_count != product_count) {
			printf(
				"File records count (%d) doesn't match expected number of rows (%d)\n", 
				record_count, product_count
			);
			exit(-1);
		}

		// product_t products[record_count];
		// printf("Created temp pointer at: %p\n", products);

		printf("Reading %d records from %s...\n\n", record_count, file_name);

		for (unsigned int i = 0; i < record_count; i++) {
			if (!feof(file)) {
				unsigned int code;
				unsigned int stock;
				float price;
				float discount;
				char name[100];
				// char *name;

				fscanf(
					file, 
					"%d %d %f %f %s\n", 
					&code, &stock, &price, &discount, name
				);

				// printf(
				// 	"%d: %d -> %d, %f, %f, %s\n", 
				// 	i, code, stock, price, discount, name
				// );

				product_t product = {
					.code = code,
					.stock = stock,
					.price = price,
					.discount = discount
					// .name = strcpy(product.name, name)
				};
				strcpy(product.name, name);

				// print_product(product);
				// product.code = code;
				// product.stock = stock;
				// product.price = price;
				// product.discount = discount;
				// product.name = name;



				// products[i].code = code;
				// products[i].stock = stock;
				// products[i].price = price;
				// products[i].discount = discount;
				// products[i].name = name;

				// printf(
				// 	"Product[%d]: %d -> %d, %f, %f, %s\n", 
				// 	i, product.code, product.stock, product.price, product.discount, product.name
				// );

				products[i] = product;

				// printf(
				// 	"Product[%d]: %d -> %d, %f, %f, %s\n", 
				// 	i, products[i].code, products[i].stock, products[i].price, products[i].discount, products[i].name
				// );
				// print_product(products[i]);

			} else {
				printf("Unexpected record count! EOF reached after %d records\n", i);
				exit(-1);
			}
		}
		if (!feof(file)) {
			printf(
				"Additional data found after record %d, please check file header\n", 
				record_count
			);
			exit(-1);
		}

		fclose(file);

		// return_ptr = products;
		// return record_count;
	} else {
		printf("Invalid file path: %s\n", file_name);
		exit(-1);
	}
}

int main(int argc, char const *argv[])
{
	char *file_name = "T5-Products-10.txt";

	// product_t products[record_count];
	unsigned int product_count = count_records(file_name);

	printf("%s contains %d records.\n\n", file_name, product_count);

	product_t *products = (product_t*) malloc(sizeof(product_t) * product_count);
	printf("Allocated space for %d products at %p.\n\n", product_count, products);

	// printf("Created pointer at: %p\n", products_ptr);
	read_file(file_name, product_count, products);

	// printf("Pointer now at: %p\n", products_ptr);


	printf("\nPrinting stock report for %d products found...\n\n", product_count);

	for (unsigned int i = 0; i < product_count; i++) {
		print_product(products[i]);
	}



	return 0;
}

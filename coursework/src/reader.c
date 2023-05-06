#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "product.c"

product_t * parse_products_from_file(char *file_name, unsigned int *product_count) {
	char *file_path = realpath(file_name, NULL);

	if (file_path != NULL) {

		FILE *file = fopen(file_path, "r");

		unsigned int record_count;
		fscanf(file, "%u\n", &record_count);
		*product_count = record_count;
		printf("%s contains %u records.\n\n", file_name, record_count);

		product_t *products = (product_t*) malloc(sizeof(product_t) * record_count);
		printf("Memory allocated at %p...", products);

		printf("reading in products...");
		for (unsigned int i = 0; i < record_count; i++) {
			if (!feof(file)) {
				unsigned int code;
				unsigned int stock;
				float price;
				float discount;
				char name[PRODUCT_NAME_LENGTH];

				fscanf(
					file, 
					"%u %u %f %f %s\n", 
					&code, &stock, &price, &discount, name
				);

				product_t product = {
					.code = code,
					.stock = stock,
					.price = price,
					.discount = discount
				};
				strcpy(product.name, name);

				products[i] = product;
			} else {
				printf("Unexpected record count! EOF reached after %u records\n", i);
				exit(-1);
			}
		}
		if (!feof(file)) {
			printf(
				"Additional data found after record %u, please check file header\n", 
				record_count
			);
			exit(-1);
		}

		fclose(file);
		printf("success.\n");

		return products;
	} else {
		printf("Invalid file path: %s\n", file_name);
		exit(-1);
	}
}

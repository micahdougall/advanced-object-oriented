#include "product.c"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
 * parse_products_from_file() - Parses a file to extract products data.
 * @file_name: The file name or filep path of the file to parse.
 * @*product_count: A pointer to store he number of products contained in the file data.
 * 
 * Assumes that the file is in the local directory unless a relative path is passed.
 * The first line of the file should contain the number of records, following which the 
 * file should contain a single product per line.
 * 
 * Will return unsuccessful if the number of records does not match the header.
 * 
 * Return: A pointer to an array of products where the data has been stored.
 */
product_t * parse_products_from_file(char *file_name, unsigned int *product_count) {
	char *file_path = realpath(file_name, NULL);

	if (file_path != NULL) {

		FILE *file = fopen(file_path, "r");

		unsigned int record_count;
		fscanf(file, "%u\n", &record_count);
		*product_count = record_count;

		// Verbose print out for file record count.
		char log_message[MAX_LINE];
		sprintf(log_message, "%s contains %u records.\n\n", file_name, record_count);
		print_if(VERBOSE, "%s", log_message);

		product_t *products = (product_t*) malloc(sizeof(product_t) * record_count);

		print_if(VERBOSE, "Memory allocated at %p...", products);
		print_if(VERBOSE, "%s", "reading in products...");

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
		print_if(VERBOSE, "%s", "success.\n");

		fclose(file);
		return products;
	} else {
		printf("Invalid file path: %s\n", file_name);
		exit(-1);
	}
}

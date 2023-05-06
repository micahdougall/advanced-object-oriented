#include "reader.h"

unsigned int get_num_records(char *file_name) {
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

void parse_products_from_file(char *file_name, int product_count, product_t *products) {
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
					"%d %d %f %f %s\n", 
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
		printf("success.\n");
	} else {
		printf("Invalid file path: %s\n", file_name);
		exit(-1);
	}
}

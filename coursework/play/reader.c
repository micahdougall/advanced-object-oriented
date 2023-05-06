#include <stdio.h>
#include <stdlib.h>
// #include "product.c"


unsigned int read_file(char *file_path, product_t *products_array) {
	FILE *file = fopen(file_path, "r");


	if (file) {
		printf("\nReading file...\n");

		unsigned int record_count = 2;
		// fscanf(file, "%d\n", &record_count);

		printf("\nLooping through %d records\n", record_count);

		// int *block_ptr = (int*) malloc(
		// 	10 * (sizeof(unsigned int) * 2 + sizeof(float) * 2 + sizeof(char) * 50)
		// );
		// if (block_ptr == NULL) {
		// 	printf("Failed ot allocatie enough memory...\n");
		// 	exit(-1);
		// }

		for (int i = 0; i < record_count; i++) {
			printf("\n%d...\n", i);

			int product_code;
			// int product_quantity;
			// float product_price;
			// float product_discount;
			// //TODO: Max size is bigger than this - 1000
			char *product_name;

			printf("%p\n", &product_code);
			// printf("%p\n", &product_quantity);
			// printf("%p\n", &product_price);
			// printf("%p\n", &product_discount);
			printf("%p\n", product_name);
			

			// printf("\nScanning...\n");
			// fscanf(file, "%d %d %f %f %s\n", 
			// 	&product_code, &product_quantity, &product_price, 
			// 	&product_discount, product_name);

			// unsigned int *product_code = (unsigned int *) malloc(sizeof(unsigned int));
			// char *product_name = (char *) malloc(sizeof(char) * 20);

			// printf("%p\n", product_code);
			// printf("%p\n", &product_quantity);
			// printf("%p\n", &product_price);
			// printf("%p\n", &product_discount);
			// printf("%p\n", product_name);

			fscanf(file, "%d %s\n", 
				&product_code, product_name);

			// printf("%d", &product_code);
			// printf("%d", product_quantity);
			// printf("%f", product_price);
			// printf("%f", product_discount);
			// printf("%s", product_name);

			// product_t product = {
				// .code = *product_code,
				// .quantity = product_quantity,
				// .price = product_price,
				// .max_dicount_rate = product_discount,
				// .name = product_name
			// };
			// products_array[i] = product;

			// p_products_t[i] = &product;

			// printf("prod name in loop %s\n", product.name);
			// struct product_t *prod_ptr = &product;
			// printf("%p\n", &prod_ptr);



			// printf("Product: %s\n", product.name);
		}

		// printf("\nRecord count...%d\n", record_count);
		// Store mem address of first product
		// printf("%s\n", ptr_products_store);
		// printf("%s\n", products_array[0]);


		// printf("prod name in loop %s\n", products_array[0].name);
		// struct product_t first_product = products[0];
		// printf("prod name in loop %s\n", first_product.name);
		// *ptr_products_store = &(products_array[0]);
		// ptr_products_array = &first_product;
		// ptr_products_array = products;

		fclose(file);
		return record_count;
	} else {
		printf("File not found");
		exit(-1);
	}
}

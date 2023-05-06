#include <stdio.h>
#include "main.h"

int main(int argc, char *argv[]) {
	product_t *products_array;

	printf("pointer address..%p\n", products_array);

	// int product_count = read_file("T5-Products-10.txt", products_array);
//	int product_count = read_file("../T5-Products-10-Test.txt", products_array);

    FILE *file = fopen("../T5-Products-10-Test.txt", "r");

    int product_count;
    if (file) {
        printf("\nReading file...\n");

        unsigned int record_count = 2;
        // fscanf(file, "%d\n", &record_count);

        printf("\nLooping through %d records\n", record_count);

        for (int i = 0; i < record_count; i++) {
            printf("\n%d...\n", i);

            int product_code;
            char *product_name;

            printf("%p\n", &product_code);
            printf("%p\n", product_name);
            fscanf(file, "%d %s\n",
                   &product_code, product_name);
        }
        product_count = record_count;
    }

	printf("File has %d products\n", product_count);
	printf("pointer address..%p\n", products_array);
	// print_stock_report(products_array, product_count);
	



	return 0;
}
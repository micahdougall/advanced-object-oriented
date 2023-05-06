#include <stdio.h>
#include "main.h"

int main(int argc, char *argv[]) {
	product_t *products_array;

	printf("pointer address..%p\n", products_array);

	// int product_count = read_file("T5-Products-10.txt", products_array);
	int product_count = read_file("T5-Products-10-Test.txt", products_array);

	printf("File has %d products\n", product_count);
	printf("pointer address..%p\n", products_array);
	// print_stock_report(products_array, product_count);
	



	return 0;
}